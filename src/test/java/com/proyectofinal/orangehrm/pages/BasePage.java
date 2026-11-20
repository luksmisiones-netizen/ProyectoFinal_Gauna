package com.proyectofinal.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base de todos los Page Objects.
 * Encapsula las acciones genéricas sobre el navegador (click, type, getText, esperas)
 * para que las páginas hijas (LoginPage, DashboardPage, PIMPage) las reutilicen por herencia.
 */
public class BasePage {

    /** Espera máxima para acciones normales (OrangeHRM puede ser lento: 10 a 15 segundos). */
    protected static final int DEFAULT_TIMEOUT_SECONDS = 15;

    /** Espera corta usada solo para preguntar "¿está visible?" sin bloquear demasiado si no lo está. */
    protected static final int SHORT_TIMEOUT_SECONDS = 5;

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }

    /** Hace click en un elemento (espera a que sea clickeable). */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Escribe texto en un campo (espera a que sea visible y lo limpia antes de escribir). */
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /** Obtiene el texto de un elemento (espera a que sea visible). */
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    /** Verifica si un elemento está visible. Devuelve false si no aparece dentro de la espera corta. */
    protected boolean isElementVisible(By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(SHORT_TIMEOUT_SECONDS))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Espera a que la URL contenga un texto. Devuelve false si se agota el tiempo. */
    protected boolean waitForUrlContains(String text) {
        try {
            return wait.until(ExpectedConditions.urlContains(text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Espera a que un elemento desaparezca (o no exista). Devuelve false si sigue visible al agotarse el tiempo. */
    protected boolean waitForElementToDisappear(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
