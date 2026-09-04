package com.qa.testing.saucetest.tests;

import com.qa.testing.saucetest.pages.login.LoginPage;
import com.qa.testing.saucetest.utils.ConfigReader;
import com.qa.testing.saucetest.utils.TestDataReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Clase base para los tests del módulo de checkout.
 * El checkout requiere estar logueado, así que además del setup del driver
 * (heredado de BaseTest), realiza el login con un usuario válido antes de
 * cada test, dejando al navegador listo en la página de inventario.
 */
public abstract class LoggedInBaseTest extends BaseTest {

    @BeforeMethod
    public void loginConUsuarioValido() {
        String username = TestDataReader.getValue("login/validUser", "username");
        String password = TestDataReader.getValue("login/validUser", "password");

        new LoginPage(driver).login(username, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getDefaultTimeoutSeconds()));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
}