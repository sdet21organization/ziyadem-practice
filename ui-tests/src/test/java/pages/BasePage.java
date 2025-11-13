package pages;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import utils.ConfigurationReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage {

    protected final TestContext context;

    protected BasePage(TestContext context) {
        this.context = context;
    }

    @Step("Open Main page")
    public void open() {
        context.page.navigate(ConfigurationReader.get("URL"));
    }

    @Step("Open page with path: {path}")
    public void open(String path) {
        context.page.navigate(ConfigurationReader.get("URL") + path);
    }

    @Step("Get locator for selector: {selector}")
    public Locator getLocator(String selector) {
        return context.page.locator(selector);
    }

    @Step("Get locator for selector: {selector} at index: {index}")
    public Locator getLocator(String selector, int index) {
        return getLocator(selector).nth(index);
    }

    @Step("Wait for element to be visible: {selector}")
    public void waitForVisibility(String selector) {
        getLocator(selector).waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }

    @Step("Wait for element to be visible: {selector}")
    protected void waitForVisible(String selector) {
        context.page.waitForSelector(selector);
    }

    @Step("Click on element: {selector}")
    protected void click(String selector) {
        context.page.click(selector);
    }

    @Step("Type {text} in to element: {selector}")
    protected void type(String selector, String text) {
        context.page.fill(selector, text);
    }

    @Step("Get text from element: {selector}")
    public String getText(String selector) {
        waitForVisibility(selector);
        return getLocator(selector).innerText();
    }

    @Step("Get text from element: {selector} at index {index}")
    public String getText(String selector, int index) {
        return getLocator(selector, index).innerText();
    }

    @Step("Wait for the table has count of rows: {expectedCount}")
    public void waitForTableRowCount(String tableSelector, int expectedCount) {
        Locator rows = getLocator(tableSelector);
        assertThat(rows).hasCount(expectedCount);
    }

    @Step("Accept cookies if present")
    public void acceptCookiesIfPresent() {
        String[] selectors = new String[]{
                "button:has-text('Alle akzeptieren')",
                "button:has-text('Akzeptieren')",
                "button:has-text('Zustimmen')",
                "#cmplz-accept",
                ".cmplz-accept",
                ".cky-btn-accept",
                ".cc-allow",
                "#onetrust-accept-btn-handler"
        };
        for (String s : selectors) {
            try {
                Locator btn = context.page.locator(s).first();
                if (btn.isVisible()) {
                    btn.click();
                    break;
                }
            } catch (Exception ignored) {}
        }
    }
}