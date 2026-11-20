package com.unite.example.driver;

import com.unite.example.config.TestConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates the browser. Keeping this in one class means switching browser is a
 * configuration change (-Dbrowser=firefox), not a test change.
 */
public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver create() {
        WebDriver driver = switch (TestConfig.browser().toLowerCase()) {
            case "firefox" -> new FirefoxDriver(firefoxOptions());
            case "edge" -> new EdgeDriver(edgeOptions());
            default -> new ChromeDriver(chromeOptions());
        };
        // Fixed window size: the site is responsive, so the size decides which layout
        // (desktop or mobile) the tests see. Be explicit so runs are repeatable.
        driver.manage().window().setSize(new Dimension(TestConfig.windowWidth(), TestConfig.windowHeight()));
        return driver;
    }

    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
        }
        // Needed on most CI agents (containers/Linux) and harmless locally.
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--lang=en-GB");
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (TestConfig.headless()) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}
