package com.qa.testing.saucetest.pages;

import com.qa.testing.saucetest.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base para todos los Page Objects.
 * Centraliza la inicialización del driver, los elementos anotados con @FindBy
 * y las esperas explícitas necesarias antes de interactuar con la UI.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this(driver, ConfigReader.getDefaultTimeoutSeconds());
    }

    protected BasePage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        PageFactory.initElements(driver, this);
    }

    /**
     * Espera a que el elemento esté visible en pantalla.
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Espera a que el elemento esté visible y habilitado para click.
     */
    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Escribe texto en un campo, esperando primero a que esté visible
     * y limpiando cualquier valor previo.
     */
    protected void type(WebElement element, String text) {
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
        pauseForVisibility();
    }

    /**
     * Hace click en un elemento, esperando primero a que sea clickeable.
     */
    protected void click(WebElement element) {
        waitForClickable(element).click();
        pauseForVisibility();
    }

    /**
     * Pausa configurable tras una acción de UI, solo para poder seguir el test
     * visualmente más despacio (ej. en demos o depuración manual). No es una
     * espera de sincronización: la duración se controla en config.properties
     * (action.delay.millis) y por defecto es 0, sin efecto en la ejecución.
     */
    protected void pauseForVisibility() {
        long delayMillis = ConfigReader.getActionDelayMillis();
        if (delayMillis <= 0) {
            return;
        }
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}