package com.proyectofinal.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object del módulo PIM (Personal Information Management) de OrangeHRM.
 */
public class PIMPage extends BasePage {

    private final By employeeNameInput = By.xpath(
            "//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");

    // Texto de cantidad de resultados: "(5) Records Found" / "(1) Record Found" / "No Records Found"
    private final By recordsFoundLabel = By.xpath("//span[contains(normalize-space(),'Found')]");
    private final By noRecordsLabel = By.xpath("//span[normalize-space()='No Records Found']");
    private final By resultRows = By.cssSelector(".oxd-table-body .oxd-table-card");

    // Indicadores de carga de OrangeHRM
    private final By formLoader = By.cssSelector(".oxd-form-loader");
    private final By tableLoader = By.cssSelector(".oxd-table-loader");

    public PIMPage(WebDriver driver) {
        super(driver);
    }

    /** Busca un empleado por nombre y espera a que la lista termine de actualizarse. */
    public void searchEmployeeByName(String employeeName) {
        // Se espera a que la lista inicial termine de cargar antes de buscar
        getText(recordsFoundLabel);
        waitForElementToDisappear(formLoader);

        type(employeeNameInput, employeeName);
        click(searchButton);

        // Se espera a que terminen los indicadores de carga y a que se muestre el resultado
        waitForElementToDisappear(formLoader);
        waitForElementToDisappear(tableLoader);
        getText(recordsFoundLabel);
    }

    /** Verifica si la búsqueda devolvió al menos un empleado. */
    public boolean hasResults() {
        return !driver.findElements(resultRows).isEmpty();
    }

    /** Verifica si se muestra el mensaje "No Records Found". */
    public boolean isNoRecordsDisplayed() {
        return isElementVisible(noRecordsLabel);
    }

    /** Verifica si estamos en la página de PIM. */
    public boolean isOnPIMPage() {
        return waitForUrlContains("viewEmployeeList") && isElementVisible(employeeNameInput);
    }
}
