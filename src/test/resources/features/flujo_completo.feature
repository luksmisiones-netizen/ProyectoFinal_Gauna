Feature: Flujo completo en OrangeHRM

  Scenario: Login exitoso
    Given que el usuario está en la página de login
    When inicia sesión con el usuario "Admin" y la contraseña "admin123"
    Then debería visualizar el Dashboard

  Scenario: Buscar empleado
    Given que el usuario inició sesión con credenciales válidas
    When navega al módulo PIM
    And busca al empleado "John"
    Then debería visualizar resultados de búsqueda

  Scenario: Flujo completo E2E
    Given que el usuario inició sesión con credenciales válidas
    When navega al módulo PIM
    And busca al empleado "John"
    Then debería visualizar resultados de búsqueda
    When cierra sesión
    Then debería volver a la página de login
