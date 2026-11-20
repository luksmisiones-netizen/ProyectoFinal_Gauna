package com.proyectofinal.orangehrm.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Hooks de Cucumber: inicializan el WebDriver antes de cada scenario y lo cierran al terminar.
 * El driver se comparte con los steps mediante {@link #getDriver()}.
 *
 * Para ejecutar sin abrir la ventana del navegador: mvn test -Dheadless=true
 */
public class Hooks {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /** Devuelve el WebDriver del scenario en ejecución. */
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        // Selenium 4.10 incluye Selenium Manager: descarga el chromedriver correcto automáticamente
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        DRIVER.set(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                if (scenario.isFailed()) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                }
            } finally {
                driver.quit();
                DRIVER.remove();
            }
        }
    }
}
