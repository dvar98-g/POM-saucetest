package com.qa.testing.saucetest.tests.cart;

import com.qa.testing.saucetest.pages.cart.CartPage;
import com.qa.testing.saucetest.pages.cart.InventoryPage;
import com.qa.testing.saucetest.tests.LoggedInBaseTest;
import com.qa.testing.saucetest.utils.RandomUtils;
import com.qa.testing.saucetest.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Casos de prueba del carrito de compras de SauceDemo:
 * agregar productos, removerlos y verificar el estado del carrito.
 */
public class CartTest extends LoggedInBaseTest {

    private static final int PRODUCTS_TO_TEST = 3;

    @Test(description = "Agregar 3 productos aleatorios y removerlos debe dejar el carrito vacío")
    public void remover3ProductosAleatoriosDeberiaVaciarElCarrito() {
        List<String> availableProducts = TestDataReader.getValues("cart/availableProducts", "product");
        List<String> randomProducts = RandomUtils.pickRandom(availableProducts, PRODUCTS_TO_TEST);

        InventoryPage inventoryPage = new InventoryPage(driver);
        for (String product : randomProducts) {
            inventoryPage.addProductToCart(product);
        }
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        for (String product : randomProducts) {
            cartPage.removeProduct(product);
        }

        Assert.assertEquals(
                cartPage.getItemCount(),
                0,
                "Se esperaba que el carrito quedara vacío tras remover los productos"
        );
    }
}