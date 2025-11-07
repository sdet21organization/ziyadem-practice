package tests.wishlist;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import pages.wishlist.AuthSteps;
import pages.wishlist.NutsPage;
import pages.wishlist.WishlistPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Wishlist")
@DisplayName("Wishlist tests (Logged-in)")
@Owner("Oleksiy Korniyenko")
public class WishlistLoggedInTests extends BaseTest {

    private AuthSteps auth;
    private NutsPage nuts;

    @BeforeEach
    void loginBeforeEach() {
        auth = new AuthSteps(context);
        auth.login();
        nuts = new NutsPage(context);
        nuts.open();
    }

    @AfterEach
    void clearWishlist() {
        try {
            context.page.navigate(utils.ConfigurationReader.get("URL") + "wishlist");
            new WishlistPage(context).clearAll();
        } catch (Exception ignored) {
        }
    }

    @Test
    @DisplayName("Logged-in: Add one product from Nuts to wishlist")
    void addProduct() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0).openWishlistAndVerify(link);
    }

    @Test
    @DisplayName("Logged-in: Add two products to wishlist")
    void addMultipleProducts() {
        String link0 = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        String link1 = nuts.getLink(1);
        nuts.addToWishlist(1).assertAddedToWishlist(1);
        nuts.openWishlistAndVerify(link0, link1);
    }

    @Test
    @DisplayName("Logged-in: Clicking wishlist icon again opens Wishlist with product present")
    void openWishlistByClickingAgain() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        nuts.clickWishlistAgain(0);
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(wishlist.hasProductByLink(link), "Product should be present in wishlist after redirect!\nURL: " + link);
    }

    @Test
    @DisplayName("Logged-in: Remove product from Wishlist page")
    void removeFromWishlist() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        WishlistPage wishlist = nuts.openWishlistAndVerify(link);
        wishlist.removeByLink(link);
        String removedMsg = wishlist.waitRemovedMessage();
        assertTrue(removedMsg.contains("Artikel entfernt"), "Expected 'Artikel entfernt' but got: " + removedMsg);
        wishlist.waitAbsentByLink(link);
        assertTrue(!wishlist.hasProductByLink(link), "Product still present after removal!\nURL: " + link);
    }

    @Test
    @DisplayName("Logged-in: Wishlist persists after re-login")
    void persistsAfterRelogin() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0).openWishlistAndVerify(link);
        auth.logout();
        auth.login();
        nuts.clickWishlistIcon();
        WishlistPage wishlistAfterRelogin = new WishlistPage(context).waitLoaded();
        assertTrue(wishlistAfterRelogin.hasProductByLink(link), "Product should persist in wishlist after re-login\nURL: " + link);
    }
}
