package tests.search;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.search.SearchResultsPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-4 Search Icon Function")
@Feature("Pressing Enter triggers search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Search with Enter key")
public class SearchEnterTests extends BaseTest {

    private static final String KEYWORD = "manuka";
    private static final String SEARCH_INPUT = "input[type='search'], input[name='s']";

    @Test
    @DisplayName("Guest user: pressing Enter triggers search and displays results")
    public void guest_enter_triggers_search() {
        openHomeAndAcceptCookies();
        typeSearch(KEYWORD);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String url = context.page.url();
        assertTrue(url.contains("s="));
        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
    }

    @Test
    @DisplayName("Logged-in user: pressing Enter triggers search and displays results")
    public void loggedin_enter_triggers_search() {
        loginAsDefaultUser();
        typeSearch(KEYWORD);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String url = context.page.url();
        assertTrue(url.contains("s="));
        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
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