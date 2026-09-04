# POM-saucetest

Framework de pruebas funcionales automatizadas para [SauceDemo](https://www.saucedemo.com/), construido con **Selenium**, **Java 25**, **Maven** y **TestNG**, siguiendo el patrón **Page Object Model (POM)**.

## Stack

- **Java 25**
- **Maven**
- **Selenium WebDriver 4.39.0** (Chrome, vía Selenium Manager — no requiere descargar el driver manualmente)
- **TestNG 7.10.2**
- Todo el código vive en `src/test` (no se usa `src/main`)

## Estructura del proyecto

```
src/test/java/com/qa/testing/saucetest/
├── pages/                          # Page Objects, organizados por módulo
│   ├── BasePage.java                # esperas explícitas, helpers type()/click(), Page Factory
│   ├── login/
│   │   └── LoginPage.java
│   ├── cart/
│   │   ├── InventoryPage.java       # reutilizada por checkout y cart
│   │   └── CartPage.java            # reutilizada por checkout y cart
│   ├── checkout/
│   │   ├── CheckoutStepOnePage.java
│   │   ├── CheckoutStepTwoPage.java
│   │   └── CheckoutCompletePage.java
│   └── logout/
│       └── LogoutPage.java
│
├── tests/                          # Casos de prueba, organizados por módulo
│   ├── BaseTest.java                 # setup/teardown del WebDriver
│   ├── LoggedInBaseTest.java         # extiende BaseTest; hace login antes de cada test
│   ├── login/
│   │   └── LoginTest.java
│   ├── checkout/
│   │   └── CheckoutTest.java
│   ├── cart/
│   │   └── CartTest.java
│   └── logout/
│       └── LogoutTest.java
│
└── utils/
    ├── ConfigReader.java            # lee config.properties / variables de entorno
    ├── DriverFactory.java           # crea y cierra el WebDriver (Chrome)
    ├── TestDataReader.java          # lee testdata.xml
    └── RandomUtils.java             # elige N elementos aleatorios de una lista

src/test/resources/
├── config.template.properties      # plantilla versionada en git
├── config.properties               # copia local, NO se sube a git (.gitignore)
├── data/
│   └── testdata.xml                 # credenciales, catálogo de productos, datos de envío
└── suites/
    └── testng.xml                   # suite de TestNG (registrada en el pom.xml)
```

## Cómo correr los tests

### 1. Configurar el entorno

La primera vez, copia la plantilla y ajusta los valores si hace falta:

```bash
cp src/test/resources/config.template.properties src/test/resources/config.properties
```

`config.properties` **no se sube a git** (es específico de cada entorno). Los valores disponibles:

| Propiedad | Variable de entorno equivalente | Requerida | Descripción |
|---|---|---|---|
| `base.url` | `BASE_URL` | Sí | URL base de la aplicación bajo prueba |
| `default.timeout.seconds` | `DEFAULT_TIMEOUT_SECONDS` | Sí | Timeout por defecto para esperas explícitas |
| `action.delay.millis` | `ACTION_DELAY_MILLIS` | No (default `0`) | Pausa tras cada click/escritura, útil para ver los tests correr más despacio |

Las variables de entorno tienen prioridad sobre `config.properties` — útil para CI/CD (ej. GitHub Actions), donde ni siquiera hace falta que exista el archivo.

### 2. Correr desde IntelliJ

- Click derecho sobre cualquier clase de test (ej. `LoginTest`) → **Run**.
- O click derecho sobre `src/test/resources/suites/testng.xml` → **Run 'testng.xml'** para correr toda la suite.

### 3. Correr desde Maven

```bash
mvn test
```

## Módulos cubiertos

| Módulo | Caso(s) de prueba |
|---|---|
| **Login** | Login exitoso (credenciales válidas) y login fallido (usuario vacío, valida mensaje de error) |
| **Checkout** | Flujo completo: agrega productos aleatorios al carrito → información de envío → confirmación de compra |
| **Cart** | Agrega 3 productos aleatorios distintos, los remueve y valida que el carrito quede vacío |
| **Logout** | Cierra sesión desde el menú lateral y valida el regreso a la pantalla de login |

## Decisiones de arquitectura

- **Page Object Model + Page Factory**: cada página expone solo acciones de negocio (`login()`, `addProductToCart()`), nunca aserciones. Los elementos se declaran con `@FindBy` e inicializan vía `PageFactory.initElements` en `BasePage`.
- **Localizadores**: se prioriza el atributo `data-test` (expuesto por SauceDemo específicamente para automatización) sobre clases CSS o `id`, por ser más estable ante cambios de estilos.
- **Esperas explícitas**: `BasePage` centraliza `WebDriverWait` con timeout configurable; no se usan esperas implícitas ni `Thread.sleep` para sincronización (el único `Thread.sleep` existente es opcional y solo estético, ver `action.delay.millis`).
- **Bases compartidas**: `BaseTest` maneja el ciclo de vida del driver; `LoggedInBaseTest` la extiende para módulos que requieren sesión iniciada (`checkout`, `cart`), evitando duplicar la lógica de login.
- **Datos externalizados**: credenciales, catálogo de productos y datos de formularios viven en `testdata.xml`, nunca hardcodeados en los tests.
- **Un caso de prueba, un método `@Test`**: no se agrupan múltiples escenarios en un mismo test.