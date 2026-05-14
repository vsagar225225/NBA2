package com.nba.portal.runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        // Cucumber reads all feature files from this folder and maps steps using glue packages.
        features = "src/test/resources/features",
        glue = {"com.nba.portal.steps", "com.nba.portal.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber-report.json"
        },
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @Override
    // Parallel execution is disabled to keep browser sessions simple and predictable.
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
