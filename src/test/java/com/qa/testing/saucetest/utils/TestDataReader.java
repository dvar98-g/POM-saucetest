package com.qa.testing.saucetest.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para leer datos de prueba desde src/test/resources/data/testdata.xml.
 * Evita hardcodear credenciales u otros valores directamente en las clases de test.
 */
public final class TestDataReader {

    private static final String TESTDATA_PATH = "/data/testdata.xml";

    private static Document document;

    private TestDataReader() {
        // Clase utilitaria: no debe instanciarse
    }

    /**
     * Obtiene el valor de texto de un elemento hijo directo dentro de una sección.
     * Ejemplo: getValue("login/validUser", "username")
     *
     * @param sectionPath ruta de elementos padres separados por "/", ej. "login/validUser"
     * @param field       nombre del elemento hijo a leer
     * @return texto contenido en el elemento, o cadena vacía si no tiene contenido
     */
    public static String getValue(String sectionPath, String field) {
        Element section = navigateToSection(sectionPath);
        Element fieldElement = getChildElement(section, field);
        return fieldElement.getTextContent();
    }

    /**
     * Obtiene los valores de texto de todos los elementos hijos con un mismo nombre
     * dentro de una sección. Ejemplo: getValues("checkout/products", "product")
     *
     * @param sectionPath ruta de elementos padres separados por "/", ej. "checkout/products"
     * @param field       nombre repetido de los elementos hijos a leer
     * @return lista de textos, en el orden en que aparecen en el XML
     */
    public static List<String> getValues(String sectionPath, String field) {
        Element section = navigateToSection(sectionPath);
        var children = section.getElementsByTagName(field);

        List<String> values = new ArrayList<>();
        for (int i = 0; i < children.getLength(); i++) {
            values.add(children.item(i).getTextContent());
        }
        return values;
    }

    private static Element navigateToSection(String sectionPath) {
        Document doc = getDocument();
        Element current = doc.getDocumentElement();

        for (String tag : sectionPath.split("/")) {
            current = getChildElement(current, tag);
        }
        return current;
    }

    private static Element getChildElement(Element parent, String tagName) {
        var children = parent.getElementsByTagName(tagName);
        if (children.getLength() == 0) {
            throw new IllegalArgumentException(
                    "No se encontró el elemento <" + tagName + "> dentro de <" + parent.getTagName() + "> en testdata.xml");
        }
        return (Element) children.item(0);
    }

    private static synchronized Document getDocument() {
        if (document == null) {
            document = loadDocument();
        }
        return document;
    }

    private static Document loadDocument() {
        try (InputStream inputStream = TestDataReader.class.getResourceAsStream(TESTDATA_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("No se encontró el archivo de datos de prueba: " + TESTDATA_PATH);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            throw new IllegalStateException("Error al leer testdata.xml", e);
        }
    }
}