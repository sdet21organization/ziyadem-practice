package pages;

import context.TestContext;
import io.qameta.allure.Step;
import utils.ConfigurationReader;


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

    protected void waitForVisible(String selector) {
        context.page.waitForSelector(selector);
    }

    protected void click(String selector) {
        context.page.click(selector);
    }

    protected void type(String selector, String text) {
        context.page.fill(selector, text);
    }
}