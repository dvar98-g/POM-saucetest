package com.qa.testing.saucetest.pages.logout;

import com.qa.testing.saucetest.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object del menú lateral (burger menu) de SauceDemo, específicamente
 * para la acción de logout. El menú está disponible en cualquier página
 * ya autenticada.
 */
public class LogoutPage extends BasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(css = "[data-test='logout-sidebar-link']")
    private WebElement logoutLink;

    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Abre el menú lateral y hace click en la opción de logout.
     */
    public void logout() {
        click(menuButton);
        click(logoutLink);
    }
}