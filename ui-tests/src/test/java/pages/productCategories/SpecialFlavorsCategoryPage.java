package pages.productCategories;

import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.purchaseFunction.CheckoutPage;
import pages.shoppingBag.ShoppingBagPage;
import utils.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.productCategories.SpecialFlavorsCategoryElements.*;
import static pages.purchaseFunction.CheckoutPageElements.CHECKOUT_PAGE_TITLE;
import static pages.shoppingBag.ShoppingBagPageElements.SHOPPING_BAG_TABLE;
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

            productNameList.add(getText(PRODUCT_TITLE).replace("–", "-").trim());
            productPriceList.add(
                    getLocator(PRODUCT_PRICE_DISCOUNTED).count() > 0
                    ? getText(PRODUCT_PRICE_DISCOUNTED, 0).replaceAll("[^0-9.,]", "").trim()
                    : getText(PRODUCT_PRICE, 0).replaceAll("[^0-9.,]", "").trim());

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
    public SpecialFlavorsCategoryPage verifyProductAddedToShoppingBag() {
        String actualProductName = getText(PRODUCT_NAME_IN_MINI_CART_POPUP, 0);
        assertEquals(expectedProductName, actualProductName,
                "Product name in shopping cart does not match the added product");
        return this;
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

    @Step("Go to shopping bag page")
    public ShoppingBagPage goToShoppingBagPage() {
        click(SHOPPING_BAG_ICON);
        waitForVisibility(SHOPPING_BAG_TABLE);
        return new ShoppingBagPage(context);
    }

    @Step("Add some products to shopping bag and get their prices")
    public ShoppingBagPage addSomeProductsToShoppingBagAndGetPrices() {
        for (int i = 0; ; i++) {
            getLocator(AVAILABLE_ITEM, i).click();
            context.page.waitForLoadState(LoadState.NETWORKIDLE);
            waitForVisibility(QUANTITY_COUNTER_INPUT);
            waitForVisibility(ADD_TO_CART_BUTTON);

            productPriceList.add(
                    getLocator(PRODUCT_PRICE_DISCOUNTED).count() > 0
                            ? getText(PRODUCT_PRICE_DISCOUNTED, 0).replaceAll("[^0-9.,]", "").trim()
                            : getText(PRODUCT_PRICE, 0).replaceAll("[^0-9.,]", "").trim());

            click(ADD_TO_CART_BUTTON);
            if (i == 5) {
                click(SHOPPING_BAG_ICON);
                return new ShoppingBagPage(context, productNameList, productPriceList);
            }
            context.page.goBack();
            context.page.goBack();
            waitForVisibility(SPECIAL_FLAVORS_CATEGORY_TITLE);
            context.page.waitForLoadState(LoadState.NETWORKIDLE);
        }
    }

    @Step("Hover to shopping bag and verify mini cart popup")
    public SpecialFlavorsCategoryPage hoverToShoppingBagAndVerifyMiniCartPopup() {
        context.page.goBack();
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        context.page.hover(SHOPPING_BAG_ICON);
        waitForVisibility(MINI_CART_POPUP_HOVER);
        waitForVisibility(PRODUCT_NAME_IN_MINI_CART_POPUP);
        waitForVisibility(KASSE_BUTTON_IN_MINI_CART_POPUP);
        return this;
    }

    @Step("Proceed to checkout from mini cart popup")
    public CheckoutPage proceedToCheckoutFromMiniCartPopup() {
        click(KASSE_BUTTON_IN_MINI_CART_POPUP);
        waitForVisibility(CHECKOUT_PAGE_TITLE);
        return new CheckoutPage(context);
    }
}
