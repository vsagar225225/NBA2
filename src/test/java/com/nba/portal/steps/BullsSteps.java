package com.nba.portal.steps;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.testng.Assert;

import com.nba.portal.config.ConfigReader;
import com.nba.portal.driver.DriverManager;
import com.nba.portal.pages.BullsPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BullsSteps {
    private final ConfigReader config = new ConfigReader();
    private BullsPage bullsPage;
    private int totalFooterLinks;
    private Path footerLinksCsvPath;
    private Set<String> duplicateFooterLinks;

    @Given("I navigate to the Bulls home page")
    public void iNavigateToTheBullsHomePage() {
        bullsPage = new BullsPage(DriverManager.getDriver(), config.getInt("timeoutSeconds", 20));
        bullsPage.openHomePage(config.get("baseUrl", "https://www.nba.com"));
    }

    @Then("the Bulls page should load successfully")
    public void theBullsPageShouldLoadSuccessfully() {
        Assert.assertTrue(bullsPage.isLoaded(), "Bulls page did not load successfully.");
        Assert.assertTrue(bullsPage.currentUrlContains("bulls"), "Expected URL to contain bulls.");
        Assert.assertTrue(bullsPage.pageContainsText("Bulls"), "Expected page to show Bulls.");
    }

    @When("I scroll to the footer section")
    public void iScrollToTheFooterSection() {
        bullsPage.scrollToFooter();
    }

    @Then("I extract all links from categories")
    public void iExtractAllLinksFromCategories() {
        totalFooterLinks = bullsPage.extractFooterLinks();
        System.out.println("Total Bulls footer links: " + totalFooterLinks);
        Assert.assertTrue(totalFooterLinks > 0, "Expected at least one Bulls footer link.");
    }

    @Then("I save the links to a CSV file")
    public void iSaveTheLinksToACsvFile() {
        footerLinksCsvPath = bullsPage.saveLinksToCsv();
        Assert.assertTrue(Files.exists(footerLinksCsvPath), "Footer links CSV was not created.");
    }

    @Then("I detect and report duplicate links")
    public void iDetectAndReportDuplicateLinks() {
        duplicateFooterLinks = bullsPage.getDuplicateLinks();
        System.out.println("Duplicate Bulls footer links found: " + duplicateFooterLinks.size());
    }
}
