@Grab(group='net.masterthought', module='cucumber-reporting', version='3.1.0')

import net.masterthought.cucumber.*;

File reportOutputDirectory = new File("target");
List<String> jsonFiles = new ArrayList<>();
jsonFiles.add("target/cucumber-json-report.json");

String jenkinsBasePath = "";
String buildNumber = "1";
String projectName = "offernet";
boolean runWithJenkins = false;
boolean parallelTesting = false;

Configuration configuration = new Configuration(reportOutputDirectory, projectName);
// optional configuration
//configuration.setParallelTesting(parallelTesting);
//configuration.setJenkinsBasePath(jenkinsBasePath);
//configuration.setRunWithJenkins(runWithJenkins);
//configuration.setBuildNumber(buildNumber);

ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
Reportable result = reportBuilder.generateReports();
// and here validate 'result' to decide what to do
// if report has failed features, undefined steps etc