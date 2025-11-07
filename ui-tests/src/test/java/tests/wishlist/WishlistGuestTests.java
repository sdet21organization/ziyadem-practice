package tests.wishlist;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.wishlist.NutsPage;
import pages.wishlist.WishlistPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Wishlist")
@DisplayName("Wishlist tests (Guest)")
@Owner("Oleksiy Korniyenko")
public class WishlistGuestTests extends BaseTest {

    private NutsPage nuts;

    @BeforeEach
    void openNutsPage() {
        nuts = new NutsPage(context);
        nuts.open();
    }

    @Test
    @DisplayName("Guest: Add first product from Nuts category to wishlist")
    void addProduct() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0).openWishlistAndVerify(link);
    }

    @Test
    @DisplayName("Guest: Add two different products from Nuts to wishlist")
    void addMultipleProducts() {
        String link0 = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        String link1 = nuts.getLink(1);
        nuts.addToWishlist(1).assertAddedToWishlist(1);
        nuts.openWishlistAndVerify(link0, link1);
    }

    @Test
    @DisplayName("Guest: Clicking wishlist icon again opens Wishlist with the product present")
    void openWishlistByClickingAgain() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        nuts.clickWishlistAgain(0);
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(wishlist.hasProductByLink(link), "Product should be present in wishlist after redirect!\nURL: " + link);
    }

    @Test
    @DisplayName("Guest: Remove product from Wishlist page")
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
    @DisplayName("Guest: Wishlist persists after page refresh")
    void persistsAfterRefresh() {
        String link = nuts.getLink(0);
        nuts.addToWishlist(0).assertAddedToWishlist(0);
        WishlistPage wishlist = nuts.openWishlistAndVerify(link);
        context.page.reload();
        wishlist.waitLoaded();
        assertTrue(wishlist.hasProductByLink(link), "Product disappeared after refresh!\nURL: " + link);
    }
}

