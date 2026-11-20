package com.unite.example.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * JUnit extension that saves a screenshot when a test fails, to target/screenshots.
 * The pipeline uploads that folder, so a failed run comes with evidence.
 */
public class ScreenshotOnFailure implements TestWatcher {

    private final Supplier<WebDriver> driver;

    public ScreenshotOnFailure(Supplier<WebDriver> driver) {
        this.driver = driver;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            WebDriver d = driver.get();
            if (d instanceof TakesScreenshot shooter) {
                Path dir = Path.of("target", "screenshots");
                Files.createDirectories(dir);
                Path file = dir.resolve(context.getRequiredTestClass().getSimpleName() + "_" + context.getRequiredTestMethod().getName() + ".png");
                Files.write(file, shooter.getScreenshotAs(OutputType.BYTES));
            }
        } catch (Exception ignored) {
            // Never let evidence-gathering hide the real test failure.
        }
    }
}
