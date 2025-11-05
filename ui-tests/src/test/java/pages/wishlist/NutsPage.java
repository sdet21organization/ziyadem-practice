package pages.wishlist;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class NutsPage extends BasePage {

    public NutsPage(TestContext context) {
        super(context);
    }

    @Step("Open Nuts category page")
    @Override
    public void open() {
        super.open("produkt-kategory/kuruyemis");
        context.page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        context.page.locator("li.product, div.product-small.product")
                .first();
    }

    private Locator productCardAt(int index) {
        return context.page.locator("li.product, div.product-small.product").nth(index);
    }

    @Step("Get product link by index")
    public String getLink(int index) {
        return productCardAt(index)
                .locator(".woocommerce-loop-product__title a")
                .getAttribute("href");
    }

    @Step("Add product to wishlist by index")
    public NutsPage addToWishlist(int index) {
        Locator card = productCardAt(index);
        card.scrollIntoViewIfNeeded();
        card.hover();
        Locator wishlistBtn = card.locator("button.wishlist-button").first();
        wishlistBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        wishlistBtn.click();
        Locator addedMsg = card.locator(".yith-wcwl-wishlistaddedbrowse").first();
        addedMsg.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    @Step("Get 'added' message text by index")
    public String getAddedMessage(int index) {
        return productCardAt(index)
                .locator(".yith-wcwl-wishlistaddedbrowse .feedback")
                .innerText()
                .trim();
    }

    @Step("Click header Wishlist icon")
    public void clickWishlistIcon() {
        Locator wishlistIcon = context.page
                .locator("header .wishlist-link[aria-label='Wunschliste'], header a[title='Wunschliste']")
                .first();
        wishlistIcon.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        wishlistIcon.click();
    }

    @Step("Click wishlist again by index")
    public void clickWishlistAgain(int index) {
        Locator card = context.page.locator("li.product, div.product-small.product").nth(index);
        card.scrollIntoViewIfNeeded();
        card.hover();
        Locator wishlistBtn = card.locator("button.wishlist-button").first();
        wishlistBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        wishlistBtn.click();
        context.page.waitForURL("**/wishlist*");
    }
}

