# NBA Portal Automation

Simple Selenium Cucumber automation project for the NBA portal.

## Tech Stack

- Java 11
- Maven
- Selenium WebDriver
- Cucumber
- TestNG

## Run From Eclipse

1. Open Eclipse.
2. Go to `File > Import > Maven > Existing Maven Projects`.
3. Select this folder: `C:\Sagar\Selenium\NBA_Portal_Automation`.
4. Wait for Maven dependencies to finish downloading.
5. Open `src/test/resources/testng.xml`.
6. Right click the file and choose `Run As > TestNG Suite`.

You can also run the Cucumber runner directly:

`src/test/java/com/nba/portal/runners/CucumberTestRunner.java`

Right click the class and choose `Run As > TestNG Test`.

## Feature Files

Feature files are available in:

`src/test/resources/features`

Current features:

<<<<<<< HEAD
- `NbaHome.feature`
=======
>>>>>>> ea37d4f (Updated project with latest changes)
- `Warriors.feature`
- `Sixers.feature`
- `Bulls.feature`

<<<<<<< HEAD
=======
Step definition files are aligned with feature files:

- `WarriorsSteps.java`
- `SixersSteps.java`
- `BullsSteps.java`

Page object files are aligned with team features:

- `WarriorsPage.java`
- `SixersPage.java`
- `BullsPage.java`

>>>>>>> ea37d4f (Updated project with latest changes)
## Configuration

Update values in:

`src/main/resources/config.properties`

Available settings:

- `browser=chrome`
- `baseUrl=https://www.nba.com`
- `headless=false`
- `timeoutSeconds=20`

## Maven Command

From this project folder:

```bash
mvn test
```

Run all tests in headless mode:

```bash
mvn test -Dheadless=true
```

Run tests separately in headless mode:

```bash
<<<<<<< HEAD
mvn test -Dheadless=true -Dcucumber.filter.tags="@home"
=======
>>>>>>> ea37d4f (Updated project with latest changes)
mvn test -Dheadless=true -Dcucumber.filter.tags="@warriors"
mvn test -Dheadless=true -Dcucumber.filter.tags="@sixers"
mvn test -Dheadless=true -Dcucumber.filter.tags="@bulls"
```
<<<<<<< HEAD
=======

## Reports

Open the Cucumber HTML report after execution:

`target/cucumber-report.html`
>>>>>>> ea37d4f (Updated project with latest changes)
