package com.unite.example.tests;

import com.unite.example.data.TestData;
import com.unite.example.pages.CityPage;
import com.unite.example.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Tags let the pipeline choose which tests to run:  mvn test -Dgroups=smoke
class HomePageTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Homepage loads with the expected title and main heading")
    void loadsWithTitleAndHeading() {
        HomePage home = new HomePage(driver);
        home.open();

        assertTrue(home.title().toLowerCase().contains(TestData.HOME_TITLE_TEXT), "Unexpected title: " + home.title());
        assertTrue(home.headingText().contains(TestData.HOME_HEADING_TEXT), "Unexpected heading: " + home.headingText());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Homepage shows the main navigation and search options")
    void showsNavigationAndSearchOptions() {
        HomePage home = new HomePage(driver);
        home.open();

        assertTrue(home.citiesMenuVisible(), "Cities menu not visible");
        assertTrue(home.exploreAccommodationVisible(), "Explore accommodation link not visible");
        assertTrue(home.searchOptionsVisible(), "Search options not visible");
    }

    @Test
    @Tag("regression")
    @DisplayName("User can navigate to Bristol from the Cities menu")
    void navigatesToBristolFromCitiesMenu() {
        HomePage home = new HomePage(driver);
        home.open();

        CityPage bristol = home.goToBristol();

        assertTrue(bristol.headingText().toUpperCase().contains(TestData.BRISTOL_HEADING_TEXT), "Unexpected heading: " + bristol.headingText());
    }
}
