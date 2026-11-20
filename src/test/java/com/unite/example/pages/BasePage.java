package com.unite.example.pages;

import com.unite.example.config.TestConfig;
import com.unite.example.pages.components.CookieBanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Parent of all page objects: shared behaviour lives here.
 *
 * Page Object Model (POM) in one sentence: each page gets a class that knows its
 * locators and actions, so tests read like business steps and a UI change is fixed
 * in one file instead of in every test.
 *
 * Waiting: we use EXPLICIT waits only (wait for a specific condition). Never Thread.sleep,
 * and never mix implicit and explicit waits: that makes timing unpredictable.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private final CookieBanner cookies;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.timeoutSeconds()));
        this.cookies = new CookieBanner(driver);
    }

    /** Path of the page relative to the base URL, e.g. "/". */
    protected abstract String path();

    public void open() {
        driver.get(TestConfig.baseUrl() + path());
        cookies.acceptIfShown();
    }

    /** Wait until the element is visible, then return it. */
    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Wait until the element is clickable, then click it. */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public String title() {
        return driver.getTitle();
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
