package com.proyectofinal.orangehrm.steps;

import com.proyectofinal.orangehrm.pages.DashboardPage;
import com.proyectofinal.orangehrm.pages.LoginPage;
import com.proyectofinal.orangehrm.pages.PIMPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions de los 3 scenarios de flujo_completo.feature.
 * Los steps solo orquestan: toda la interacción con la UI vive en los Page Objects.
 */
public class FlujoPrincipalSteps {

    private static final String VALID_USER = "Admin";
    private static final String VALID_PASSWORD = "admin123";

    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;
    private final PIMPage pimPage;

    public FlujoPrincipalSteps() {
        // Cucumber crea esta clase al ejecutar el primer step, cuando el @Before de Hooks ya corrió
        this.loginPage = new LoginPage(Hooks.getDriver());
        this.dashboardPage = new DashboardPage(Hooks.getDriver());
        this.pimPage = new PIMPage(Hooks.getDriver());
    }

    // ---------- Given ----------

    @Given("que el usuario está en la página de login")
    public void queElUsuarioEstaEnLaPaginaDeLogin() {
        loginPage.goTo();
        Assert.assertTrue(loginPage.isOnLoginPage(), "No se mostró la página de login");
    }

    @Given("que el usuario inició sesión con credenciales válidas")
    public void queElUsuarioInicioSesionConCredencialesValidas() {
        loginPage.goTo();
        loginPage.loginAs(VALID_USER, VALID_PASSWORD);
        Assert.assertTrue(dashboardPage.isOnDashboard(), "No se llegó al Dashboard después del login");
    }

    // ---------- When ----------

    @When("inicia sesión con el usuario {string} y la contraseña {string}")
    public void iniciaSesionConElUsuarioYLaContrasena(String usuario, String contrasena) {
        loginPage.loginAs(usuario, contrasena);
    }

    @When("navega al módulo PIM")
    public void navegaAlModuloPIM() {
        dashboardPage.goToPIM();
        Assert.assertTrue(pimPage.isOnPIMPage(), "No se mostró la página de PIM");
    }

    @When("busca al empleado {string}")
    public void buscaAlEmpleado(String nombre) {
        pimPage.searchEmployeeByName(nombre);
    }

    @When("cierra sesión")
    public void cierraSesion() {
        dashboardPage.logout();
    }

    // ---------- Then ----------

    @Then("debería visualizar el Dashboard")
    public void deberiaVisualizarElDashboard() {
        Assert.assertTrue(dashboardPage.isOnDashboard(), "No se mostró el Dashboard");
        Assert.assertEquals(dashboardPage.getHeaderText(), "Dashboard", "El header no coincide");
    }

    @Then("debería visualizar resultados de búsqueda")
    public void deberiaVisualizarResultadosDeBusqueda() {
        Assert.assertTrue(pimPage.hasResults(), "La búsqueda no devolvió resultados");
        Assert.assertFalse(pimPage.isNoRecordsDisplayed(), "Se mostró el mensaje 'No Records Found'");
    }

    @Then("debería volver a la página de login")
    public void deberiaVolverALaPaginaDeLogin() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "No se volvió a la página de login después del logout");
    }
}
