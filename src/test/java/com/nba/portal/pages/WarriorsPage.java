package com.nba.portal.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WarriorsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By moreMenu = By.xpath("//span[text()='...']/parent::button | //button[contains(@aria-label, 'More')]");
    private final By newsAndFeaturesMenu = By.xpath("//a[contains(@title,'News') or contains(.,'News & Features')]");
    private final By videoFeeds = By.cssSelector("[data-testid='tile-article']");
    private final By videoDuration = By.cssSelector("time span, time");
<<<<<<< HEAD
=======
    private final By body = By.tagName("body");
>>>>>>> ea37d4f (Updated project with latest changes)

    public WarriorsPage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public void openHomePage(String baseUrl) {
        driver.get(baseUrl + "/warriors");
        waitForPageReady();
        closePopupsIfPresent();
    }

<<<<<<< HEAD
=======
    public boolean isLoaded() {
        waitForPageReady();
        return currentUrlContains("nba.com") && pageContainsText("Warriors");
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

>>>>>>> ea37d4f (Updated project with latest changes)
    public void navigateToNewsAndFeatures() {
        try {
            WebElement moreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(moreMenu));
            new Actions(driver).moveToElement(moreElement).perform();
            wait.until(ExpectedConditions.elementToBeClickable(newsAndFeaturesMenu)).click();
        } catch (Exception exception) {
<<<<<<< HEAD
=======
            // Direct URL fallback keeps the validation stable if the responsive menu changes.
>>>>>>> ea37d4f (Updated project with latest changes)
            driver.get("https://www.nba.com/warriors/news");
        }

        waitForPageReady();
        closePopupsIfPresent();
    }

    public int getTotalVideoFeeds() {
        List<WebElement> videos = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(videoFeeds));
        return videos.size();
    }

    public int getVideosWithDurationAtLeastDays(int expectedDays) {
        int count = 0;
        List<WebElement> videos = driver.findElements(videoFeeds);

<<<<<<< HEAD
=======
        // Each feed card can contain timestamp text like "3d"; count cards meeting the age rule.
>>>>>>> ea37d4f (Updated project with latest changes)
        for (WebElement video : videos) {
            List<WebElement> durations = video.findElements(videoDuration);
            for (WebElement duration : durations) {
                String text = duration.getText().trim().toLowerCase();
                if (isAtLeastDaysOld(text, expectedDays)) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    private boolean isAtLeastDaysOld(String text, int expectedDays) {
        if (!text.contains("d")) {
            return false;
        }

        String digits = text.replaceAll("[^0-9]", "");
        return !digits.isEmpty() && Integer.parseInt(digits) >= expectedDays;
    }

    private void closePopupsIfPresent() {
<<<<<<< HEAD
=======
        // Popups are optional and vary by session, so they are handled defensively.
>>>>>>> ea37d4f (Updated project with latest changes)
        clickIfPresent(By.id("onetrust-accept-btn-handler"));
        clickIfPresent(By.xpath("//div[contains(@class, 'hover:cursor-pointer') and text()='x']"));
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
