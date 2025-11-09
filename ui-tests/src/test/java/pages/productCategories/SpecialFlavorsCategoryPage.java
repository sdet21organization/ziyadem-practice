package pages.productCategories;

import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.shoppingBag.ShoppingBagPage;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.productCategories.SpecialFlavorsCategoryElements.*;
import static pages.shoppingBag.ShoppingBagElements.SHOPPING_BAG_TABLE;
import static pages.startPageHeader.StartPageHeaderElements.*;

public class SpecialFlavorsCategoryPage extends BasePage {

    private String expectedProductName;
    private final List<String> productPriceList = new ArrayList<>();
    private final List<String> productNameList = new ArrayList<>();

    public SpecialFlavorsCategoryPage(context.TestContext context) {
        super(context);
    }

    @Step("Open Special Flavors category page")
    public SpecialFlavorsCategoryPage openSpecialFlavorsCategoryPage() {
        open(ConfigurationReader.get("special-flavors-category-path"));
        waitForVisibility(SPECIAL_FLAVORS_CATEGORY_TITLE);
        return this;
    }

    @Step("Choose product from available items")
    public SpecialFlavorsCategoryPage chooseProduct() {
        getLocator(AVAILABLE_ITEM, 0).click();
        waitForVisibility(QUANTITY_COUNTER_INPUT);
        waitForVisibility(ADD_TO_CART_BUTTON);
        return this;
    }

    @Step("Add product to shopping bag")
    public SpecialFlavorsCategoryPage addProductToShoppingBag() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        expectedProductName = getText(PRODUCT_TITLE).replace("–", "-").trim();
        click(ADD_TO_CART_BUTTON);
        waitForVisibility(MINI_CART_POPUP);
        waitForVisibility(SUCCESS_ADDITION_ALERT);
        return this;
    }

    @Step("Add some products to shopping bag and get their names and prices")
    public ShoppingBagPage addSomeProductsToShoppingBag() {
        for (int i = 0; ; i++) {
            getLocator(AVAILABLE_ITEM, i).click();
            context.page.waitForLoadState(LoadState.NETWORKIDLE);
            waitForVisibility(QUANTITY_COUNTER_INPUT);
            waitForVisibility(ADD_TO_CART_BUTTON);
            String productName = getText(PRODUCT_TITLE).replace("–", "-").trim();
            String productPrice;
            if (getLocator(PRODUCT_PRICE_DISCOUNTED).count() > 0) {
                productPrice = getText(PRODUCT_PRICE_DISCOUNTED, 0).replaceAll("[^0-9.,]", "").trim();
            } else {
                productPrice = getText(PRODUCT_PRICE).replaceAll("[^0-9.,]", "").trim();
            }
            productNameList.add(productName);
            productPriceList.add(productPrice);
            click(ADD_TO_CART_BUTTON);
            if (i == 3) {
                click(SHOPPING_BAG_ICON);
                return new ShoppingBagPage(context, productNameList, productPriceList);
            }
            context.page.goBack();
            context.page.goBack();
            waitForVisibility(SPECIAL_FLAVORS_CATEGORY_TITLE);
            context.page.waitForLoadState(LoadState.NETWORKIDLE);
        }
    }

    @Step("Verify product added to shopping bag")
    public void verifyProductAddedToShoppingBag() {
        String actualProductName = getText(PRODUCT_NAME_IN_MINI_CART_POPUP);
        assertEquals(expectedProductName, actualProductName,
                "Product name in shopping cart does not match the added product");
    }

    @Step("Verify items count in shopping bag")
    public SpecialFlavorsCategoryPage verifyItemsCount() {
        assertTrue(getLocator(String.format(PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON, "1"), 0).isVisible(),
                "Product quantity in shopping bag icon is not correct");
        return this;
    }

    @Step("Add one more item to the shopping bag and verify quantity")
    public void addOneMoreItemAndVerifyQuantity() {
        click(ADD_TO_CART_BUTTON);
        waitForVisibility(MINI_CART_POPUP);
        waitForVisibility(SUCCESS_ADDITION_ALERT);

        assertTrue(getLocator(String.format(PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON, "2"), 0).isVisible(),
                "Product quantity in shopping bag icon is not correct after adding one more item");
    }

    @Step("Add one more item to shopping bag and open shopping cart")
    public ShoppingBagPage addOneMoreItemAndOpenShoppingCart() {
        click(ADD_TO_CART_BUTTON);
        waitForVisibility(MINI_CART_POPUP);
        waitForVisibility(SUCCESS_ADDITION_ALERT);
        click(SHOPPING_BAG_ICON);
        waitForVisibility(SHOPPING_BAG_TABLE);
        return new ShoppingBagPage(context);
    }
}
