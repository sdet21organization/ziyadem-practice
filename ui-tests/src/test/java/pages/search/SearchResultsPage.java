package pages.search;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.product.ProductDetailsPage;

public class SearchResultsPage extends BasePage {

    private final Locator cards;
    private final Locator links;
    private final Locator noResultsMsg;

    public SearchResultsPage(TestContext context) {
        super(context);
        this.cards = context.page.locator("ul.products li.product:visible, div.product-small.product:visible");
        this.links = context.page.locator("a.woocommerce-loop-product__link");
        this.noResultsMsg = context.page.locator(
                ".woocommerce-notices-wrapper .woocommerce-info, " +
                        ".woocommerce .woocommerce-info, " +
                        "p.woocommerce-info, " +
                        ".search-no-results, " +
                        ".no-products"
        );
    }

    @Step("Wait Search Results page loaded")
    public void waitLoaded() {
        context.page.waitForSelector("ul.products li.product, div.product-small.product, .woocommerce-info, .search-no-results, .no-products");
    }

    @Step("Validate results are not empty")
    public boolean hasAnyResults() {
        return cards.count() > 0;
    }

    @Step("At least one result contains '{keyword}' in title or href")
    public boolean containsRelevantProduct(String keyword) {
        String k = keyword == null ? "" : keyword.toLowerCase();
        int n = cards.count();
        for (int i = 0; i < n; i++) {
            Locator card = cards.nth(i);
            String title = safeCardTitle(card).toLowerCase();
            if (title.contains(k)) return true;

            String href = card.locator(":scope a").first().getAttribute("href");
            if (href != null && href.toLowerCase().contains(k)) return true;
        }
        return false;
    }

    public String firstTitle() {
        return safeCardTitle(cards.first());
    }

    public String firstPriceText() {
        Locator card = cards.first();
        Locator price = card.locator(":scope .price, :scope .price-wrapper .price, :scope .amount bdi");
        return price.count() > 0 ? price.first().innerText().trim() : "";
    }

    public String firstImageSrc() {
        Locator card = cards.first();
        Locator img = card.locator(":scope img");
        if (img.count() == 0) return "";
        String src = img.first().getAttribute("src");
        if (src == null || src.isEmpty()) src = img.first().getAttribute("data-src");
        return src == null ? "" : src;
    }

    @Step("Open first search result")
    public ProductDetailsPage openFirstResult() {
        if (links.count() > 0) {
            links.first().click();
        } else {
            cards.first().locator(":scope a").first().click();
        }
        return new pages.product.ProductDetailsPage(context);
    }

    @Step("Is results list empty")
    public boolean isEmpty() {
        return cards.count() == 0;
    }

    @Step("Get 'no results' message text")
    public String getNoResultsMessage() {
        if (noResultsMsg.count() == 0) return "";
        return noResultsMsg.first().innerText().trim();
    }

    private String safeCardTitle(Locator card) {
        Locator title = card.locator(":scope h2.woocommerce-loop-product__title, " +
                ":scope .woocommerce-loop-product__title, " +
                ":scope .name.product-title, " +
                ":scope .box-text .name, " +
                ":scope .box-text .name a");
        if (title.count() > 0) {
            return title.first().innerText().trim();
        }
        Locator lnk = card.locator(":scope a.woocommerce-loop-product__link");
        if (lnk.count() > 0) {
            String aria = lnk.first().getAttribute("aria-label");
            if (aria != null && !aria.isBlank()) return aria.trim();
            String txt = lnk.first().innerText();
            if (txt != null && !txt.isBlank()) return txt.trim();
        }
        return "";
    }
}