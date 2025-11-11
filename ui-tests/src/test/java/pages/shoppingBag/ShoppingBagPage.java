package pages.shoppingBag;

import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.shoppingBag.ShoppingBagElements.*;

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
}
