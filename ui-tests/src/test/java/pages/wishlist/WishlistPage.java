package pages.wishlist;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;


public class WishlistPage extends BasePage {


    private final Locator wishlistTable;
    private final Locator productNames;


    public WishlistPage(TestContext context) {
        super(context);
        this.wishlistTable = context.page.locator("table.wishlist_table");
        this.productNames = context.page.locator("table.wishlist_table td.product-name a");
    }

    @Step("Wait wishlist page loaded")
    public WishlistPage waitLoaded() {
        context.page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        wishlistTable.first().waitFor();
        return this;
    }

    @Step("Check product present in wishlist by name")
    public boolean hasProduct(String name) {
        return productNames
                .filter(new Locator.FilterOptions().setHasText(name))
                .count() > 0;
    }

    @Step("Check product present in wishlist by link")
    public boolean hasProductByLink(String productHref) {
        return context.page
                .locator("table.wishlist_table td.product-name a[href='" + productHref + "']")
                .count() > 0;
    }
}
