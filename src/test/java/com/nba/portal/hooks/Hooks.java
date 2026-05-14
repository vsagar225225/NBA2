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

        WebDriver driver = DriverFactory.createDriver(browser, headless);
        DriverManager.setDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failed-screenshot");
        }

        DriverManager.quitDriver();
    }
}
