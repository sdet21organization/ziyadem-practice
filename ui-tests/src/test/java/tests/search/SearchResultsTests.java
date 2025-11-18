package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.search.SearchResultsPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Search results display")
public class SearchResultsTests extends BaseTest {

    private static final String KEYWORD = "manuka";

    @AfterEach
    void ensureGuestAfterTest() {
        try {
            new Header(context).logoutIfLoggedIn();
        } catch (Exception ignored) {
        }
    }

    @Test
    @DisplayName("Guest user: search for existing product shows results")
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
    @DisplayName("Logged-in user: search for existing product shows results")
    public void search_existing_loggedIn() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.loginAsDefaultUser();
        header.typeSearch(KEYWORD);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        assertTrue(results.hasAnyResults());
        assertTrue(results.containsRelevantProduct(KEYWORD));
    }
}