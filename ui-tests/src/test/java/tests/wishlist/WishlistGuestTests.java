package tests.wishlist;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.wishlist.NutsPage;
import pages.wishlist.WishlistPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Wishlist")
@Owner("Oleksiy Korniyenko")
public class WishlistGuestTests extends BaseTest {

    @Test
    @DisplayName("Guest: Add first product from Nuts category to wishlist")
    void addProduct() {
        NutsPage nuts = new NutsPage(context);
        nuts.open();
        String link = nuts.getLink(0);
        nuts.addToWishlist(0);
        String msg = nuts.getAddedMessage(0);
        assertTrue(
                msg.contains("Artikel hinzugefügt!"),
                "Expected 'Artikel hinzugefügt!' but got: " + msg
        );

        nuts.clickWishlistIcon();
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(
                wishlist.hasProductByLink(link),
                "Product not found in wishlist!\nURL: " + link
        );
    }

    @Test
    @DisplayName("Guest: Add two different products from Nuts to wishlist")
    void addMultipleProducts() {
        NutsPage nuts = new NutsPage(context);
        nuts.open();
        String link0 = nuts.getLink(0);
        nuts.addToWishlist(0);
        String msg0 = nuts.getAddedMessage(0);
        assertTrue(msg0.contains("Artikel hinzugefügt!"), "Expected 'Artikel hinzugefügt!' but got: " + msg0);

        String link1 = nuts.getLink(1);
        nuts.addToWishlist(1);
        String msg1 = nuts.getAddedMessage(1);
        assertTrue(msg1.contains("Artikel hinzugefügt!"), "Expected 'Artikel hinzugefügt!' but got: " + msg1);

        nuts.clickWishlistIcon();
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(wishlist.hasProductByLink(link0), "Product #1 not found in wishlist!\nURL: " + link0);
        assertTrue(wishlist.hasProductByLink(link1), "Product #2 not found in wishlist!\nURL: " + link1);
    }

    @Test
    @DisplayName("Guest: Clicking wishlist icon again opens Wishlist with the product present")
    void openWishlistByClickingAgain() {
        NutsPage nuts = new NutsPage(context);
        nuts.open();
        String link = nuts.getLink(0);
        nuts.addToWishlist(0);
        String msg = nuts.getAddedMessage(0);
        assertTrue(msg.contains("Artikel hinzugefügt!"), "Expected 'Artikel hinzugefügt!' but got: " + msg);

        nuts.clickWishlistAgain(0);
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(
                wishlist.hasProductByLink(link),
                "Product should be present in wishlist after redirect!\nURL: " + link
        );
    }

    @Test
    @DisplayName("Guest: Remove product from Wishlist page")
    void removeFromWishlist() {
        NutsPage nuts = new NutsPage(context);
        nuts.open();
        String link = nuts.getLink(0);
        nuts.addToWishlist(0);
        String addedMsg = nuts.getAddedMessage(0);
        assertTrue(addedMsg.contains("Artikel hinzugefügt!"),
                "Expected 'Artikel hinzugefügt!' but got: " + addedMsg);

        nuts.clickWishlistIcon();
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(wishlist.hasProductByLink(link),
                "Product should be present before removal\nURL: " + link);

        wishlist.removeByLink(link);
        String removedMsg = wishlist.waitRemovedMessage();
        assertTrue(removedMsg.contains("Artikel entfernt"),
                "Expected 'Artikel entfernt' but got: " + removedMsg);

        wishlist.waitAbsentByLink(link);
        assertTrue(!wishlist.hasProductByLink(link),
                "Product still present after removal!\nURL: " + link);
    }

    @Test
    @DisplayName("Guest: Wishlist persists after page refresh")
    void persistsAfterRefresh() {
        NutsPage nuts = new NutsPage(context);
        nuts.open();
        String link = nuts.getLink(0);
        nuts.addToWishlist(0);
        String msg = nuts.getAddedMessage(0);
        assertTrue(msg.contains("Artikel hinzugefügt!"),
                "Expected 'Artikel hinzugefügt!' but got: " + msg);

        nuts.clickWishlistIcon();
        WishlistPage wishlist = new WishlistPage(context).waitLoaded();
        assertTrue(wishlist.hasProductByLink(link),
                "Product should be present before refresh\nURL: " + link);

        context.page.reload();
        wishlist.waitLoaded();

        assertTrue(wishlist.hasProductByLink(link),
                "Product disappeared after refresh!\nURL: " + link);
    }
}
