package com.nba.portal.steps;

import org.testng.Assert;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.NbaHomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class NbaPortalSteps {
    private final ConfigReader config = new ConfigReader();
    private final NbaHomePage nbaHomePage;

    public NbaPortalSteps() {
        int timeoutSeconds = config.getInt("timeoutSeconds", 20);
        nbaHomePage = new NbaHomePage(DriverManager.getDriver(), timeoutSeconds);
    }

    @Given("I open the NBA portal home page")
    public void iOpenTheNbaPortalHomePage() {
        nbaHomePage.open(config.get("baseUrl", "https://www.nba.com"));
    }

    @Given("I open the NBA portal page {string}")
    public void iOpenTheNbaPortalPage(String pagePath) {
        String baseUrl = config.get("baseUrl", "https://www.nba.com");
        nbaHomePage.open(baseUrl + pagePath);
    }

    @Then("the NBA portal should load successfully")
    public void theNbaPortalShouldLoadSuccessfully() {
        Assert.assertTrue(nbaHomePage.isLoaded(), "NBA portal did not load successfully.");
    }

    @Then("the page url should contain {string}")
    public void thePageUrlShouldContain(String expectedUrlPart) {
        Assert.assertTrue(nbaHomePage.currentUrlContains(expectedUrlPart),
                "Expected URL to contain: " + expectedUrlPart);
    }

    @Then("the page should show {string}")
    public void thePageShouldShow(String expectedText) {
        Assert.assertTrue(nbaHomePage.pageContainsText(expectedText),
                "Expected page to show text: " + expectedText);
    }
}
