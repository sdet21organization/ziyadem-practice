package pages;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import utils.ConfigurationReader;
import java.util.Map;

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

    @Step("Accept cookies if present")
    public void acceptCookiesIfPresent() {
        String[] selectors = new String[]{
                "button:has-text('Alle akzeptieren')",
                "button:has-text('Akzeptieren')",
                "button:has-text('Zustimmen')",
                "#cmplz-accept",
                ".cmplz-accept",
                ".cky-btn-accept",
                ".cc-allow"
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

    protected void waitForVisible(String selector) {
        context.page.waitForSelector(selector);
    }

    protected void click(String selector) {
        context.page.click(selector);
    }

    protected void type(String selector, String text) {
        context.page.fill(selector, text);
    }

    protected void waitForVisibility(String selector) {
        context.page.waitForSelector(selector);
    }

    protected Locator getLocator(String selector) {
        return context.page.locator(selector);
    }

    protected Locator getLocator(String selector, int index) {
        return context.page.locator(selector).nth(index);
    }

    protected String getText(String selector) {
        return context.page.innerText(selector);
    }

    protected String getText(String selector, int index) {
        return context.page.locator(selector).nth(index).innerText();
    }

    protected void waitForTableRowCount(String rowsSelector, int expected) {
        context.page.waitForFunction(
                "({sel, n}) => document.querySelectorAll(sel).length === n",
                Map.of("sel", rowsSelector, "n", expected)
        );
    }
}