package com.nba.portal.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private final By body = By.tagName("body");

    protected BasePage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    protected void waitForPageReady() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));
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

    protected void clickIfPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                elements.get(0).click();
            }
        } catch (Exception ignored) {
        }
    }
}
