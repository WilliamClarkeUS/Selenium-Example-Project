package com.unite.example.tests;

import com.unite.example.driver.DriverFactory;
import com.unite.example.support.ScreenshotOnFailure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;

/**
 * Parent of every test class: starts a fresh browser before each test and closes it after.
 * A new browser per test means tests cannot affect each other, which is what makes
 * running them in parallel safe.
 */
abstract class BaseTest {

    protected WebDriver driver;

    // Takes a screenshot if a test fails. The Supplier lets it see the driver created in setUp().
    @RegisterExtension
    final ScreenshotOnFailure screenshotOnFailure = new ScreenshotOnFailure(() -> driver);

    @BeforeEach
    void setUp() {
        driver = DriverFactory.create();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // quit() closes the browser AND the driver process; close() only closes a window
        }
    }
}
