package org.ys.automation.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = "pretty",
        monochrome = true,
        tags = "@regression",
        glue = "org.cucumber",
        features = "src/test/resources/features")
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
