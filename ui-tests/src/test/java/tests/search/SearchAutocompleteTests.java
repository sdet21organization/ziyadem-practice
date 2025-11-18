package tests.search;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.login.LoginPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("ZIYAD-4 Search Icon Function")
@Feature("Predictive dropdown")
@Owner("Kostiantyn Herasymets")
@DisplayName("Predictive search dropdown behavior")
public class SearchAutocompleteTests extends BaseTest {

    private static final String SEARCH_INPUT = "input[type='search'], input[name='s']";
    private static final String PREDICTIVE_CONTAINER = ".autocomplete-suggestions, .live-search-results, ul.search-results";
    private static final String PREDICTIVE_ITEMS = ".autocomplete-suggestion, .autocomplete-suggestions li, .live-search-results li, ul.search-results li";

    @Test
    @DisplayName("Guest user: predictive dropdown appears after typing 3+ characters")
    public void guest_predictive_dropdown() {
        openHomeAndAcceptCookies();

        typeSearch("ma");
        assertFalse(isPredictiveVisible(), "Should not appear before 3 characters");

        typeSearch("man");
        waitPredictiveNonEmpty();

        List<String> items = getPredictiveTexts(8);
        assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }

    @Test
    @DisplayName("Logged-in user: predictive dropdown appears after typing 3+ characters")
    public void loggedin_predictive_dropdown() {
        loginAsDefaultUser();

        typeSearch("ma");
        assertFalse(isPredictiveVisible(), "Should not appear before 3 characters");

        typeSearch("man");
        waitPredictiveNonEmpty();

        List<String> items = getPredictiveTexts(8);
        assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }

    private void openHomeAndAcceptCookies() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();
    }

    private void loginAsDefaultUser() {
        LoginPage lp = new LoginPage(context);
        lp.openLoginPage();
        new BasePage(context) {}.acceptCookiesIfPresent();
        lp.submitWith(ConfigurationReader.get("email"), ConfigurationReader.get("password"));
        context.page.locator("body.logged-in, .woocommerce-MyAccount-navigation, a[href*='customer-logout']").first().waitFor();
        openHomeAndAcceptCookies();
    }

    private void typeSearch(String text) {
        context.page.locator(SEARCH_INPUT).first().click();
        context.page.locator(SEARCH_INPUT).first().fill(text);
    }

    private boolean isPredictiveVisible() {
        if (context.page.locator(PREDICTIVE_CONTAINER).count() == 0) return false;
        if (!context.page.locator(PREDICTIVE_CONTAINER).first().isVisible()) return false;
        return context.page.locator(PREDICTIVE_ITEMS).count() > 0 && context.page.locator(PREDICTIVE_ITEMS).first().isVisible();
    }

    private void waitPredictiveNonEmpty() {
        context.page.waitForFunction("sel => document.querySelectorAll(sel).length > 0", PREDICTIVE_ITEMS);
    }

    private List<String> getPredictiveTexts(int limit) {
        waitPredictiveNonEmpty();
        int count = Math.min(context.page.locator(PREDICTIVE_ITEMS).count(), Math.max(limit, 0));
        List<String> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(context.page.locator(PREDICTIVE_ITEMS).nth(i).innerText().trim());
        }
        return result;
    }
}