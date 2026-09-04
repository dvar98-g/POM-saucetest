package com.qa.testing.saucetest.pages.checkout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object del carrito de compras de SauceDemo (/cart.html).
 * Por ahora solo expone continuar al checkout (camino feliz).
 */
public class CartPage extends BasePage {

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Continúa desde el carrito hacia el primer paso del checkout.
     */
    public void goToCheckout() {
        click(checkoutButton);
    }
}