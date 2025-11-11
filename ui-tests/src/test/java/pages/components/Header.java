package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class Header extends BasePage {

    private final Locator searchInput;
    private final String predictiveRootSelector = "header .autocomplete-suggestions";
    private final String predictiveItemSelector = "header .autocomplete-suggestions .autocomplete-suggestion";

    public Header(TestContext context) {
        super(context);
        this.searchInput = context.page.locator("header").locator("input[name='s'][type='search']").first();
    }

    @Step("Open site")
    public void open() {
        context.page.navigate(ConfigurationReader.get("URL"));
    }

    @Step("Accept cookies if present")
    public void acceptCookiesIfPresent() {
        Page page = context.page;
        Locator btn = page.locator(
                "button:has-text('Akzeptieren'), button:has-text('Accept'), .cookie, .cookies, #cn-accept-cookie, .cky-btn-accept"
        ).first();
        try {
            if (btn.isVisible()) btn.click();
        } catch (Exception ignored) {}
    }

    @Step("Click 'Account' button")
    public void clickAccountButton() {
        Page page = context.page;
        Locator visibleAccount = page.locator(
                "a[data-open=\"#login-form-popup\"]:visible, " +
                        "a[href*='mein-konto']:visible, " +
                        ".header-account:visible, " +
                        ".account-link:visible, " +
                        ".account-link-mobile:visible"
        ).first();

        if (visibleAccount.count() > 0) {
            visibleAccount.click();
            return;
        }

        page.navigate(ConfigurationReader.get("URL") + "mein-konto");
    }

    @Step("Type search: {text}")
    public void typeSearch(String text) {
        searchInput.click();
        searchInput.fill(text);
    }

    @Step("Press Enter in search")
    public void pressEnter() {
        searchInput.press("Enter");
    }

    @Step("Predictive dropdown visible?")
    public boolean isPredictiveVisible() {
        return context.page.isVisible(predictiveRootSelector);
    }

    @Step("Wait predictive list to be non-empty")
    public void waitPredictiveNonEmpty() {
        String itemsSelector = predictiveItemSelector;
        context.page.waitForFunction("sel => document.querySelectorAll(sel).length > 0", itemsSelector);
    }

    @Step("Get predictive texts (limit {limit})")
    public List<String> getPredictiveTexts(int limit) {
        Locator items = context.page.locator(predictiveItemSelector);
        List<String> all = items.allInnerTexts();
        if (limit <= 0 || all.size() <= limit) return all;
        return new ArrayList<>(all.subList(0, limit));
    }

    @Step("Login as default user")
    public void loginAsDefaultUser() {
        String base = ConfigurationReader.get("URL");
        context.page.navigate(base + "mein-konto");
        acceptCookiesIfPresent();

        boolean alreadyLogged =
                context.page.isVisible(".woocommerce-MyAccount-navigation, .woocommerce-MyAccount-content, a[href*='customer-logout']");

        if (!alreadyLogged && context.page.isVisible("form.login")) {
            context.page.fill("form.login input#username, form.login input[name='username'], form.login input[name='log']",
                    ConfigurationReader.get("email"));
            context.page.fill("form.login input#password, form.login input[name='password'], form.login input[name='pwd']",
                    ConfigurationReader.get("password"));
            context.page.click("form.login button[type='submit'], form.login button[name='login'], form.login input[type='submit']");

            context.page.waitForURL("**/mein-konto**", new Page.WaitForURLOptions().setTimeout(10000));
            context.page.waitForSelector(
                    ".woocommerce-MyAccount-navigation, .woocommerce-MyAccount-content, a[href*='customer-logout']",
                    new Page.WaitForSelectorOptions()
                            .setState(WaitForSelectorState.ATTACHED)
                            .setTimeout(10000)
            );
        }
    }
}