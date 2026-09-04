package com.qa.testing.saucetest.tests.checkout;

import com.qa.testing.saucetest.pages.checkout.CartPage;
import com.qa.testing.saucetest.pages.checkout.CheckoutCompletePage;
import com.qa.testing.saucetest.pages.checkout.CheckoutStepOnePage;
import com.qa.testing.saucetest.pages.checkout.CheckoutStepTwoPage;
import com.qa.testing.saucetest.pages.cart.InventoryPage;
import com.qa.testing.saucetest.tests.LoggedInBaseTest;
import com.qa.testing.saucetest.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Casos de prueba del flujo de checkout completo de SauceDemo:
 * carrito → información de envío → confirmación.
 * Por ahora solo cubre el camino feliz (checkout exitoso).
 */
public class LoggedInTest extends LoggedInBaseTest {

    @Test(description = "Checkout completo con 2 o más productos debe finalizar con mensaje de confirmación")
    public void checkoutConVariosProductosDeberiaCompletarseExitosamente() {
        List<String> products = TestDataReader.getValues("checkout/products", "product");

        String firstName = TestDataReader.getValue("checkout/shippingInfo", "firstName");
        String lastName = TestDataReader.getValue("checkout/shippingInfo", "lastName");
        String postalCode = TestDataReader.getValue("checkout/shippingInfo", "postalCode");
        String expectedConfirmationMessage = TestDataReader.getValue("checkout", "expectedConfirmationMessage");

        InventoryPage inventoryPage = new InventoryPage(driver);
        for (String product : products) {
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