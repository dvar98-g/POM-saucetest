package com.qa.testing.saucetest.tests.login;

import com.qa.testing.saucetest.pages.login.LoginPage;
import com.qa.testing.saucetest.tests.BaseTest;
import com.qa.testing.saucetest.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Casos de prueba de la pantalla de login de SauceDemo.
 * Cada escenario vive en su propio método de test, sin mezclar aserciones
 * de distintos flujos en un mismo caso.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Login exitoso con credenciales válidas debe llevar a la página de inventario")
    public void loginConCredencialesValidasDeberiaRedirigirAlInventario() {
        String username = TestDataReader.getValue("login/validUser", "username");
        String password = TestDataReader.getValue("login/validUser", "password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Se esperaba ser redirigido a la página de inventario tras un login exitoso"
        );
    }

    @Test(description = "Login sin usuario debe mostrar el mensaje de error correspondiente")
    public void loginSinUsuarioDeberiaMostrarMensajeDeError() {
        String username = TestDataReader.getValue("login/invalidUser", "username");
        String password = TestDataReader.getValue("login/invalidUser", "password");
        String expectedError = TestDataReader.getValue("login/invalidUser", "expectedError");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertEquals(
                loginPage.getErrorMessage(),
                expectedError,
                "El mensaje de error mostrado no coincide con el esperado"
        );
    }
}