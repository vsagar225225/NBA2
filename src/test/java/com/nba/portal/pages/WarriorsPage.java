package com.nba.portal.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WarriorsPage extends BasePage {

    private final By moreMenu = By.xpath("//span[text()='...']/parent::button | //button[contains(@aria-label, 'More')]");
    private final By newsAndFeaturesMenu = By.xpath("//a[contains(@title,'News') or contains(.,'News & Features')]");
    private final By videoFeeds = By.cssSelector("[data-testid='tile-article']");
    private final By videoDuration = By.cssSelector("time span, time");

    public WarriorsPage(WebDriver driver, int timeoutSeconds) {
        super(driver, timeoutSeconds);
    }

    public void openHomePage(String baseUrl) {
        driver.get(baseUrl + "/warriors");
        waitForPageReady();
        closePopupsIfPresent();
    }

    public boolean isLoaded() {
        waitForPageReady();
        return currentUrlContains("nba.com") && pageContainsText("Warriors");
    }

    public void navigateToNewsAndFeatures() {
        try {
            WebElement moreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(moreMenu));
            new Actions(driver).moveToElement(moreElement).perform();
            wait.until(ExpectedConditions.elementToBeClickable(newsAndFeaturesMenu)).click();
        } catch (Exception exception) {
            // Direct URL fallback keeps the validation stable if the responsive menu changes.
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

        // Each feed card can contain timestamp text like "3d"; count cards meeting the age rule.
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
        // Popups are optional and vary by session, so they are handled defensively.
        clickIfPresent(By.id("onetrust-accept-btn-handler"));
        clickIfPresent(By.xpath("//div[contains(@class, 'hover:cursor-pointer') and text()='x']"));
    }
}
