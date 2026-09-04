package com.qa.testing.saucetest.pages.checkout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object de la página de inventario/productos de SauceDemo (/inventory.html).
 * Por ahora solo expone lo necesario para el flujo de checkout: agregar productos
 * al carrito e ir al carrito. Si en el futuro se automatiza un módulo de catálogo
 * independiente, esta clase debería moverse a su propia carpeta.
 */
public class InventoryPage extends BasePage {

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Agrega un producto al carrito buscándolo por su nombre visible en pantalla,
     * sin depender del slug interno que SauceDemo genera para cada botón.
     * Se localiza usando exclusivamente atributos data-test (más estables que
     * las clases CSS, que en esta página pueden traer espacios extra).
     *
     * @param productName nombre exacto del producto, ej. "Sauce Labs Backpack"
     */
    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(
                "//div[@data-test='inventory-item-description']"
                        + "[.//div[@data-test='inventory-item-name' and normalize-space(text())='"
                        + productName + "']]"
                        + "//button[starts-with(@data-test,'add-to-cart-')]");

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        button.click();
    }

    /**
     * Navega al carrito de compras.
     */
    public void goToCart() {
        click(cartLink);
    }
}