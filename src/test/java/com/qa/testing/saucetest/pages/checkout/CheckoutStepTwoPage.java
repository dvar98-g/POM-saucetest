package com.qa.testing.saucetest.pages.checkout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object del segundo paso del checkout de SauceDemo (/checkout-step-two.html).
 * Resumen del pedido (overview) antes de finalizar la compra.
 */
public class CheckoutStepTwoPage extends BasePage {

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Finaliza la compra desde el resumen del pedido.
     */
    public void finishOrder() {
        click(finishButton);
    }
}