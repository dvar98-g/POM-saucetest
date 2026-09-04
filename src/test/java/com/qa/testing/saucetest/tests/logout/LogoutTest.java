package com.qa.testing.saucetest.tests.logout;

import com.qa.testing.saucetest.pages.logout.LogoutPage;
import com.qa.testing.saucetest.tests.LoggedInBaseTest;
import com.qa.testing.saucetest.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Casos de prueba de logout de SauceDemo.
 * Arranca ya logueado (heredado de LoggedInBaseTest) y valida que el logout
 * regrese correctamente a la pantalla de login.
 */
public class LogoutTest extends LoggedInBaseTest {

    @Test(description = "Logout desde el menú lateral debe regresar a la pantalla de login")
    public void logoutDeberiaRegresarALaPantallaDeLogin() {
        LogoutPage logoutPage = new LogoutPage(driver);
        logoutPage.logout();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                ConfigReader.getBaseUrl(),
                "Se esperaba regresar a la URL base (pantalla de login) tras hacer logout"
        );
    }
}