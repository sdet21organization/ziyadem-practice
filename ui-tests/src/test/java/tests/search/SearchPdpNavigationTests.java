package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.components.LoginModal;
import pages.product.ProductDetailsPage;
import pages.search.SearchResultsPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Search")
@Feature("Clicking a search result opens correct PDP (guest & logged-in)")
public class SearchPdpNavigationTests extends BaseTest {

    private static final String KEYWORD = "manuka";

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
                .replaceAll("[^0-9,\\.]", "")
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

    @Test
    public void guest_click_opens_correct_pdp() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.typeSearch(KEYWORD);
        header.pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        String pdpTitle = pdp.getTitle();
        String pdpPrice = pdp.getPriceText();
        String pdpImg   = pdp.getMainImageSrc();

        assertTrue(normText(pdpTitle).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdpPrice.isEmpty()) {
            assertTrue(normPrice(pdpPrice).contains(normPrice(listPrice)) || normPrice(listPrice).contains(normPrice(pdpPrice)));
        }
        if (!listImg.isEmpty() && !pdpImg.isEmpty()) {
            assertTrue(normImg(pdpImg).contains(normImg(listImg)) || normImg(listImg).contains(normImg(pdpImg)));
        }
    }

    @Test
    public void loggedin_click_opens_correct_pdp() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.clickAccountButton();

        LoginModal modal = new pages.components.LoginModal(context);
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

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        String pdpTitle = pdp.getTitle();
        String pdpPrice = pdp.getPriceText();
        String pdpImg   = pdp.getMainImageSrc();

        assertTrue(normText(pdpTitle).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdpPrice.isEmpty()) {
            assertTrue(normPrice(pdpPrice).contains(normPrice(listPrice)) || normPrice(listPrice).contains(normPrice(pdpPrice)));
        }
        if (!listImg.isEmpty() && !pdpImg.isEmpty()) {
            assertTrue(normImg(pdpImg).contains(normImg(listImg)) || normImg(listImg).contains(normImg(pdpImg)));
        }
    }
}