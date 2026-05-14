package com.nba.portal.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverFactory;
import com.nba.portal.driver.DriverManager;

public class BaseTest {
    protected ConfigReader config;

    @BeforeMethod
    public void setUp() {
        config = new ConfigReader();
        String browser = config.get("browser", "chrome");
        boolean headless = config.getBoolean("headless", false);

        WebDriver driver = DriverFactory.createDriver(browser, headless);
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
