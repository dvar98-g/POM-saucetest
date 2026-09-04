package com.qa.testing.saucetest.pages.cart;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object del carrito de compras de SauceDemo (/cart.html).
 * Expone continuar al checkout, remover productos y consultar cuántos
 * productos quedan en el carrito.
 */
public class CartPage extends BasePage {

    private static final String CART_ITEM_CONTAINER = "[data-test='inventory-item']";

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

    /**
     * Remueve del carrito un producto, buscándolo por su nombre visible en pantalla.
     *
     * @param productName nombre exacto del producto, ej. "Sauce Labs Backpack"
     */
    public void removeProduct(String productName) {
        By removeButton = By.xpath(
                "//div[@data-test='inventory-item']"
                        + "[.//div[@data-test='inventory-item-name' and normalize-space(text())='"
                        + productName + "']]"
                        + "//button[starts-with(@data-test,'remove-')]");

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        button.click();
    }

    /**
     * Obtiene la cantidad de productos actualmente listados en el carrito.
     *
     * @return cantidad de productos en el carrito
     */
    public int getItemCount() {
        List<WebElement> items = driver.findElements(By.cssSelector(CART_ITEM_CONTAINER));
        return items.size();
    }
}