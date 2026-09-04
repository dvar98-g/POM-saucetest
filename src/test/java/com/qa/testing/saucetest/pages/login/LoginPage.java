package com.qa.testing.saucetest.pages.login;


import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object de la pantalla de login de SauceDemo (https://www.saucedemo.com).
 * Los localizadores usan el atributo data-test, que la app expone específicamente
 * para automatización (más estable que id o clases CSS ante cambios de frontend).
 * Expone el flujo completo de login y la lectura del mensaje de error en caso de fallo.
 */

public class LoginPage extends BasePage {

    @FindBy(css = "[data-test='username']")
    private WebElement usernameInput;

    @FindBy(css = "[data-test='password']")
    private WebElement passwordInput;

    @FindBy(css = "[data-test='login-button']")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    /**
     * Ejecuta el flujo completo de login: escribe usuario, contraseña y hace submit.
     * Cada acción espera explícitamente a que el elemento esté listo antes de interactuar.
     *
     * @param username usuario a ingresar
     * @param password contraseña a ingresar
     */
    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    /**
     * Obtiene el texto del mensaje de error mostrado cuando el login falla
     * (ej. "Epic sadface: Username is required").
     * Espera a que el mensaje sea visible antes de leerlo.
     *
     * @return texto del mensaje de error
     */
    public String getErrorMessage() {
        return waitForVisibility(errorMessage).getText();
    }
}
