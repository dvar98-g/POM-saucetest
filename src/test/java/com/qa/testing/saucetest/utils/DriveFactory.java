package com.qa.testing.saucetest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

/**
 * Fábrica responsable de crear e inicializar el WebDriver, y de cerrarlo
 * correctamente al finalizar. Centraliza la configuración y el ciclo de vida
 * del navegador para que los tests no dependan de cómo se construye o se cierra.
 *
 * Por ahora solo soporta Chrome, sin modo headless.
 */
public final class DriveFactory {

    private DriveFactory() {
        // Clase utilitaria: no debe instanciarse
    }

    /**
     * Crea un WebDriver de Chrome, maximizado y listo para usar.
     * Desactiva el gestor de contraseñas y la detección de filtraciones,
     * que de otro modo muestran un popup nativo que bloquea la interacción
     * al escribir credenciales en formularios de login.
     *
     * @return instancia de WebDriver configurada
     */
    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        );
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication");

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