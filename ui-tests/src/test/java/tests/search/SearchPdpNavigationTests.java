package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.product.ProductDetailsPage;
import pages.search.SearchResultsPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Open correct PDP from search results")
public class SearchPdpNavigationTests extends BaseTest {

    private static final String KEYWORD = "manuka";

    @Test
    @DisplayName("Guest user: first search result opens correct PDP")
    public void guest_click_opens_correct_pdp() {
        searchAsGuest(KEYWORD);

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        assertTrue(normText(pdp.getTitle()).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdp.getPriceText().isEmpty()) {
            assertTrue(
                    normPrice(pdp.getPriceText()).contains(normPrice(listPrice)) ||
                            normPrice(listPrice).contains(normPrice(pdp.getPriceText()))
            );
        }
        if (!listImg.isEmpty() && !pdp.getMainImageSrc().isEmpty()) {
            assertTrue(
                    normImg(pdp.getMainImageSrc()).contains(normImg(listImg)) ||
                            normImg(listImg).contains(normImg(pdp.getMainImageSrc()))
            );
        }
    }

    @Test
    @DisplayName("Logged-in user: first search result opens correct PDP")
    public void loggedin_click_opens_correct_pdp() {
        searchAsLoggedIn(KEYWORD);

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        assertTrue(normText(pdp.getTitle()).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdp.getPriceText().isEmpty()) {
            assertTrue(
                    normPrice(pdp.getPriceText()).contains(normPrice(listPrice)) ||
                            normPrice(listPrice).contains(normPrice(pdp.getPriceText()))
            );
        }
        if (!listImg.isEmpty() && !pdp.getMainImageSrc().isEmpty()) {
            assertTrue(
                    normImg(pdp.getMainImageSrc()).contains(normImg(listImg)) ||
                            normImg(listImg).contains(normImg(pdp.getMainImageSrc()))
            );
        }
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

    private static String normText(String s) {
        if (s == null) return "";
        return s.replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }

    private static String normPrice(String s) {
        if (s == null) return "";
        return s.replace('\u00A0', ' ')
                .replaceAll("[^0-9,.]", "")
                .replace(',', '.')
                .trim();
    }

    private static String normImg(String url) {
        if (url == null) return "";
        return url.replaceAll("-\\d+x\\d+(?=\\.[a-zA-Z]{3,4}$)", "")
                .replaceAll("\\?.*$", "")
                .trim()
                .toLowerCase();
    }
}