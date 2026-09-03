package com.qa.testing.saucetest.utils;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Fábrica responsable de crear e inicializar el WebDriver, y de cerrarlo
 * correctamente al finalizar. Centraliza la configuración y el ciclo de vida
 * del navegador para que los tests no dependan de cómo se construye o se cierra.
 *
 * Por ahora solo soporta Chrome, sin modo headless.
 */

public class DriveFactory {

    private DriveFactory(){

    }

    /**
      * Crea un WebDriver de Chrome, maximizado y listo para usar.
      * @return instancia de WebDriver configurada
     */

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        return driver;
    }


    /**
     * Cierra el navegador y libera los recursos asociados al driver.
     * No lanza excepción si el driver ya es nulo, para que pueda llamarse
     * de forma segura desde un @AfterMethod incluso si la creación falló.
     *
     * @param driver instancia de WebDriver a cerrar
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }





}
