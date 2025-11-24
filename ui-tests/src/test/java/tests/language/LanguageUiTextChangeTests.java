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
import pages.shoppingBag.ShoppingBagPageElements;
import pages.shoppingBag.ShoppingBagPage;
import pages.startPageHeader.StartPage;
import pages.wishlist.NutsPage;
import pages.wishlist.WishlistPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-24 Language selector")
@Feature("AC3: UI text changes to selected language")
@Owner("Kostiantyn Herasymets")
@DisplayName("UI text follows selected language across main pages")
public class LanguageUiTextChangeTests extends BaseTest {

    @Test
    @DisplayName("Guest: after selecting English, UI stays English across main flow and after reload")
    void uiTextChangesToSelectedLanguageFullFlow() {
        StartPage startPage = new StartPage(context);
        Header header = new Header(context);
        LanguageSelector selector = new LanguageSelector(context);
        BasePage base = new BasePage(context) {};

        startPage.openStartPage();
        startPage.acceptCookiesIfPresent();
        selector.switchToEnglish();
        assertTrue(waitForEnglish(selector));

        header.openTopMenuCategory("Special Flavors");
        assertTrue(waitForEnglish(selector));

        NutsPage nutsPage = new NutsPage(context);
        nutsPage.addToWishlist(0).assertAddedToWishlist(0);

        nutsPage.clickWishlistIcon();
        new WishlistPage(context).waitLoaded();
        assertTrue(waitForEnglish(selector));

        context.page
                .locator("table.wishlist_table a.add_to_cart, table.wishlist_table a.button")
                .first()
                .click();

        context.page.waitForTimeout(1000);
        assertTrue(waitForEnglish(selector));

        context.page
                .locator("a:has-text('View cart'), a:has-text('Warenkorb anzeigen')")
                .first()
                .click();

        new ShoppingBagPage(context);
        base.waitForVisibility(ShoppingBagPageElements.SHOPPING_BAG_TABLE);
        assertTrue(waitForEnglish(selector));

        context.page
                .locator("a:has-text('Further information'), a:has-text('Weiter zur Kasse'), a:has-text('Checkout')")
                .first()
                .click();

        base.waitForVisibility("form.checkout");
        assertTrue(waitForEnglish(selector));

        startPage.openStartPage();
        header.clickAccountButton();
        LoginModal loginModal = new LoginModal(context);
        loginModal.waitVisible();
        assertTrue(waitForEnglish(selector));

        context.page.keyboard().press("Escape");
        context.page.waitForTimeout(500);

        base.acceptCookiesIfPresent();
        context.page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        context.page
                .locator("footer a:has-text('Privacy Policy'), footer a:has-text('Corporate Information')")
                .first()
                .click();
        base.acceptCookiesIfPresent();
        assertTrue(waitForEnglish(selector));

        context.page.reload();
        assertTrue(waitForEnglish(selector));
    }

    private boolean waitForEnglish(LanguageSelector selector) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 5000) {
            if (selector.isEnglishUi()) {
                return true;
            }
            context.page.waitForTimeout(250);
        }
        return selector.isEnglishUi();
    }
}