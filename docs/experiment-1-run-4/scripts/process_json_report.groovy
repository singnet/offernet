@Grab(group='commons-codec', module='commons-codec', version='1.10')
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import org.apache.commons.codec.binary.Base64
import javax.imageio.ImageIO
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

wd=''
def json_report_raw = new JsonSlurper().parse(new File(wd+'cucumber-json-report.json').toURL())
def json_report = processJsonReport(json_report_raw)


public void processJsonReport(Object report) {
	report.each { feature ->
		boolean featurePasses = false;
		boolean featureInDoc = false;
		feature.elements.each { scenario ->
			boolean scenarioPasses = true;
			int stepNo=1;
			scenario.steps.each  {step -> 
				boolean stepPasses = true;
				stepPasses = (step.result.status == "passed")
				scenarioPasses = scenarioPasses && stepPasses
				if (step['embeddings']) {
				  byte[] imageInByte = Base64.decodeBase64((String) step.embeddings.data)
				  InputStream inStream = new ByteArrayInputStream(imageInByte);
          BufferedImage image = ImageIO.read(inStream);
				  String dirName = wd+'screenshots/'+scenario.name.replaceAll(" ","_")+"/"
				  new File(dirName).mkdir()
				  String screenshotFileName=stepNo+'.png'
				  File outputfile = new File(dirName+screenshotFileName)
          ImageIO.write(image, "png", outputfile);
				}
				stepNo+=1
			}
			scenario.result=[:]
			if (scenarioPasses) {
				scenario['result'].status='passed'
			} else {
				scenario['result'].status ='no-passed'
			}
			featurePasses = featurePasses | scenarioPasses
			def scenarioInDoc = (scenario.tags.find {tag->tag.name=='@indoc'} ? true : false)
			featureInDoc = featureInDoc | scenarioInDoc
		}
		feature.result=[:]
		if (featurePasses) {
			feature['result'].status = 'passed'
		} else {
			feature['result'].status = 'not-passed'
		}
		feature.tags=[]
		if (featureInDoc) {
			feature['tags'].add([name:'@indoc'])
		}

	}
	def result = JsonOutput.toJson(report)
	File outputfile = new File(wd+'cucumber-json-report-processed.json')
	outputfile.write(result)
}
