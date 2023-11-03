package runner;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;


//@RunWith(Cucumber.class)
//@CucumberOptions(
//        plugin = {"pretty", "html:target/cucumber/bagbasics.html"},
//        features = "src/test/resources/features",
//        dryRun = true,
//        glue = "kg.attractor.features"
//)
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("stepdefs")
@ConfigurationParameters({
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty"),
        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:data/cucumber/testUI.html"),
        @ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features"),
        @ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME, value = "true"),
})
public class MovieReviewApplicationTests {
}
