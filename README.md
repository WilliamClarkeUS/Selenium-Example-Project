# Selenium Example Project

A small, readable Selenium 4 (Java, Maven, JUnit 5) framework testing two pages of
[unitestudents.com](https://www.unitestudents.com): the **homepage** and the **Bristol city page**.
It exists as a **discussion piece**: every file is commented to explain *why* it is set up that way.
It mirrors the [Playwright example](https://github.com/WilliamClarkeUS/Playwright-Example-Project)
(same pages, same tests), so the two approaches can be compared side by side.

Tests are read-only (no logins, forms or bookings) and safe to run against the public site.

## Run it

Needs Java 17+ and Google Chrome. Maven is included via the wrapper (`./mvnw`).

```bash
BASE_URL=https://www.unitestudents.com ./mvnw test                 # everything
BASE_URL=https://www.unitestudents.com ./mvnw test -Dgroups=smoke  # only @Tag("smoke")
BASE_URL=https://www.unitestudents.com ./mvnw test -Dheadless=false  # watch the browser
BASE_URL=https://www.unitestudents.com ./mvnw test -Dbrowser=firefox # another browser
```

Reports: `target/surefire-reports/`. Failure screenshots: `target/screenshots/`.

## Structure

```
pom.xml                                  # dependencies (Selenium, JUnit), test runner (Surefire), tag selection
src/test/resources/
  config.properties                      # default settings: browser, headless, timeout, window size
  junit-platform.properties              # parallel execution settings
src/test/java/com/unite/example/
  config/TestConfig.java                 # reads settings: command line > BASE_URL env var > config.properties
  driver/DriverFactory.java              # builds the browser (Chrome / Firefox / Edge)
  pages/                                 # Page Object Model: one class per page
    BasePage.java                        #   shared behaviour: open, explicit waits, click
    HomePage.java
    CityPage.java                        #   takes a city slug, so it works for any city
    components/CookieBanner.java         #   reusable UI piece used by every page
  data/TestData.java                     # static test data (city slug, expected title and heading)
  support/ScreenshotOnFailure.java       # saves a screenshot when a test fails
  tests/                                 # readable tests: business steps, no locators in sight
    BaseTest.java                        #   fresh browser per test
    HomePageTest.java
    BristolPageTest.java
.github/workflows/ci.yml                 # GitHub Actions pipeline
azure-pipelines.yml                      # Azure DevOps equivalent, for discussion
.azure/selenium-steps.yml                # Reusable ADO steps used by each stage
```

## Where do I change...?

| I want to... | Change this |
|---|---|
| Test a different environment | `BASE_URL` (env var locally, pipeline variable in CI). No code change |
| Use another browser | `-Dbrowser=firefox`, or `browser=` in `config.properties` |
| Watch the browser run | `-Dheadless=false` |
| Change the wait time | `timeout.seconds` in `config.properties` |
| Change the screen size (desktop/mobile layout) | `window.width` / `window.height` in `config.properties` |
| Run more or fewer tests in parallel | `parallelism` in `junit-platform.properties` |
| Run only some tests | `-Dgroups=smoke`, or the `groups` parameter in the pipeline |
| Fix a broken locator | The page object, once (not in each test) |
| Add a page | New class in `pages/` extending `BasePage`, new test class in `tests/` |
| Change when the pipeline runs | `on:` in `ci.yml` / `trigger`, `schedules` in `azure-pipelines.yml` |
| Upgrade Selenium or JUnit | Version properties in `pom.xml` |

## The pipeline in one minute

1. **Pull request:** smoke tests only, for fast feedback.
2. **Merge to main / nightly:** the full suite.
3. Tests run headless in Chrome; Surefire reports and failure screenshots are uploaded **even when tests fail**.
4. Environment URL and secrets come from pipeline variables, never from the repo.

## Discussion prompts

- Why does `TestConfig` throw if `BASE_URL` is missing instead of using a default?
- Why explicit waits only? What goes wrong if you mix implicit and explicit waits, or use `Thread.sleep`?
- Why a new browser per test? What does it cost, and what does it buy (parallel safety, independence)?
- The page has two "sort" buttons with the same test id. How did we handle that, and what would you ask the developers to change?
- How would you add Firefox and Edge to the pipeline as a matrix?
- Compare with the Playwright version: what does Playwright give you for free (auto-waiting, tracing, a test runner), and what do you gain with Selenium (language choice, Grid, ecosystem)?
- How would you run this against Selenium Grid or a cloud device provider?
- What would you do to make the suite faster if it had 500 tests?
