package com.qa.testing.saucetest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utilidad para leer la configuración del entorno de pruebas (URL base, timeouts, etc.).
 * Evita hardcodear estos valores directamente en las clases de test.
 *
 * Orden de precedencia por cada valor:
 *   1. Variable de entorno (ej. definida como secret/var en GitHub Actions)
 *   2. Propiedad en src/test/resources/config.properties
 *
 * Si config.properties no existe (por ejemplo, en CI donde todo llega por variables
 * de entorno), no se lanza error mientras cada valor requerido esté cubierto por su
 * variable de entorno correspondiente.
 */
public final class ConfigReader {

    private static final String CONFIG_PATH = "/config.properties";

    private static Properties properties;

    private ConfigReader() {
        // Clase utilitaria: no debe instanciarse
    }

    /**
     * Obtiene la URL base de la aplicación.
     * Variable de entorno: BASE_URL. Propiedad: base.url.
     */
    public static String getBaseUrl() {
        return getValue("BASE_URL", "base.url");
    }

    /**
     * Obtiene el timeout por defecto (en segundos) para las esperas explícitas.
     * Variable de entorno: DEFAULT_TIMEOUT_SECONDS. Propiedad: default.timeout.seconds.
     */
    public static int getDefaultTimeoutSeconds() {
        return Integer.parseInt(getValue("DEFAULT_TIMEOUT_SECONDS", "default.timeout.seconds"));
    }

    /**
     * Obtiene la pausa (en milisegundos) a esperar después de cada acción sobre la UI
     * (click, escribir), útil para ver el test correr más despacio y seguirlo visualmente.
     * Es opcional: si no está definida ni como variable de entorno ni como propiedad,
     * se asume 0 (sin pausa), para no afectar corridas en CI por defecto.
     * Variable de entorno: ACTION_DELAY_MILLIS. Propiedad: action.delay.millis.
     */
    public static long getActionDelayMillis() {
        return Long.parseLong(getOptionalValue("ACTION_DELAY_MILLIS", "action.delay.millis", "0"));
    }

    /**
     * Igual que getValue, pero devuelve un valor por defecto en vez de lanzar error
     * cuando no se encuentra ni la variable de entorno ni la propiedad.
     */
    private static String getOptionalValue(String envVarName, String propertyName, String defaultValue) {
        String envValue = System.getenv(envVarName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String propertyValue = getProperties().getProperty(propertyName);
        return (propertyValue == null || propertyValue.isBlank()) ? defaultValue : propertyValue;
    }

    /**
     * Resuelve un valor de configuración: primero busca la variable de entorno,
     * y si no está definida, cae a la propiedad equivalente en config.properties.
     *
     * @param envVarName   nombre de la variable de entorno (ej. en GitHub Actions)
     * @param propertyName nombre de la propiedad en config.properties
     * @return el valor resuelto
     */
    private static String getValue(String envVarName, String propertyName) {
        String envValue = System.getenv(envVarName);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String propertyValue = getProperties().getProperty(propertyName);
        if (propertyValue == null || propertyValue.isBlank()) {
            throw new IllegalArgumentException(
                    "No se encontró configuración para '" + propertyName + "'. "
                            + "Defínela como variable de entorno " + envVarName
                            + " o como propiedad en config.properties");
        }
        return propertyValue;
    }

    private static synchronized Properties getProperties() {
        if (properties == null) {
            properties = loadProperties();
        }
        return properties;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();

        try (InputStream inputStream = ConfigReader.class.getResourceAsStream(CONFIG_PATH)) {
            if (inputStream == null) {
                // No es un error fatal: en CI, los valores pueden llegar solo por variables de entorno.
                return props;
            }
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer config.properties", e);
        }
    }
}