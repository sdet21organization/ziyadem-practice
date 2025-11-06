package pages.wishlist;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;


public class WishlistPage extends BasePage {

    private final Locator wishlistTable;

    public WishlistPage(TestContext context) {
        super(context);
        this.wishlistTable = context.page.locator("table.wishlist_table");
    }

    @Step("Wait wishlist page loaded")
    public WishlistPage waitLoaded() {
        context.page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        wishlistTable.first().waitFor();
        return this;
    }

    @Step("Check product present in wishlist by link")
    public boolean hasProductByLink(String productHref) {
        return context.page
                .locator("table.wishlist_table td.product-name a[href='" + productHref + "']")
                .count() > 0;
    }

    @Step("Remove product from wishlist by link")
    public void removeByLink(String productHref) {
        Locator row = context.page
                .locator("table.wishlist_table td.product-name a[href='" + productHref + "']")
                .first()
                .locator("xpath=ancestor::tr");
        row.locator("td.product-remove a.remove").click();
    }

    @Step("Wait product absent in wishlist by link")
    public void waitAbsentByLink(String productHref) {
        context.page
                .locator("table.wishlist_table td.product-name a[href='" + productHref + "']")
                .first()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    }

    @Step("Wait for 'removed' message")
    public String waitRemovedMessage() {
        Locator msg = context.page.locator("text=Artikel entfernt.");
        msg.waitFor(); // ждём появления
        return msg.innerText().trim();
    }
}
