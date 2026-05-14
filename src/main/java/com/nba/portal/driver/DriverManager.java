package com.nba.portal.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
<<<<<<< HEAD
=======
    // ThreadLocal keeps each execution thread's browser isolated if parallel runs are enabled later.
>>>>>>> ea37d4f (Updated project with latest changes)
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
<<<<<<< HEAD
=======
            // Remove the reference after quitting to avoid stale drivers and memory leaks.
>>>>>>> ea37d4f (Updated project with latest changes)
            DRIVER.remove();
        }
    }
}
