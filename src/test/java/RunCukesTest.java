package io.singularitynet.offernet;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(
	//dryRun = true,
	features = {"src/test/resources/"},
	plugin = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" },
	tags = {"~@ignore"}
)

public class RunCukesTest {

    @AfterClass
    public static void teardown() {
		System.gc();
    }

}
