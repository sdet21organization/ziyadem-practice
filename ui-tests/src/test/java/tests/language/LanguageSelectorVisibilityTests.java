package tests.language;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import pages.components.LoginModal;
import pages.language.LanguageSelector;
import pages.product.ProductDetailsPage;
import pages.productCategories.SpecialFlavorsCategoryPage;
import pages.search.SearchResultsPage;
import pages.shoppingBag.ShoppingBagPage;
import pages.startPageHeader.StartPage;
import pages.wishlist.NutsPage;
import pages.wishlist.WishlistPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-24 Language selector")
@Feature("AC1: visibility in header")
@Owner("Kostiantyn Herasymets")
@DisplayName("Language selector is visible in header on main user flows (AC1)")
public class LanguageSelectorVisibilityTests extends BaseTest {

    @Test
    @DisplayName("Guest: language selector is visible on main pages & flows")
    void languageSelectorVisibleOnMainFlowsAc1() {
        LanguageSelector selector = new LanguageSelector(context);
        StartPage startPage = new StartPage(context);
        Header header = new Header(context);

        startPage.openStartPage();
        startPage.acceptCookiesIfPresent();
        assertSelectorVisible(selector, "Home page");

        SpecialFlavorsCategoryPage categoryPage =
                new SpecialFlavorsCategoryPage(context).openSpecialFlavorsCategoryPage();
        assertSelectorVisible(selector, "Category page (Special Flavors)");

        categoryPage.chooseProduct();
        ProductDetailsPage pdp = new ProductDetailsPage(context);
        pdp.waitLoaded();
        assertSelectorVisible(selector, "Product Details Page from category");

        new NutsPage(context).clickWishlistIcon();
        new WishlistPage(context).waitLoaded();
        assertSelectorVisible(selector, "Wishlist page");

        startPage.openStartPage().openShoppingBagPage();
        new ShoppingBagPage(context);
        assertSelectorVisible(selector, "Shopping bag (cart) page");

        startPage.openStartPage();
        header.clickAccountButton();
        LoginModal loginModal = new LoginModal(context);
        loginModal.waitVisible();
        assertSelectorVisible(selector, "Login popup (account icon)");

        BasePage any = new BasePage(context) {
        };
        any.open("/impressum/");
        any.acceptCookiesIfPresent();
        assertSelectorVisible(selector, "Footer page (Impressum)");

        startPage.openStartPage();
        header.typeSearch("propolis");
        header.pressEnter();
        SearchResultsPage searchResultsPage = new SearchResultsPage(context);
        searchResultsPage.waitLoaded();
        assertSelectorVisible(selector, "Search results page");

        ProductDetailsPage pdpFromSearch = searchResultsPage.openFirstResult();
        pdpFromSearch.waitLoaded();
        assertSelectorVisible(selector, "PDP opened from search results");

        startPage.openStartPage();
        assertSelectorVisible(selector, "Home page after navigation");
    }

    private void assertSelectorVisible(LanguageSelector selector, String where) {
        selector.waitVisible();
        boolean visible = selector.isVisible();
        assertTrue(
                visible,
                "Language selector must be visible in header on: " + where
        );
    }
}