package com.unite.example.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Cookie consent banner (OneTrust).
 *
 * A "component" is a reusable piece of UI that appears on many pages. One class means
 * a change to the banner is fixed in ONE place, not in every test.
 */
public class CookieBanner {

    private static final By ACCEPT = By.id("onetrust-accept-btn-handler");

    private final WebDriver driver;

    public CookieBanner(WebDriver driver) {
        this.driver = driver;
    }

    /** Accept cookies if the banner is shown. Safe to call when it is not. */
    public void acceptIfShown() {
        try {
            // Short wait: the banner appears a moment after the page loads.
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(ACCEPT))
                .click();
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated(ACCEPT));
        } catch (TimeoutException noBannerThisTime) {
            // No banner (e.g. already accepted): nothing to do.
        }
    }
}
