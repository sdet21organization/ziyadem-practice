package pages.shoppingBag;

import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.shoppingBag.ShoppingBagElements.*;
import static pages.startPageHeader.StartPageHeaderElements.PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON;

public class ShoppingBagPage extends BasePage {

    private final List<String> productNameList;
    private final List<String> productPriceList;

    public ShoppingBagPage(TestContext context) {
        this(context, null, null);
    }

    public ShoppingBagPage(TestContext context, List<String> productNameList, List<String> productPriceList) {
        super(context);
        this.productNameList = productNameList;
        this.productPriceList = productPriceList;
    }

    @Step("Verify increased quantity in shopping bag")
    public void verifyIncreasedQuantityInShoppingBag() {
        waitForTableRowCount(ROW_IN_SHOPPING_BAG_TABLE, 2);
        getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).inputValue();
        String actualQuantity = getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).inputValue();
        assertEquals("2", actualQuantity,
                "Product quantity in shopping bag is not increased as expected");
    }

    @Step("Verify product added to shopping bag")
    public ShoppingBagPage verifyAllProductsAddedToShoppingBag() {
        waitForVisibility(SHOPPING_BAG_TABLE);
        waitForTableRowCount(ROW_IN_SHOPPING_BAG_TABLE, (productPriceList.size() + 1));
        context.page.waitForLoadState(LoadState.NETWORKIDLE);

        for (int i = 0; i < productPriceList.size(); i++) {
            String expectedName = productNameList.get(i);
            String expectedPrice = productPriceList.get(i);
            String actualPrice = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE, i).replaceAll("[^0-9.,]", "").trim();
            String actualName = getText(ITEM_NAME_IN_SHOPPING_BAG_TABLE, i).trim();
            assertEquals(expectedName, actualName,
                    "Product name in shopping cart does not match the added product");
            assertEquals(expectedPrice, actualPrice,
                    "Product price in shopping cart does not match the added product");
        }
        return this;
    }

    @Step("Verify amount in shopping bag")
    public void verifyAmountInShoppingBag() {
        String expectedAmount = productPriceList.stream()
                .map(price -> price.replace(",", "."))
                .mapToDouble(Double::parseDouble)
                .sum() + "";
        expectedAmount = new DecimalFormat("0.00").format(Double.parseDouble(expectedAmount));
        String actualAmount = getText(AMOUNT_IN_SHOPPING_BAG).replaceAll("[^0-9.,]", "").trim();
        assertEquals(expectedAmount, actualAmount,
                "Total amount in shopping bag does not match expected amount");
    }

    @Step("Edit item count in shopping bag and verify quantity and total price")
    public void editShoppingBagAndVerifyQuantityAndTotalPrice() {
        String actualPriceBefore = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE).replaceAll("[^0-9.,]", "").trim();
        click(COUNTER_PLUS_BUTTON);
        click(SHOPPING_BAG_UPDATE_BUTTON);
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        String actualTotalPriceAfter = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE).replaceAll("[^0-9.,]", "").trim();
        double expectedTotalPriceAfter = 2 * Double.parseDouble(actualPriceBefore.replace(",", "."));
        String actualQuantity = getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).inputValue();

        assertEquals("2", actualQuantity,
                "Product quantity in shopping bag is not correct after editing");
        assertEquals(new DecimalFormat("0.00").format(expectedTotalPriceAfter), actualTotalPriceAfter,
                "Total price in shopping bag is not correct after editing");
    }

    @Step("Remove item from shopping bag")
    public ShoppingBagPage removeItemFromShoppingBag() {
        click(REMOVE_ITEM_FROM_SHOPPING_BAG_TABLE);
        waitForVisibility(SUCCESS_DELETION_MESSAGE);
        return this;
    }

    @Step("Verify shopping bag is empty")
    public void verifyShoppingBagIsEmpty() {
        waitForVisibility(SHOPPING_BAG_IS_EMPTY_MESSAGE);
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        assertTrue(getLocator(String.format(PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON, "0"), 0).isVisible(),
                "Product quantity in shopping bag icon is not zero after removing the item");
    }

    @Step("Remove item and verify shopping bag message")
    public void removeItemAndVerifyShoppingBagMessage() {
        click(REMOVE_ITEM_FROM_SHOPPING_BAG_TABLE);
        waitForVisibility(SUCCESS_DELETION_MESSAGE);
        assertTrue(getLocator(SHOPPING_BAG_IS_EMPTY_MESSAGE).isVisible(),
                "Message about empty shopping bag is not displayed after removing the item");
        assertEquals("Zurück zum Shop", getText(RETURN_TO_SHOP_BUTTON).trim(),
                "Return to shop button text is not correct");
    }

    @Step("Verify shopping bag after page reload")
    public void verifyShoppingBagAfterReload() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        String productNameBeforeReload = getText(ITEM_NAME_IN_SHOPPING_BAG_TABLE).trim();
        String productCountBeforeReload = getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).inputValue();
        String productPriceBeforeReload = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE).replaceAll("[^0-9.,]", "").trim();
        context.page.reload();
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        String productNameAfterReload = getText(ITEM_NAME_IN_SHOPPING_BAG_TABLE).trim();
        String productCountAfterReload = getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).inputValue();
        String productPriceAfterReload = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE).replaceAll("[^0-9.,]", "").trim();

        assertEquals(productNameBeforeReload, productNameAfterReload,
                "Product name in shopping bag changed after page reload");
        assertEquals(productCountBeforeReload, productCountAfterReload,
                "Product count in shopping bag changed after page reload");
        assertEquals(productPriceBeforeReload, productPriceAfterReload,
                "Product price in shopping bag changed after page reload");
    }

    @Step("Get items list in shopping bag")
    public Map<String, List<String>> getItemsListInShoppingBag() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);

        List<String> itemNameList = new ArrayList<>();
        List<String> itemQuantityList = new ArrayList<>();
        List<String> itemPriceList = new ArrayList<>();

        int itemCount = getLocator(ROW_IN_SHOPPING_BAG_TABLE).count() - 1;
        for (int i = 0; i < itemCount; i++) {
            itemNameList.add(getText(ITEM_NAME_IN_SHOPPING_BAG_TABLE, i).trim());
            itemQuantityList.add(getLocator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE, i).inputValue());
            itemPriceList.add(getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE, i).replaceAll("[^0-9.,]", "").trim());
        }

        Map<String, List<String>> data = new HashMap<>();
        data.put("names", itemNameList);
        data.put("quantities", itemQuantityList);
        data.put("prices", itemPriceList);
        return data;
    }

    @Step("Verify items list in shopping bag")
    public void verifyItemsListInShoppingBag(Map<String, List<String>> expectedData) {
        Map<String, List<String>> actualData = getItemsListInShoppingBag();
        assertEquals(expectedData.get("names"), actualData.get("names"), "Product names mismatch");
        assertEquals(expectedData.get("quantities"), actualData.get("quantities"), "Quantities mismatch");
        assertEquals(expectedData.get("prices"), actualData.get("prices"), "Prices mismatch");
    }

    @Step("Verify product added to shopping bag")
    public ShoppingBagPage verifyProductPricesInShoppingBag() {
        waitForVisibility(SHOPPING_BAG_TABLE);
        waitForTableRowCount(ROW_IN_SHOPPING_BAG_TABLE, (productPriceList.size() + 1));
        context.page.waitForLoadState(LoadState.NETWORKIDLE);

        for (int i = 0; i < productPriceList.size(); i++) {
            String expectedPrice = productPriceList.get(i);
            String actualPrice = getText(ITEM_PRICE_IN_SHOPPING_BAG_TABLE, i).replaceAll("[^0-9.,]", "").trim();
            assertEquals(expectedPrice, actualPrice,
                    "Product price in shopping cart does not match the added product");
        }
        return this;
    }

    @Step("Remove item from shopping bag with counter")
    public ShoppingBagPage removeProductWithCounter() {
        click(COUNTER_MINUS_BUTTON);
        click(SHOPPING_BAG_UPDATE_BUTTON);
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        return this;
    }
}
