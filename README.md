# ProyectoFinal_Gauna – Test Automation (OrangeHRM)

Trabajo Final Integrador: automatización E2E del flujo **LOGIN → DASHBOARD → BUSCAR EMPLEADO → LOGOUT**
sobre https://opensource-demo.orangehrmlive.com/ (usuario `Admin` / contraseña `admin123`).

**Stack:** Java 11+, Maven, Selenium WebDriver 4.10.0, Cucumber 7.13.0, Cucumber-TestNG 7.13.0, TestNG 7.8.0.

## Estructura

```
├── pom.xml
├── testng.xml                      # suite -> TestRunner
└── src/test
    ├── java/com/proyectofinal/orangehrm
    │   ├── pages
    │   │   ├── BasePage.java       # click, type, getText, isElementVisible, waitForUrlContains, waitForElementToDisappear
    │   │   ├── LoginPage.java      # extends BasePage
    │   │   ├── DashboardPage.java  # extends BasePage
    │   │   └── PIMPage.java        # extends BasePage
    │   ├── steps
    │   │   ├── Hooks.java          # @Before / @After (WebDriver)
    │   │   └── FlujoPrincipalSteps.java
    │   └── runners
    │       └── TestRunner.java
    └── resources/features
        └── flujo_completo.feature  # 3 scenarios
```

## Cómo ejecutar

Requisitos: JDK 11 o superior, Maven y Google Chrome instalado (Selenium Manager descarga el chromedriver solo).

```bash
mvn clean test                    # abre Chrome
mvn clean test -Dheadless=true    # sin ventana
```

Los reportes quedan en `target/cucumber-reports/` (carpeta que no se incluye en la entrega).

## Notas

- Si cambiás el paquete base, actualizá el nombre de la clase en `testng.xml` y el `glue` en `TestRunner`.
- Las esperas explícitas son de 15 segundos (OrangeHRM demo puede ser lento).
- `PIMPage.hasResults()` devuelve `true` cuando la búsqueda trae al menos un empleado; `isNoRecordsDisplayed()` es su contrario.
