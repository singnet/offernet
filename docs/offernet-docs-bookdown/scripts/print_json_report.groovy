import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def json_report_processed = new JsonSlurper().parse(new File('cucumber-json-report-processed.json').toURL())

json_report_processed.each { feature ->
	def includeFeature = (feature.tags.find {tag->tag.name=='@indoc'} ? true : false)
	if (includeFeature && feature.result.status =="passed") {
		println('## '+feature.name)
		println(feature.description+"\n")
		def scenarioInOutlineNumber = 1;
		feature.elements.each {  scenario->
			def includeScenario = (scenario.tags.find {tag->tag.name=='@indoc'} ? true : false)
			if (includeScenario && scenario.result.status =="passed") {
				if (scenario.keyword == "Scenario Outline") {
					if (scenarioInOutlineNumber == 1) {
						println('### '+scenario.keyword+': '+scenario.name)
						println(scenario.description+"\n")
					}
					println('#### Example '+scenarioInOutlineNumber+'\n')
					scenarioInOutlineNumber = scenarioInOutlineNumber + 1;
					scenario.steps.each { step ->
						println('* '+step.keyword+': '+step.name+'\n')
					}	
				} else {
					scenarioInOutlineNumber = 1;
					println('### '+scenario.keyword+': '+scenario.name)
					println(scenario.description+"\n")
					scenario.steps.each { step ->
						println('* '+step.keyword+': '+step.name+'\n')
					}
				}
				//println("![](images/"+scenario.name.replaceAll(' ','_')+".gif)")
			}
		}
	}
}