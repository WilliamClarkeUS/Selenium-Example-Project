package com.unite.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** The unitestudents.com homepage. */
public class HomePage extends BasePage {

    // Locators are defined once, as constants.
    // Preference order: id / data-testid (stable) > CSS > link text > XPath (brittle).
    private static final By HEADING = By.cssSelector("h1");
    private static final By CITIES_MENU = By.xpath("//nav//a[normalize-space()='Cities']");
    private static final By EXPLORE_LINK = By.xpath("//nav//a[normalize-space()='Explore accommodation']");
    // data-testid attributes are put there for tests: the most stable option.
    private static final By LENGTH_OF_STAY = By.cssSelector("[data-testid='hero-length-of-stay-popover-button']");
    private static final By ACADEMIC_YEAR = By.cssSelector("[data-testid='hero-academic-year-popover-button']");
    private static final By BRISTOL_LINK = By.xpath("//nav//a[@href='/student-accommodation/bristol']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String path() {
        return "/";
    }

    public String headingText() {
        return visible(HEADING).getText();
    }

    public boolean citiesMenuVisible() {
        return visible(CITIES_MENU).isDisplayed();
    }

    public boolean exploreAccommodationVisible() {
        return visible(EXPLORE_LINK).isDisplayed();
    }

    public boolean searchOptionsVisible() {
        return visible(LENGTH_OF_STAY).isDisplayed() && visible(ACADEMIC_YEAR).isDisplayed();
    }

    /** Open the Cities menu and choose Bristol. Returns the page we land on. */
    public CityPage goToBristol() {
        click(CITIES_MENU);
        click(BRISTOL_LINK);
        wait.until(ExpectedConditions.urlContains("/student-accommodation/bristol"));
        return new CityPage(driver, "bristol");
    }
}
