# Ndosi Test Automation Rest Assured Framework

A robust API test automation framework built with **Rest Assured**, **TestNG**, and **Allure Reports**. This framework is designed to test REST APIs with comprehensive test data generation, request/response validation, and detailed reporting.

---

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Viewing Reports](#viewing-reports)
- [Test Data Generation](#test-data-generation)
- [Project Components](#project-components)

---

## ✨ Features

- **REST API Testing** – Comprehensive API endpoint testing with Rest Assured
- **Data-Driven Testing** – Automated test data generation using Java Faker
- **JSON Schema Validation** – Validate API responses against JSON schemas
- **Detailed Reporting** – Allure Reports for rich, visual test reports
- **TestNG Framework** – Powerful test execution and organization
- **JSON Parsing** – Handle complex JSON responses with Gson and JSON-Simple

---

## 🛠️ Technology Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Programming Language |
| **Maven** | Latest | Build & Dependency Management |
| **Rest Assured** | 6.0.0 | API Testing Library |
| **TestNG** | 7.12.0 | Test Framework |
| **Allure** | 2.34.0 | Test Reporting |
| **Java Faker** | 1.0.2 | Test Data Generation |
| **Gson** | 2.14.0 | JSON Processing |
| **JSON-Simple** | 1.1.1 | JSON Parsing |

---

## 📁 Project Structure

```
NdosiApiBasic/
│
├── src/
│   ├── main/
│   │   └── java/                    # Production code (if any)
│   │
│   └── test/
│       └── java/
│           ├── basic/               # Basic test classes (e.g., UserFlowTest)
│           ├── common/              # Common utilities and helpers
│           ├── payloadBuilder/      # Request payload builders
│           ├── requestBuilder/      # Request construction utilities
│           ├── utils/               # General utility functions
│           └── testData/            # Test data generators (e.g., FakerData)
│
├── pom.xml                          # Maven configuration & dependencies
├── testng.xml                       # TestNG suite configuration
├── README.md                        # This file
│
├── allure-results/                  # Allure test results (generated)
├── target/                          # Maven build output (generated)
└── .gitignore                       # Git ignore configuration

```

---

## 🔧 Prerequisites

Before running this project, ensure you have:

- **Java 21** or higher installed
- **Maven 3.6+** installed
- **Git** for version control
- An IDE (IntelliJ IDEA, Eclipse, VS Code) with Java support

### Verify Installation

```powershell
java -version
mvn -version
git --version
```

---

## 📦 Installation

### 1. Clone the Repository

```powershell
git clone <repository-url>
cd NdosiApiBasic
```

### 2. Install Dependencies

```powershell
mvn clean install
```

This command will:
- Download all dependencies defined in `pom.xml`
- Compile the project
- Prepare the build environment

### 3. Verify Installation

```powershell
mvn test --help
```

---

## ⚙️ Configuration

### TestNG Suite Configuration (`testng.xml`)

The framework uses `testng.xml` to define and organize test execution:

```xml
<suite name="Ndosi API Automation Suite">
    <test name="Ndosi API Register User Flow Tests">
        <classes>
            <class name="basic.UserFlowTest"/>
        </classes>
    </test>
</suite>
```

**Key Features:**
- **Allure Integration** – Listener configured for Allure reporting
- **Test Organization** – Tests grouped by test class
- **Flexible Execution** – Easy to add, remove, or reorder tests

### Java Compiler Configuration

The project is configured to use **Java 21**:

```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

---

## 🚀 Running Tests

### Run All Tests

```powershell
mvn test
```

### Run Specific TestNG Suite

```powershell
mvn test -DsuiteXmlFile=testng.xml
```

### Run Specific Test Class

```powershell
mvn test -Dtest=basic.UserFlowTest
```

### Run with Maven Surefire Plugin (Specified in pom.xml)

```powershell
mvn clean test
```

The Surefire plugin is configured to automatically use `testng.xml` and set up Allure result directories.

---

## 📊 Viewing Reports

### Generate Allure Report

After running tests, generate the Allure report:

```powershell
mvn allure:report
```

### View Allure Report in Browser

```powershell
mvn allure:serve
```

This command will:
1. Generate the Allure report from results in `allure-results/`
2. Start a local web server
3. Automatically open the report in your default browser

**Report Location:** `target/site/allure-report/index.html`

---

## 🧪 Test Data Generation

### Using FakerData Class

The project includes a `FakerData` utility class for generating random test data:

#### Location
```
src/test/java/testData/FakerData.java
```

#### Available Methods

| Method | Returns | Example |
|--------|---------|---------|
| `randomFullName()` | Full name | "John Smith" |
| `randomFirstName()` | First name | "John" |
| `randomLastName()` | Last name | "Smith" |
| `randomEmail()` | Random email | "john.smith@email.com" |
| `randomEmail(String domain)` | Email with custom domain | "john.smith123@example.com" |

#### Usage Example

```java
import testData.FakerData;

public class UserFlowTest {
    FakerData fakeData = new FakerData();
    
    public void testUserRegistration() {
        String userName = fakeData.randomFullName();
        String userEmail = fakeData.randomEmail();
        
        // Use in API request
        given()
            .body("{ \"name\": \"" + userName + "\", \"email\": \"" + userEmail + "\" }")
            .when()
            .post("/api/users")
            .then()
            .statusCode(201);
    }
}
```

---

## 🏗️ Project Components

### 1. **basic/** – Core Test Classes
Contains main test classes like `UserFlowTest` that define test scenarios and flows.

### 2. **common/** – Common Utilities
Shared helper methods and constants used across tests.

### 3. **payloadBuilder/** – Request Payload Creation
Classes to build and structure API request payloads (JSON/XML).

**Example:**
```java
PayloadBuilder builder = new PayloadBuilder();
builder.addField("name", "John")
       .addField("email", "john@example.com")
       .build();
```

### 4. **requestBuilder/** – HTTP Request Construction
Utilities to construct HTTP requests with headers, authentication, and query parameters.

**Example:**
```java
RequestBuilder.newRequest()
    .baseUrl("https://api.example.com")
    .endpoint("/users")
    .header("Authorization", "Bearer token")
    .build();
```

### 5. **utils/** – General Utilities
Helper functions for logging, validation, response parsing, and common operations.

### 6. **testData/** – Test Data Management
Faker data generators and test data setup methods.

---

## 📝 Rest Assured Quick Reference

### Basic API Test Example

```java
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    
    @Test
    public void testGetUser() {
        given()
            .baseUri("https://api.example.com")
            .header("Content-Type", "application/json")
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", notNullValue());
    }
    
    @Test
    public void testPostUser() {
        given()
            .baseUri("https://api.example.com")
            .header("Content-Type", "application/json")
            .body("{\"name\": \"John\", \"email\": \"john@example.com\"}")
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue());
    }
}
```

---

## 🔍 JSON Schema Validation

The framework includes JSON Schema validation capabilities:

```java
import io.restassured.module.jsv.JsonSchemaValidator;

@Test
public void validateResponseSchema() {
    given()
        .baseUri("https://api.example.com")
    .when()
        .get("/users/1")
    .then()
        .matchesJsonSchemaInClasspath("schemas/user-schema.json");
}
```

---

## 👥 Team

| Role | Name |
|------|------|
| **Tester** | Athenkosi Afikile Breakfast |

---

## 🤝 Contributing

1. Create a new branch for your feature
2. Write tests following the existing structure
3. Ensure all tests pass before committing
4. Push to the repository and create a pull request

---

## 📧 Contact & Support

For issues, questions, or contributions, please open an issue in the repository.

---

## 📄 License

This project is licensed under the MIT License.

---

**Happy Testing! 🚀**
