package com.proyectofinal.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object del Dashboard de OrangeHRM (pantalla posterior al login).
 */
public class DashboardPage extends BasePage {

    private final By headerTitle = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By pimMenuItem = By.cssSelector("a[href*='viewPimModule']");
    private final By userDropdown = By.cssSelector("span.oxd-userdropdown-tab");
    private final By logoutLink = By.linkText("Logout");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    /** Navega al módulo PIM desde el menú lateral. */
    public void goToPIM() {
        click(pimMenuItem);
    }

    /** Hace logout desde el menú del usuario y espera a volver a la pantalla de Login. */
    public void logout() {
        click(userDropdown);
        click(logoutLink);
        waitForUrlContains("auth/login");
    }

    /** Verifica si estamos en el Dashboard. */
    public boolean isOnDashboard() {
        return waitForUrlContains("dashboard") && isElementVisible(headerTitle);
    }

    /** Obtiene el texto del header (por ejemplo "Dashboard"). */
    public String getHeaderText() {
        return getText(headerTitle);
    }
}
