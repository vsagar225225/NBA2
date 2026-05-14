package com.nba.portal.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class SixersPage extends BasePage {

    private final By slidesLocator = By.xpath("//button[@data-testid='content-hero-navigation-button' and not(contains(@class, 'hidden'))]");
    private final By slideTitleLocator = By.xpath(".//*[contains(@class, 'ButtonTitle')]");

    public SixersPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void openHomePage(String baseUrl) {
        driver.get(baseUrl + "/sixers/");
        waitForPageReady();
        closePopupsIfPresent();
    }

    public boolean isLoaded() {
        waitForPageReady();
        return currentUrlContains("nba.com") && pageContainsText("76ers");
    }

    public int getSlideCount() {
        return getSlides().size();
    }

    public List<String> getSlideTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement slide : getSlides()) {
            List<WebElement> titleElements = slide.findElements(slideTitleLocator);
            if (!titleElements.isEmpty()) {
                titles.add(titleElements.get(0).getText().trim());
            }
        }
        return titles;
    }

    public void validateSlideTitles(List<String> expectedTitles) {
        List<String> actualTitles = getSlideTitles();
        Assert.assertFalse(actualTitles.isEmpty(), "No Sixers slide titles were found.");

        // The Sixers carousel content changes frequently on the live site. We still validate that:
        // 1) Titles are present (non-empty), and
        // 2) At least one current title matches the expected-data list (used as an allowlist / smoke check).
        for (int i = 0; i < actualTitles.size(); i++) {
            Assert.assertFalse(actualTitles.get(i).isBlank(), "Slide title is blank at index " + i);
        }

        Assert.assertFalse(expectedTitles.isEmpty(), "Expected slide title test data is empty.");

        boolean foundMatch = false;
        for (String actualTitle : actualTitles) {
            for (String expectedTitle : expectedTitles) {
                if (actualTitle.equalsIgnoreCase(expectedTitle)) {
                    foundMatch = true;
                    break;
                }
            }
            if (foundMatch) {
                break;
            }
        }

        Assert.assertTrue(foundMatch,
                "None of the current slide titles matched expected data. Actual: " + actualTitles);
    }

    public void validateSlideDuration(long expectedDurationSeconds) {
        List<WebElement> slides = getSlides();
        Assert.assertFalse(slides.isEmpty(), "No Sixers slides were found.");

        WebElement activeSlide = findActiveSlide(slides);
        wait.until(ExpectedConditions.attributeToBe(activeSlide, "aria-selected", "true"));

        // Measure how long the active carousel slide remains selected.
        long startTime = System.currentTimeMillis();
        wait.until(ExpectedConditions.attributeToBe(activeSlide, "aria-selected", "false"));
        long actualDurationSeconds = (System.currentTimeMillis() - startTime) / 1000;

        Assert.assertTrue(actualDurationSeconds > 0, "Slide duration should be greater than 0 seconds.");
        Assert.assertTrue(Math.abs(actualDurationSeconds - expectedDurationSeconds) <= 5,
                "Slide duration " + actualDurationSeconds + "s does not match expected ~"
                        + expectedDurationSeconds + "s");
    }

    private WebElement findActiveSlide(List<WebElement> slides) {
        // The active slide is marked by aria-selected=true in the carousel controls.
        for (WebElement slide : slides) {
            if ("true".equals(slide.getAttribute("aria-selected"))) {
                return slide;
            }
        }
        return slides.get(0);
    }

    private List<WebElement> getSlides() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(slidesLocator));
    }

    private void closePopupsIfPresent() {
        clickIfPresent(By.id("onetrust-accept-btn-handler"));
        clickIfPresent(By.id("sixers-close"));
    }
}
