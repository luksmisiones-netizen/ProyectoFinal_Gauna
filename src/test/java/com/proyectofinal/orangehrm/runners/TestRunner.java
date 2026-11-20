package com.proyectofinal.orangehrm.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runner de Cucumber sobre TestNG. Lo invoca testng.xml (y por lo tanto "mvn test").
 * Genera reportes en target/cucumber-reports/.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.proyectofinal.orangehrm.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
