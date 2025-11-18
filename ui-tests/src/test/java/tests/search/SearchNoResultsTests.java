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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Display of 'no results' message when no products found")
public class SearchNoResultsTests extends BaseTest {

    private static final String INVALID = "asdfgh12345";

    @Test
    @DisplayName("Guest user: no results message appears for invalid query")
    public void guest_no_results_message() {
        searchAsGuest(INVALID);

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        assertFalse(results.getNoResultsMessage().isEmpty());
    }

    @Test
    @DisplayName("Logged-in user: no results message appears for invalid query")
    public void loggedin_no_results_message() {
        searchAsLoggedIn(INVALID);

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.isEmpty());
        assertFalse(results.getNoResultsMessage().isEmpty());
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