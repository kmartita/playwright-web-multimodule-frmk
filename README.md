![Java](https://img.shields.io/badge/java-white?style=for-the-badge&logo=openjdk&logoSize=auto&color=%23e69138&cacheSeconds=3600&link=https%3A%2F%2Fwww.oracle.com%2Fjava%2F)
![Maven](https://img.shields.io/badge/maven-white?style=for-the-badge&logo=apachemaven&logoSize=auto&color=%23cc0000&cacheSeconds=3600&link=https%3A%2F%2Fmaven.apache.org)
![Playwright](https://img.shields.io/badge/playwright-white?style=for-the-badge&logoSize=auto&labelColor=%23999999&color=%236aa84f&cacheSeconds=3600&link=https%3A%2F%2Fplaywright.dev%2Fjava%2F)
![TestNG](https://img.shields.io/badge/testng-white?style=for-the-badge&logoSize=auto&color=%233d85c6&cacheSeconds=3600&link=https%3A%2F%2Ftestng.org)
![Allure Report](https://img.shields.io/badge/allure-white?style=for-the-badge&logoSize=auto&color=%23f1c232&cacheSeconds=3600&link=https%3A%2F%2Fallurereport.org)

# Web Automation Demo Project: Playwright
This multi-module Java framework for test automation has been built with Maven and utilizes Playwright for browser automation, providing fast, reliable, and headless execution of web tests with detailed reporting and integration capabilities.

### Tech Stack:
- **Programming Language**: Java
- **Build Tool**: Maven
- **UI Automation Framework**: Playwright
- **Testing Framework**: TestNG
- **Reporting**: Allure Report

### Requirements:
Requires **Java 17**, **Maven 3.9.x**, and **Allure Report 2.33.x** to be installed and properly configured on your local machine.<br/>


## Table of Contents
1. [Framework Structure](#one)
   * 1.1. [Configuring project](#one-one)
2. [Page Object Pattern for Playwright](#two)
3. [Tests Execution](#three)
4. [Generate Allure Report](#four)


<a id="one"></a>
### 1. Framework Structure
The framework is organized as a Maven multi-module project, consisting of three main modules: `module-app`, `module-tests`, and `module-tools`. 
Each module serves a specific purpose and the dependencies are arranged to ensure proper relationships and integration between them, forming a cohesive test automation framework.<br/>
```text
|———core-frmk
    |—-pom.xml
    |—-config
        |—-.env
    |—-module-app
        |—-pom.xml
        |—-src
            |—-main
                |—-java
    |—-module-tests
        |—-pom.xml
        |—-src
            |—-test
                |—-java
                    |—-resources
                        |—-testng.xml
                        |—-allure.properties
    |—-module-tools
        |—-pom.xml
        |—-src
            |—-main
               |—-java
```
1. **Root Module (`core-frmk`)**. The parent project that defines shared dependencies, plugin configurations, and manages overall builds. Contains the `config` directory, where environment variables (`.env` files of various extensions) are stored for easy configuration management (e.g., `BASE_URL`).<br/>
2. **Modules**:
   - `module-app`. The main module containing page object classes, UI components, and core application logic. It depends on **module-tools** for shared utilities and design pattern implementations.<br/>
   - `module-tools`. A utility module providing shared helpers, including support for browser configuration and Playwright integration, scalable Page Object pattern implementations, report generation, logging, and other common functions used across modules.<br/>
   - `module-tests`. This module contains test classes and test configurations (`testng.xml`). It depends on **module-app** to access page objects and perform web UI testing in a controlled environment.<br/>

<a id="one-one"></a>
#### 1.1. Configuring project
Run this command from the start to ensure that you don't have anything corrupted.<br/>
```bash
cd core-frmk
mvn clean compile -U
```

<a id="two"></a>
### 2. Page Object Pattern for Playwright
This framework implements a scalable, declarative architecture for building Page Objects in Playwright with Java.
It uses custom annotations to systematically define locators and components, which helps create clean, readable, and maintainable test code, especially suitable for large-scale web applications.<br/>

<img width="1880" height="1640" alt="page-design" src="https://github.com/user-attachments/assets/f2af744f-fcb9-45f0-8c6b-f4bb4b8e4349" />

#### Annotations:
`@UiPage` : marks a class as a Page Object representing a web page. It supports hierarchical nesting of frames.<br/>
`@UiComponent` : defines a container locator scope. All child elements and locators are searched within this container.<br/>
`@UiFind` : specifies locators (CSS or XPath) for elements or lists of elements. Locators can be scoped to either the page or a component container.<br/>

#### How it works:
##### 1. Defining a Page Object:
Simply annotate your page class with `@UiPage`.<br/>
```java
@UiPage
public class HomePage { }
```

##### 2. Declaring Locators:
Create class fields with your chosen locators and annotate with `@UiFind`. You can declare single locator or lists of locators:<br/>
```java
@UiPage
public class HomePage {

    @UiFind("#some-id")
    private Locator element;

    @UiFind(".list-item")
    private List<Locator> elements;
}
```
This is equivalent to Playwright's implementation:<br/>
```java
Locator element = page.locator("#some-id");
List<Locator> elements = page.locator(".list-item").all();
```

`@UiFind` accepts any Playwright [Selectors](https://playwright.dev/docs/locators#locate-by-css-or-xpath).<br/>

##### 3. Working with Iframes:
Specify [frame](https://playwright.dev/java/docs/frames) selectors if the page contains iframes.<br/>
```java
@UiPage(frame = {".iframe-foo"})
public class PageWithIframe {

    @UiFind("#iframe-button")
    public Locator myButton;
}
```
This is equivalent to Playwright's implementation:<br/>
```java
Locator myButton = page.frameLocator(".iframe-foo").locator("#iframe-button");
```

Locators inside a class annotated with `@UiPage(frame = {...})` are scoped to that iframe. Nested iframes can be accessed by passing multiple parameters to frame.<br/>

##### 4. Defining a Component Object:
You can define a locator that is scoped under a parent locator, which itself can be a `@UiComponent`. For example:<br/>
```java
@UiComponent("#parent")
public class Header {

    @UiFind(".child")
    public Locator element;

    @UiFind(".list-item")
    public List<Locator> elements;
}
```
This is equivalent to Playwright's implementation:<br/>
```java
Locator element = page.locator("#parent").locator(".child");
List<Locator> elements = page.locator("#parent").locator(".list-item").all();
```

##### 5. Creating and Using Page / Component Objects:
Use the `PageLoader` to instantiate your Page Object and pass your Playwright [Page](https://playwright.dev/java/docs/pages) instance:<br/>
```java
HomePage homePage = PageLoader.initPages(HomePage.class, page);
```
Or your Component Object:<br/>
```java
Header header = PageLoader.initComponents(Header.class, page);
```
The `PageLoader` can instantiate any page or component class that has a constructor accepting only a Playwright Page. 
Please refer to `AbstractPage` and `AbstractComponent` respectively.<br/>

<a id="three"></a>
### 3. Tests Execution
Maven is used as the build and test management tool, with additional options for test configuration:<br/>
`-Denv={String}`  specifies the environment for test execution (default: `test`)<br/>
`-Dbrowser={String}` - defines the browser to run tests on (default: `chrome`)<br/>
`-Dheadless={boolean}` - sets the mode of test execution in the graphical interface or without it (default: `false`)<br/>
`-Dthreads={int}` -  specifies the number of threads for parallel test execution<br/>
`-Dtest={String}` - the specific test class to run<br/>

#### Supported Browsers:
* Chromium
* Firefox
* WebKit

#### Common Maven Commands:
1. Removes the `target` directory before running tests. Ensures that previous results do not affect the Allure report.
```bash
mvn clean
```
2. Runs tests defined in your `testng.xml`, using default system property settings (e.g., env, browser). By default, it executes with Chrome.<br/>
```bash
mvn test
```

####  Note:
1. Before running the commands, go to the `core-frmk` directory.
2. Create an environment variable file inside the `config` directory (`.env.test`, `.env.dev`, etc.) containing the `BASE_URL` parameter to configure the web URL for testing. This will allow you to easily manage the base URL used during your web tests.<br/>
```properties
BASE_URL = https://example.com
```

#### Usage examples:
To execute a specific test with default settings the next command line should be used:<br/>
```bash
mvn clean test -Dtest=VerifyHomePageTest
```

To execute a test with custom options the next command line should be used:<br/>
```bash
mvn clean test -Dtest=VerifyComponentTest -Dbrowser=firefox -Dheadles=true -Denv=dev
```

To execute tests in parallel with `testng.xml` the next command line should be used:<br/>
```bash
mvn clean test -Dthreads=3
```

<a id="four"></a>
### 4. Generate Allure Report
To generate a report by Allure after tests have finished, use the following command:<br/>
```bash
mvn allure:report
```
The report can be found in the root folder of a project. The folder named `allure-report` contains the generated report. Just open the `index.html` file in a browser.<br/>

Alternatively, Allure provides a command to serve the report directly:<br/>
```bash
mvn allure:serve 
```
This command starts a local web server and automatically opens the generated report in your default browser.<br/>

#### Allure Report Overview:
An example of the generated [Allure TestNG](https://allurereport.org/docs/testng/) report looks like this:<br/>
<img width="1568" height="808" alt="overview" src="https://github.com/user-attachments/assets/a2054ad7-9e09-477e-a7ea-5a8662c13735" />

Attached screenshot for failed test:<br/>
<img width="1568" height="949" alt="failed" src="https://github.com/user-attachments/assets/8542e343-8b0d-4efc-bc1e-3267b90de4d8" />

All steps are annotated:<br/>
<img width="1568" height="668" alt="passed" src="https://github.com/user-attachments/assets/c0783d03-2b5f-4df8-ab5e-29c18f8caedc" />
