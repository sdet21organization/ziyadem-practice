package pages.productCategories;

import static pages.startPageHeader.StartPageHeaderElements.*;
import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.shoppingBag.ShoppingBagPage;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static pages.productCategories.SpecialFlavorsCategoryElements.*;
import static pages.shoppingBag.ShoppingBagElements.SHOPPING_BAG_TABLE;

public class SpecialFlavorsCategoryPage extends BasePage {

    private String expectedProductName;
    private final List<String> productPriceList = new ArrayList<>();
    private final List<String> productNameList = new ArrayList<>();

    public SpecialFlavorsCategoryPage(TestContext context) {
        super(context);
    }

    @Step("Open Special Flavors category page")
    public SpecialFlavorsCategoryPage openSpecialFlavorsCategoryPage() {
        open(ConfigurationReader.get("special-flavors-category-path"));
        waitForVisible(SPECIAL_FLAVORS_CATEGORY_TITLE);
        return this;
    }

    @Step("Choose first available product")
    public SpecialFlavorsCategoryPage chooseProduct() {
        context.page.locator(AVAILABLE_ITEM).first().click();
        waitForVisible(QUANTITY_COUNTER_INPUT);
        waitForVisible(ADD_TO_CART_BUTTON);
        return this;
    }

    @Step("Add product to shopping bag")
    public SpecialFlavorsCategoryPage addProductToShoppingBag() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        expectedProductName = context.page.locator(PRODUCT_TITLE).innerText().trim();
        context.page.click(ADD_TO_CART_BUTTON);
        waitForVisible(MINI_CART_POPUP);
        waitForVisible(SUCCESS_ADDITION_ALERT);
        return this;
    }

    @Step("Verify product added to shopping bag")
    public void verifyProductAddedToShoppingBag() {
        String actualProductName = context.page.locator(PRODUCT_NAME_IN_MINI_CART_POPUP).innerText().trim();
        if (!expectedProductName.equals(actualProductName)) {
            throw new AssertionError("Expected: " + expectedProductName + " but got: " + actualProductName);
        }
    }

    @Step("Add some products to shopping bag")
    public ShoppingBagPage addSomeProductsToShoppingBag() {
        for (int i = 0; i < 4; i++) {
            context.page.locator(AVAILABLE_ITEM).nth(i).click();
            context.page.waitForLoadState(LoadState.NETWORKIDLE);

            waitForVisible(QUANTITY_COUNTER_INPUT);
            waitForVisible(ADD_TO_CART_BUTTON);

            String name = context.page.locator(PRODUCT_TITLE).innerText().trim();

            String price;
            if (context.page.locator(PRODUCT_PRICE_DISCOUNTED).count() > 0) {
                price = context.page.locator(PRODUCT_PRICE_DISCOUNTED).first().innerText().replaceAll("[^0-9.,]", "").trim();
            } else {
                price = context.page.locator(PRODUCT_PRICE).first().innerText().replaceAll("[^0-9.,]", "").trim();
            }

            productNameList.add(name);
            productPriceList.add(price);

            context.page.click(ADD_TO_CART_BUTTON);

            if (i == 3) {
                context.page.locator(SHOPPING_BAG_ICON).click();
                waitForVisible(SHOPPING_BAG_TABLE);
                return new ShoppingBagPage(context, productNameList, productPriceList);
            }

            context.page.goBack();
            context.page.waitForLoadState(LoadState.NETWORKIDLE);
        }
        return null;
    }
}