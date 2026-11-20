package com.unite.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * A city landing page, e.g. /student-accommodation/bristol.
 * Takes the city slug, so the same class works for Leeds, Bath, etc.
 */
public class CityPage extends BasePage {

    private static final By HEADING = By.cssSelector("h1");
    private static final By FILTER_BAR = By.cssSelector("[data-testid='city-properties-filter-bar']");
    private static final By SORT_BUTTONS = By.cssSelector("[data-testid='sort-options-button']");
    private static final By PROPERTY_CARDS = By.cssSelector("[data-testid='property-card']");
    private static final By FIRST_PROPERTY_LINK = By.cssSelector("[data-testid='property-card'] a");

    private final String citySlug;

    public CityPage(WebDriver driver, String citySlug) {
        super(driver);
        this.citySlug = citySlug;
    }

    @Override
    protected String path() {
        return "/student-accommodation/" + citySlug;
    }

    public String headingText() {
        return visible(HEADING).getText();
    }

    public boolean filterBarVisible() {
        return visible(FILTER_BAR).isDisplayed();
    }

    /**
     * The page has TWO sort buttons with the same test id (one is hidden at this screen
     * size). So we check that at least one is actually displayed to the user.
     */
    public boolean sortButtonVisible() {
        wait.until(ExpectedConditions.presenceOfElementLocated(SORT_BUTTONS));
        return driver.findElements(SORT_BUTTONS).stream().anyMatch(WebElement::isDisplayed);
    }

    public int propertyCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROPERTY_CARDS));
        List<WebElement> cards = driver.findElements(PROPERTY_CARDS);
        return cards.size();
    }

    /** Click the first property in the list. */
    public void openFirstProperty() {
        click(FIRST_PROPERTY_LINK);
    }

    /** Wait until we have left the city page for a property page. */
    public boolean onPropertyPage() {
        return wait.until(ExpectedConditions.urlMatches(".*/student-accommodation/" + citySlug + "/.+"));
    }
}
