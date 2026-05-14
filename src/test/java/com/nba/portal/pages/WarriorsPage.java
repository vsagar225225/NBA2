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

    public WarriorsPage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public void openHomePage(String baseUrl) {
        driver.get(baseUrl + "/warriors");
        waitForPageReady();
        closePopupsIfPresent();
    }

    public void navigateToNewsAndFeatures() {
        try {
            WebElement moreElement = wait.until(ExpectedConditions.visibilityOfElementLocated(moreMenu));
            new Actions(driver).moveToElement(moreElement).perform();
            wait.until(ExpectedConditions.elementToBeClickable(newsAndFeaturesMenu)).click();
        } catch (Exception exception) {
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
