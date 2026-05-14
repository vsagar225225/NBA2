package com.nba.portal.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NbaHomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By body = By.tagName("body");
    private final By acceptCookiesButton = By.xpath("//button[contains(., 'Accept') or contains(., 'I Accept')]");

    public NbaHomePage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(body));
        closeCookieBannerIfPresent();
    }

    public boolean isLoaded() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        String title = driver.getTitle().toLowerCase();
        String bodyText = getBodyText().toLowerCase();

        return currentUrl.contains("nba.com")
                && (title.contains("nba") || bodyText.contains("nba"));
    }

    public boolean currentUrlContains(String expectedUrlPart) {
        return wait.until(webDriver -> webDriver.getCurrentUrl().toLowerCase()
                .contains(expectedUrlPart.toLowerCase()));
    }

    public boolean pageContainsText(String expectedText) {
        return getBodyText().toLowerCase().contains(expectedText.toLowerCase());
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    private String getBodyText() {
        WebElement bodyElement = wait.until(ExpectedConditions.presenceOfElementLocated(body));
        return bodyElement.getText();
    }

    private void closeCookieBannerIfPresent() {
        try {
            WebElement button = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
            button.click();
        } catch (Exception ignored) {
            // The banner does not appear every time, so this is intentionally optional.
        }
    }
}
