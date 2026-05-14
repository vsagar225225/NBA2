package com.nba.portal.hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverFactory;
import com.nba.portal.driver.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void setUp() {
        ConfigReader config = new ConfigReader();
        String browser = config.get("browser", "chrome");
        boolean headless = config.getBoolean("headless", false);

<<<<<<< HEAD
=======
        // Create a fresh browser for every scenario and store it for step/page classes.
>>>>>>> ea37d4f (Updated project with latest changes)
        WebDriver driver = DriverFactory.createDriver(browser, headless);
        DriverManager.setDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
<<<<<<< HEAD
=======
            // Attach failure screenshots directly to the Cucumber HTML report.
>>>>>>> ea37d4f (Updated project with latest changes)
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failed-screenshot");
        }

<<<<<<< HEAD
=======
        // Always close the browser, even when a scenario fails.
>>>>>>> ea37d4f (Updated project with latest changes)
        DriverManager.quitDriver();
    }
}
