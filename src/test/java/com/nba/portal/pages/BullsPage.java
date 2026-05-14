package com.nba.portal.pages;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BullsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final List<String> footerLinks = new ArrayList<>();

    private final By footerLinksLocator = By.cssSelector("footer a");
    private final By body = By.tagName("body");

    public BullsPage(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public void openHomePage(String baseUrl) {
        driver.get(baseUrl + "/bulls/");
        waitForPageReady();
        closePopupsIfPresent();
    }

    public boolean isLoaded() {
        waitForPageReady();
        return currentUrlContains("nba.com") && pageContainsText("Bulls");
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

    public void scrollToFooter() {
        // Footer links load near the bottom of the page, so scroll before collecting them.
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
    }

    public int extractFooterLinks() {
        footerLinks.clear();
        List<WebElement> linkElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(footerLinksLocator));

        for (WebElement element : linkElements) {
            String href = element.getAttribute("href");
            if (href != null && !href.trim().isEmpty()) {
                footerLinks.add(href.trim());
            }
        }

        return footerLinks.size();
    }

    public Path saveLinksToCsv() {
        try {

            // Store extracted links under target so generated files stay out of source control.
            Path targetDirectory = Path.of("target");
            Files.createDirectories(targetDirectory);
            Path csvPath = targetDirectory.resolve("footer_links.csv");

            List<String> lines = new ArrayList<>();
            lines.add("Link");
            lines.addAll(footerLinks);
            Files.write(csvPath, lines, StandardCharsets.UTF_8);

            return csvPath;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save footer links CSV.", exception);
        }
    }

    public Set<String> getDuplicateLinks() {
        Set<String> uniqueLinks = new HashSet<>();
        Set<String> duplicateLinks = new HashSet<>();


        // A link is duplicate when it cannot be added to the unique set.
        for (String link : footerLinks) {
            if (!uniqueLinks.add(link)) {
                duplicateLinks.add(link);
            }
        }

        return duplicateLinks;
    }

    private void closePopupsIfPresent() {
        clickIfPresent(By.id("onetrust-accept-btn-handler"));
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
