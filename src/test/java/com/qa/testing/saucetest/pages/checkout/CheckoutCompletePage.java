package com.qa.testing.saucetest.pages.checkout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object de la pantalla de confirmación del checkout de SauceDemo
 * (/checkout-complete.html).
 */
public class CheckoutCompletePage extends BasePage {

    @FindBy(css = "[data-test='complete-header']")
    private WebElement confirmationHeader;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Obtiene el mensaje de confirmación mostrado tras completar la compra
     * (ej. "Thank you for your order!").
     *
     * @return texto del encabezado de confirmación
     */
    public String getConfirmationMessage() {
        return waitForVisibility(confirmationHeader).getText();
    }
}