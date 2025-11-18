package tests.search;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.login.LoginPage;
import pages.product.ProductDetailsPage;
import pages.search.SearchResultsPage;
import tests.BaseTest;
import utils.ConfigurationReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-4 Search Icon Function")
@Feature("PDP navigation from search results")
@Owner("Kostiantyn Herasymets")
@DisplayName("Open correct PDP from search results")
public class SearchPdpNavigationTests extends BaseTest {

    private static final String KEYWORD = "manuka";
    private static final String SEARCH_INPUT = "input[type='search'], input[name='s']";

    @Test
    @DisplayName("Guest user: first search result opens correct PDP")
    public void guest_click_opens_correct_pdp() {
        openHomeAndAcceptCookies();

        typeSearch(KEYWORD);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        assertTrue(normText(pdp.getTitle()).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdp.getPriceText().isEmpty()) {
            assertTrue(normPrice(pdp.getPriceText()).contains(normPrice(listPrice)) || normPrice(listPrice).contains(normPrice(pdp.getPriceText())));
        }
        if (!listImg.isEmpty() && !pdp.getMainImageSrc().isEmpty()) {
            assertTrue(normImg(pdp.getMainImageSrc()).contains(normImg(listImg)) || normImg(listImg).contains(normImg(pdp.getMainImageSrc())));
        }
    }

    @Test
    @DisplayName("Logged-in user: first search result opens correct PDP")
    public void loggedin_click_opens_correct_pdp() {
        loginAsDefaultUser();

        typeSearch(KEYWORD);
        pressEnter();

        SearchResultsPage results = new SearchResultsPage(context);
        results.waitLoaded();

        String listTitle = results.firstTitle();
        String listPrice = results.firstPriceText();
        String listImg   = results.firstImageSrc();

        ProductDetailsPage pdp = results.openFirstResult();
        pdp.waitLoaded();

        assertTrue(normText(pdp.getTitle()).contains(normText(listTitle)));
        if (!listPrice.isEmpty() && !pdp.getPriceText().isEmpty()) {
            assertTrue(normPrice(pdp.getPriceText()).contains(normPrice(listPrice)) || normPrice(listPrice).contains(normPrice(pdp.getPriceText())));
        }
        if (!listImg.isEmpty() && !pdp.getMainImageSrc().isEmpty()) {
            assertTrue(normImg(pdp.getMainImageSrc()).contains(normImg(listImg)) || normImg(listImg).contains(normImg(pdp.getMainImageSrc())));
        }
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