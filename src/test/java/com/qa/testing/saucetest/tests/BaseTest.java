package com.qa.testing.saucetest.tests;

import com.qa.testing.saucetest.utils.ConfigReader;
import com.qa.testing.saucetest.utils.DriveFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Clase base para todas las clases de test.
 * Se encarga de crear el WebDriver antes de cada test y cerrarlo al finalizar,
 * y de navegar a la URL base de la aplicación bajo prueba (leída desde config.properties).
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriveFactory.createDriver();
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        DriveFactory.quitDriver(driver);
    }
}