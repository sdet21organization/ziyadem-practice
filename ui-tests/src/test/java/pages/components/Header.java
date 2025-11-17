package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class Header extends BasePage {

    private final Locator accountButton;
    private final Locator accountButtonLogged;
    private final Locator searchInput;
    private final Locator predictiveContainer;
    private final Locator predictiveItems;

    public Header(TestContext context) {
        super(context);
        this.accountButton = context.page.locator("a[data-open=\"#login-form-popup\"]");
        this.accountButtonLogged = context.page.locator(".header-button .account-link");
        this.searchInput = context.page.locator("input[type='search'], input[name='s']");
        this.predictiveContainer = context.page.locator(".autocomplete-suggestions, .live-search-results, ul.search-results");
        this.predictiveItems = context.page.locator(".autocomplete-suggestion, .autocomplete-suggestions li, .live-search-results li, ul.search-results li");
    }

    @Step("Click 'Account' button")
    public void clickAccountButton() {
        accountButton.click();
    }

    @Step("Click 'Account' button as logged in user")
    public void clickAccountButtonLogged() {
        accountButtonLogged.click();
    }

    @Step("Login as default user via page")
    public void loginAsDefaultUser() {
        pages.LoginPage lp = new pages.LoginPage(context);
        lp.openLoginPage();
        acceptCookiesIfPresent();
        lp.submitWith(ConfigurationReader.get("email"), ConfigurationReader.get("password"));
        context.page.locator("body.logged-in, .woocommerce-MyAccount-navigation, a[href*='customer-logout']")
                .first()
                .waitFor(new Locator.WaitForOptions().setTimeout(25000));
        context.page.navigate(ConfigurationReader.get("URL"));
        acceptCookiesIfPresent();
    }

    @Step("Type search query: {text}")
    public void typeSearch(String text) {
        searchInput.first().click();
        searchInput.first().fill(text);
    }

    @Step("Press Enter in search")
    public void pressEnter() {
        searchInput.first().press("Enter");
    }

    @Step("Is predictive list visible")
    public boolean isPredictiveVisible() {
        if (predictiveContainer.count() == 0) return false;
        if (!predictiveContainer.first().isVisible()) return false;
        return predictiveItems.count() > 0 && predictiveItems.first().isVisible();
    }

    @Step("Wait predictive list visible")
    public void waitPredictiveVisible() {
        context.page.waitForFunction(
                "sel => { const el = document.querySelector(sel); return el && getComputedStyle(el).display !== 'none'; }",
                ".autocomplete-suggestions, .live-search-results, ul.search-results"
        );
    }

    @Step("Wait predictive list to be non-empty")
    public void waitPredictiveNonEmpty() {
        context.page.waitForFunction(
                "sel => document.querySelectorAll(sel).length > 0",
                ".autocomplete-suggestion, .autocomplete-suggestions li, .live-search-results li, ul.search-results li"
        );
    }

    @Step("Get up to {limit} predictive texts")
    public List<String> getPredictiveTexts(int limit) {
        waitPredictiveNonEmpty();
        int count = Math.min(predictiveItems.count(), Math.max(limit, 0));
        List<String> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(predictiveItems.nth(i).innerText().trim());
        }
        return result;
    }

    @Step("Check if user is logged in")
    public boolean isLoggedIn() {
        return context.page.locator("body.logged-in, a[href*='customer-logout']").count() > 0;
    }

    @Step("Logout if currently logged in")
    public void logoutIfLoggedIn() {
        if (!isLoggedIn()) {
            context.page.navigate(ConfigurationReader.get("URL"));
            acceptCookiesIfPresent();
            return;
        }
        String base = ConfigurationReader.get("URL");
        context.page.navigate(base + "mein-konto/");
        acceptCookiesIfPresent();
        Locator logout = context.page.locator("a[href*='customer-logout']");
        if (logout.count() > 0) {
            logout.first().click();
            context.page.locator("body.logged-in")
                    .waitFor(new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.DETACHED)
                            .setTimeout(8000));
        } else {
            context.page.context().clearCookies();
            context.page.reload();
        }
        context.page.navigate(base);
        acceptCookiesIfPresent();
    }
}