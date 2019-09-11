package runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/java/features"
,glue= {"seleniumgluecode"},
plugin = { "com.cucumber.listener.ExtentCucumberFormatter:C:/Temp/JavaTestResults/report.html"}, 
monochrome = true
)

public class TestRunner {

}
