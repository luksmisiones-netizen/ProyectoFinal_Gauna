package com.proyectofinal.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object de la pantalla de Login de OrangeHRM.
 */
public class LoginPage extends BasePage {

    private static final String LOGIN_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");

    // Credenciales inválidas -> alerta "Invalid credentials"; campos vacíos -> mensaje "Required"
    private final By invalidCredentialsAlert = By.cssSelector(".oxd-alert-content-text");
    private final By requiredFieldMessage = By.cssSelector(".oxd-input-field-error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Navega a la página de Login. */
    public void goTo() {
        driver.get(LOGIN_URL);
    }

    /** Ingresa el usuario. */
    public void enterUserName(String userName) {
        type(usernameInput, userName);
    }

    /** Ingresa la contraseña. */
    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    /** Método de alto nivel: login completo (usuario + contraseña + click en Login). */
    public void loginAs(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        click(loginButton);
    }

    /** Verifica si estamos en la página de Login. */
    public boolean isOnLoginPage() {
        return waitForUrlContains("auth/login") && isElementVisible(loginButton);
    }

    /** Verifica si hay un mensaje de error visible (credenciales inválidas o campos requeridos). */
    public boolean isErrorDisplayed() {
        return isElementVisible(invalidCredentialsAlert) || isElementVisible(requiredFieldMessage);
    }
}
