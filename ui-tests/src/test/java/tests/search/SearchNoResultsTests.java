package tests.search;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.search.SearchResultsPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-4 Search Icon Function")
@Feature("No results message")
@Owner("Kostiantyn Herasymets")
@DisplayName("Display of 'no results' message when no products found")
public class SearchNoResultsTests extends BaseTest {

    private static final String INVALID = "asdfgh12345";
    private static final String SEARCH_INPUT = "input[type='search'], input[name='s']";

    @Test
    @DisplayName("Guest user: no results message appears for invalid query")
    public void guest_no_results_message() {
        openHomeAndAcceptCookies();

        typeSearch(INVALID);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        assertFalse(results.getNoResultsMessage().isEmpty());
    }

    @Test
    @DisplayName("Logged-in user: no results message appears for invalid query")
    public void loggedin_no_results_message() {
        loginAsDefaultUser();

        typeSearch(INVALID);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        assertFalse(results.getNoResultsMessage().isEmpty());
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

    private void pressEnter() {
        context.page.locator(SEARCH_INPUT).first().press("Enter");
    }
}