package com.unite.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Single place that reads configuration.
 *
 * Order of precedence (highest first):
 *   1. -Dname=value on the command line, e.g. -Dheadless=false
 *   2. BASE_URL environment variable (for the site URL only)
 *   3. defaults in src/test/resources/config.properties
 */
public final class TestConfig {

    private static final Properties DEFAULTS = load();

    private TestConfig() {}

    /** The site under test. The ONLY value that changes between environments. */
    public static String baseUrl() {
        String url = firstNonBlank(System.getProperty("base.url"), System.getenv("BASE_URL"));
        if (url == null) {
            // Fail loudly. A silent default could point the suite at the wrong environment.
            throw new IllegalStateException(
                "No site URL set. Use the BASE_URL environment variable or -Dbase.url=https://...");
        }
        return url.replaceAll("/+$", ""); // strip trailing slash so "/" + path never doubles up
    }

    public static String browser() {
        return get("browser");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int timeoutSeconds() {
        return Integer.parseInt(get("timeout.seconds"));
    }

    public static int windowWidth() {
        return Integer.parseInt(get("window.width"));
    }

    public static int windowHeight() {
        return Integer.parseInt(get("window.height"));
    }

    private static String get(String key) {
        String override = System.getProperty(key);
        // Maven passes unset properties through as the literal text "${...}": ignore those.
        if (override != null && !override.isBlank() && !override.startsWith("${")) {
            return override;
        }
        return DEFAULTS.getProperty(key);
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank() && !v.startsWith("${")) {
                return v;
            }
        }
        return null;
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = TestConfig.class.getResourceAsStream("/config.properties")) {
            props.load(in);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
        return props;
    }
}
