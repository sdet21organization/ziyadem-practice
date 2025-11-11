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
@Feature("Search existing product")
public class SearchResultsTests extends BaseTest {

    private static final String KEYWORD = "manuka";

    @Test
    public void search_existing_guest() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.typeSearch(KEYWORD);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
    }

    @Test
    public void search_existing_loggedIn() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.clickAccountButton();

        LoginModal login = new LoginModal(context);
        login.waitVisible();
        login.login(ConfigurationReader.get("email"), ConfigurationReader.get("password"));

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

        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
    }
}