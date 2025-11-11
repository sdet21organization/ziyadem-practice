package pages.product;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class ProductDetailsPage extends BasePage {

    private final Locator title;
    private final Locator price;
    private final Locator mainImage;

    public ProductDetailsPage(TestContext context) {
        super(context);
        this.title = context.page.locator(".product_title, h1.product_title, h1");
        this.price = context.page.locator(".summary .price, .product .price, .price");
        this.mainImage = context.page.locator(".woocommerce-product-gallery__image img, .product img");
    }

    @Step("Wait PDP loaded")
    public void waitLoaded() {
        title.first().waitFor();
    }

    public String getTitle() {
        return title.first().innerText().trim();
    }

    public String getPriceText() {
        return price.count() > 0 ? price.first().innerText().trim() : "";
    }

    public String getMainImageSrc() {
        return mainImage.count() > 0 ? mainImage.first().getAttribute("src") : "";
    }
}