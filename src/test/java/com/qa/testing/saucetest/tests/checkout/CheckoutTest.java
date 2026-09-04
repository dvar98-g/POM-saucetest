package com.qa.testing.saucetest.tests.checkout;

import com.qa.testing.saucetest.pages.cart.CartPage;
import com.qa.testing.saucetest.pages.cart.InventoryPage;
import com.qa.testing.saucetest.pages.checkout.CheckoutCompletePage;
import com.qa.testing.saucetest.pages.checkout.CheckoutStepOnePage;
import com.qa.testing.saucetest.pages.checkout.CheckoutStepTwoPage;
import com.qa.testing.saucetest.tests.LoggedInBaseTest;
import com.qa.testing.saucetest.utils.RandomUtils;
import com.qa.testing.saucetest.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Casos de prueba del flujo de checkout completo de SauceDemo:
 * carrito → información de envío → confirmación.
 * Por ahora solo cubre el camino feliz (checkout exitoso), con productos
 * elegidos al azar desde el catálogo disponible.
 */
public class CheckoutTest extends LoggedInBaseTest {

    private static final int PRODUCTS_TO_PURCHASE = 2;

    @Test(description = "Checkout completo con productos aleatorios debe finalizar con mensaje de confirmación")
    public void checkoutConProductosAleatoriosDeberiaCompletarseExitosamente() {
        List<String> availableProducts = TestDataReader.getValues("cart/availableProducts", "product");
        List<String> randomProducts = RandomUtils.pickRandom(availableProducts, PRODUCTS_TO_PURCHASE);

        String firstName = TestDataReader.getValue("checkout/shippingInfo", "firstName");
        String lastName = TestDataReader.getValue("checkout/shippingInfo", "lastName");
        String postalCode = TestDataReader.getValue("checkout/shippingInfo", "postalCode");
        String expectedConfirmationMessage = TestDataReader.getValue("checkout", "expectedConfirmationMessage");

        InventoryPage inventoryPage = new InventoryPage(driver);
        for (String product : randomProducts) {
            inventoryPage.addProductToCart(product);
        }
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCheckout();

        CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage(driver);
        stepOnePage.fillInfoAndContinue(firstName, lastName, postalCode);

        CheckoutStepTwoPage stepTwoPage = new CheckoutStepTwoPage(driver);
        stepTwoPage.finishOrder();

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Assert.assertEquals(
                completePage.getConfirmationMessage(),
                expectedConfirmationMessage,
                "El mensaje de confirmación no coincide con el esperado tras completar el checkout"
        );
    }
}