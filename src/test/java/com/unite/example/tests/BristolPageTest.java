package com.unite.example.tests;

import com.unite.example.pages.CityPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BristolPageTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Bristol page loads with the Bristol heading and title")
    void loadsWithHeadingAndTitle() {
        CityPage bristol = new CityPage(driver, "bristol");
        bristol.open();

        assertTrue(bristol.title().contains("Bristol"), "Unexpected title: " + bristol.title());
        assertTrue(bristol.headingText().toUpperCase().contains("BRISTOL"), "Unexpected heading: " + bristol.headingText());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Bristol page lists residences with filters and sorting")
    void listsResidencesWithFiltersAndSorting() {
        CityPage bristol = new CityPage(driver, "bristol");
        bristol.open();

        assertTrue(bristol.filterBarVisible(), "Filter bar not visible");
        assertTrue(bristol.sortButtonVisible(), "Sort button not visible");
        // Do NOT assert an exact number of properties: live data changes. Assert the
        // behaviour that matters (there is at least one) so the test is not brittle.
        assertTrue(bristol.propertyCount() > 0, "No properties listed");
    }

    @Test
    @Tag("regression")
    @DisplayName("User can open a Bristol residence from the list")
    void opensAResidenceFromTheList() {
        CityPage bristol = new CityPage(driver, "bristol");
        bristol.open();

        bristol.openFirstProperty();

        assertTrue(bristol.onPropertyPage(), "Did not land on a Bristol property page: " + bristol.currentUrl());
    }
}
