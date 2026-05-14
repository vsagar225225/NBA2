package com.nba.portal.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.NbaHomePage;

public class NbaPortalSmokeTest extends BaseTest {

    @Test(description = "Open the NBA portal and verify the home page loads")
    public void verifyNbaHomePageLoads() {
        String baseUrl = config.get("baseUrl", "https://www.nba.com");
        int timeoutSeconds = config.getInt("timeoutSeconds", 20);

        NbaHomePage homePage = new NbaHomePage(DriverManager.getDriver(), timeoutSeconds);
        homePage.open(baseUrl);

        Assert.assertTrue(homePage.isLoaded(), "NBA portal home page did not load correctly.");
        Assert.assertTrue(homePage.getPageTitle().toLowerCase().contains("nba"),
                "Expected page title to contain NBA. Actual title: " + homePage.getPageTitle());
    }
}
