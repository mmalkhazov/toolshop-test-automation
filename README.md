# Toolshop Test Automation

[![CI](https://github.com/mmalkhazov/toolshop-test-automation/actions/workflows/ci.yml/badge.svg)](https://github.com/mmalkhazov/toolshop-test-automation/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4-brightgreen)
![REST Assured](https://img.shields.io/badge/REST--Assured-6.0-blue)
![TestNG](https://img.shields.io/badge/TestNG-7.12-red)
![Allure](https://img.shields.io/badge/Allure-2.34-yellow)
![ISTQB](https://img.shields.io/badge/ISTQB-CTFL%204.0-informational)

UI + API test automation portfolio project targeting [practicesoftwaretesting.com](https://practicesoftwaretesting.com).

📊 **[Live Allure Report](https://mmalkhazov.github.io/toolshop-test-automation/)**

---

## Tech Stack

| | Technology |
|---|---|
| Language | Java 17 |
| UI Automation | Selenium 4 |
| API Testing | REST Assured 6.0 |
| Test Framework | TestNG 7.12 |
| Build | Gradle |
| Reporting | Allure 2.34 |
| Logging | Logback / SLF4J |
| Data layer | Lombok Builder pattern |
| CI/CD | GitHub Actions |

---

## Architecture

The project separates infrastructure, page objects, API clients, and test logic into distinct layers.

```
┌──────────────────────────────────────────────┐
│                 TEST LAYER                   │
│     UI Tests │ API Tests │ E2E Tests         │
└──────────────┬───────────────────────────────┘
               │
┌──────────────▼───────────────────────────────┐
│             ABSTRACTION LAYER                │
│   Page Objects (POM) │ API Clients           │
└──────────────┬───────────────────────────────┘
               │
┌──────────────▼───────────────────────────────────┐
│          INFRASTRUCTURE / CONFIG                 │
│  ApplicationManager │ ApiManager │ ConfigManager │
│  TestDataFactory │ CartDataFactory               │
└──────────────────────────────────────────────────┘
```

### API Client Hierarchy

```
BaseClient (REST Assured specs, auth, public and invalid token requests)
  ├── UserApiClient      /users  /login  /register /me
  ├── ProductApiClient   /products  /search
  └── CartApiClient      /carts
```

### Page Object Hierarchy

```
BasePage (WebDriver utils, explicit waits, type/click helpers)
  ├── HomePage
  ├── LoginPage
  ├── RegistrationPage
  ├── ProductPage
  ├── ShoppingCartPage
  └── ProfilePage
        └── Components
              ├── Header      navigation, cart badge
              └── FilterBar   sort, filter, search, pagination
```

---

## Project Structure

```
src/test/
├── java/com/toolshop/
│   ├── client/           REST Assured client layer
│   ├── config/           ApplicationManager, ApiManager, ConfigManager
│   ├── dto/              Request and response DTOs (Lombok builders)
│   │   ├── user/
│   │   ├── products/
│   │   └── carts/
│   ├── models/           UI data models (User, Customer)
│   ├── pages/            Page Object Model
│   │   └── components/   Header, FilterBar
│   ├── utils/            TestDataFactory, CartDataFactory
│   └── tests/
│       ├── API/
│       │   ├── user/
│       │   ├── product/
│       │   ├── cart/
│       │   └── e2e/
│       └── UI/
│           ├── Cart/
│           └── e2e/
└── resources/
    ├── config.properties.example   committed config template
    └── suites/                     TestNG XML suite files
        ├── smoke.xml
        ├── regression.xml
        ├── api.xml
        ├── ui.xml
        ├── e2e.xml
        ├── auth.xml
        ├── cart.xml
        ├── catalog.xml
        ├── critical.xml
        └── negative.xml
```

---

## Test Coverage

### API Tests

| Area | Test Scenarios |
|---|---|
| **Login** | Valid credentials, wrong password, wrong email, empty fields, invalid format |
| **Registration** | Valid user, missing email / password / first name, weak password, duplicate email |
| **Get User** | Valid token → profile returned, invalid token → 401, user by ID, unauthorized ID → 404 |
| **Products** | Get all, get by ID, search by keyword, empty search results, wrong HTTP method → 405 |
| **Cart — Create** | Cart created, ID returned in response |
| **Cart — Add item** | Valid product added, missing product ID (API bug documented), zero quantity |
| **Cart — Get** | Empty cart, cart with item (full payload validated), invalid cart ID → 404 |
| **Cart — Delete** | Empty cart, cart with items + verify 404 after delete, invalid ID, double delete |
| **E2E API** | Register → Login → Fetch product → Create cart → Add item → Verify → Delete cart |

### UI Tests

| Area | Test Scenarios |
|---|---|
| **Login** | Valid login, wrong email, wrong password, invalid formats, empty fields |
| **Logout** | Redirect to login page, session cleared |
| **Registration** | Valid registration, all required field validations, invalid date format, password rules |
| **Navigation** | Home, Contact, Login via header links |
| **Product detail** | Name displayed, price displayed, buttons visible |
| **Product search** | Keyword returns matches, no-results message, empty query behaviour |
| **Sorting** | Price ascending / descending, name ascending / descending — validated across all pages |
| **Filtering** | Category filter — Hand Tools, Hammer, Measures |
| **Cart — Add** | Add product, cart badge count, multiple quantity, toast message |
| **Cart — Remove** | Item removed, empty cart message, badge disappears |
| **Cart — Update** | Quantity change recalculates total, field value reflects update |
| **E2E UI** | Full checkout: product → cart → login → billing address → payment → confirmation |

---

## Key Patterns

### DTO Builder with `.toBuilder()`
All test data variations are built from a single base factory method, overriding only what differs:
```java
TestDataFactory.createValidUserRequest().toBuilder()
    .email("")
    .build();
```

### Fresh test data per test via `@BeforeMethod`
API tests create isolated state before each test and clean up after:
```java
@BeforeMethod
public void precondition() {
    cartId = cartClient.createCartAndGetId();
}

@AfterClass
public void postcondition() {
    userClient.deleteUserById(userId);
}
```

### Documented API bugs
Where the API behaves unexpectedly, tests document the real behaviour rather than asserting an incorrect expected value:
```java
// BUG: API returns 302 redirect instead of expected 422
// Expected: 422 with validation error for missing product_id
// Actual:   302 redirect — API lacks input validation here
cartClient.addItemToCart(cartId, CartDataFactory.cartItemWithoutProductId())
        .assertThat().statusCode(302);
```

### Multi-page sort validation
`FilterBar.getAllPrices()` collects prices across all pagination pages before comparing — sort order is validated against the full catalogue, not just page one.

### Externalized configuration
No credentials in source code. `ConfigManager` reads from `config.properties` locally, with automatic fallback to environment variables for CI:
```
base.url      →  BASE_URL        (GitHub Actions secret)
user.email    →  USER_EMAIL
user.password →  USER_PASSWORD
```

---

## Running the Tests

### Prerequisites
- Java 17+
- Google Chrome (latest)

### Setup
```bash
# Copy the example config and fill in your credentials
cp src/test/resources/config.properties.example src/test/resources/config.properties
```

### Run specific suites
```bash
# API tests only
./gradlew apiTests

# UI tests (local only — see CI section below)
./gradlew uiTests -Pbrowser=chrome

# Smoke — fast sanity check
./gradlew smoke -Pbrowser=chrome

# Full regression
./gradlew regression -Pbrowser=chrome

# E2E flows
./gradlew e2e -Pbrowser=chrome

# By domain
./gradlew authTests -Pbrowser=chrome
./gradlew cartTests -Pbrowser=chrome
./gradlew catalogTests -Pbrowser=chrome
./gradlew negativeTests -Pbrowser=chrome
./gradlew critical -Pbrowser=chrome
```

### Generate Allure report locally
```bash
./gradlew allureServe
```

---

## CI/CD

API tests run automatically on every push and pull request to `main` via GitHub Actions.
The Allure report is generated and published to GitHub Pages after every pipeline run.

📊 **[View Live Allure Report](https://mmalkhazov.github.io/toolshop-test-automation/)**

### Why UI tests are excluded from CI
The target site uses Cloudflare bot protection which blocks headless browsers from cloud CI provider IP ranges. This is a standard security measure that affects all major CI providers (GitHub Actions, CircleCI, GitLab CI). UI tests run fully in local development and pass consistently.

```bash
# Run UI tests locally
./gradlew uiTests -Pbrowser=chrome
```

---

## Author

**Malkhaz Malkhazov** — QA Engineer · ISTQB® Certified Tester Foundation Level (CTFL 4.0)

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://linkedin.com/in/malkhaz-malkhazov)
[![GitHub](https://img.shields.io/badge/GitHub-Profile-black?logo=github)](https://github.com/mmalkhazov)