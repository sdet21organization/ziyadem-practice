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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Search")
@Feature("No results message for invalid query (guest & logged-in)")
public class SearchNoResultsTests extends BaseTest {

    private static final String INVALID = "asdfgh12345";

    @Test
    public void guest_no_results_message() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.typeSearch(INVALID);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        String msg = results.getNoResultsMessage();
        assertFalse(msg.isEmpty());
    }

    @Test
    public void loggedin_no_results_message() {
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
        header.typeSearch(INVALID);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        String msg = results.getNoResultsMessage();
        assertFalse(msg.isEmpty());
    }
}