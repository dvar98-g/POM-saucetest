package com.qa.testing.saucetest.pages.checkout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object del primer paso del checkout de SauceDemo (/checkout-step-one.html).
 * Formulario de información de envío: nombre, apellido y código postal.
 */
public class CheckoutStepOnePage extends BasePage {

    @FindBy(css = "[data-test='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "[data-test='lastName']")
    private WebElement lastNameInput;

    @FindBy(css = "[data-test='postalCode']")
    private WebElement postalCodeInput;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Completa el formulario de información de envío y continúa al resumen del pedido.
     *
     * @param firstName  nombre
     * @param lastName   apellido
     * @param postalCode código postal
     */
    public void fillInfoAndContinue(String firstName, String lastName, String postalCode) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, postalCode);
        click(continueButton);
    }
}