package com.unite.example.data;

/**
 * Test data used by the tests.
 *
 * Kept as static values in the repo to keep this example simple: the data is
 * the same on every run and every environment.
 */
public final class TestData {

    private TestData() {}

    /** Homepage expectations. */
    public static final String HOME_TITLE_TEXT = "student accommodation"; // compared in lower case
    public static final String HOME_HEADING_TEXT = "Find your perfect student home";

    /** Bristol city page. */
    public static final String BRISTOL_SLUG = "bristol";
    public static final String BRISTOL_TITLE_TEXT = "Bristol";
    public static final String BRISTOL_HEADING_TEXT = "BRISTOL"; // compared in upper case
}
