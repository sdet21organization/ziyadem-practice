package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.components.LoginModal;
import pages.search.SearchResultsPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Search")
@Feature("Pressing Enter triggers search (guest & logged-in)")
public class SearchEnterTests extends BaseTest {

    private static final String KEYWORD = "manuka";

    @Test
    public void guest_enter_triggers_search() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.typeSearch(KEYWORD);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String url = context.page.url();
        assertTrue(url.contains("s="), "URL must contain search query");
        assertTrue(results.hasAnyResults(), "Results list must not be empty");
        assertTrue(results.containsRelevantProduct(KEYWORD), "At least one result must be relevant");
    }

    @Test
    public void loggedin_enter_triggers_search() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.clickAccountButton();

        LoginModal modal = new LoginModal(context);
        modal.waitVisible();
        modal.login(ConfigurationReader.get("email"), ConfigurationReader.get("password"));

        context.page.waitForLoadState();
        base.acceptCookiesIfPresent();
        context.page.waitForFunction("() => document.body.classList.contains('logged-in')");

        base.open();
        base.acceptCookiesIfPresent();

        header = new Header(context);
        header.typeSearch(KEYWORD);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String url = context.page.url();
        assertTrue(url.contains("s="), "URL must contain search query");
        assertTrue(results.hasAnyResults(), "Results list must not be empty");
        assertTrue(results.containsRelevantProduct(KEYWORD), "At least one result must be relevant");
    }
}