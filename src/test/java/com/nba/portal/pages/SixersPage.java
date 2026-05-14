package com.nba.portal.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SixersPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By slidesLocator = By.xpath("//button[@data-testid='content-hero-navigation-button' and not(contains(@class, 'hidden'))]");
    private final By slideTitleLocator = By.xpath(".//*[contains(@class, 'ButtonTitle')]");
    private final By body = By.tagName("body");

    public SixersPage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
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

    public boolean currentUrlContains(String expectedUrlPart) {
        return wait.until(webDriver -> webDriver.getCurrentUrl().toLowerCase()
                .contains(expectedUrlPart.toLowerCase()));
    }

    public boolean pageContainsText(String expectedText) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(body))
                .getText()
                .toLowerCase()
                .contains(expectedText.toLowerCase());
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

        // Validate the titles available on the page against the external JSON test data.
        int titlesToValidate = Math.min(actualTitles.size(), expectedTitles.size());
        Assert.assertTrue(titlesToValidate > 0, "Expected slide title test data is empty.");

        for (int i = 0; i < titlesToValidate; i++) {
            Assert.assertEquals(actualTitles.get(i), expectedTitles.get(i),
                    "Slide title mismatch at index " + i);
        }
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

    private void clickIfPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                elements.get(0).click();
            }
        } catch (Exception ignored) {
        }
    }

    private void waitForPageReady() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
    }
}
