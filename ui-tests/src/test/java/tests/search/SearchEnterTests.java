package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.search.SearchResultsPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Search with Enter key")
public class SearchEnterTests extends BaseTest {

    private static final String KEYWORD = "manuka";

    @Test
    @DisplayName("Guest user: pressing Enter triggers search and displays results")
    public void guest_enter_triggers_search() {
        searchAsGuest(KEYWORD);

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
        searchAsLoggedIn(KEYWORD);

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String url = context.page.url();
        assertTrue(url.contains("s="));
        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
    }

    private void searchAsGuest(String query) {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.typeSearch(query);
        header.pressEnter();
    }

    private void searchAsLoggedIn(String query) {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.loginAsDefaultUser();
        header.typeSearch(query);
        header.pressEnter();
    }
}