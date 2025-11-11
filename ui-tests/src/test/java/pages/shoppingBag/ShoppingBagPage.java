package pages.shoppingBag;

import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.text.DecimalFormat;
import java.util.List;

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

    @Step("Verify increased quantity")
    public void verifyIncreasedQuantityInShoppingBag() {
        context.page.waitForSelector(ROW_IN_SHOPPING_BAG_TABLE);
        String quantity = context.page.locator(ITEM_COUNT_IN_SHOPPING_BAG_TABLE).first().inputValue();
        if (!quantity.equals("2")) {
            throw new AssertionError("Expected quantity 2 but got " + quantity);
        }
    }

    @Step("Verify all products in bag")
    public ShoppingBagPage verifyAllProductsAddedToShoppingBag() {
        waitForVisible(SHOPPING_BAG_TABLE);
        context.page.waitForLoadState(LoadState.NETWORKIDLE);

        int expectedRows = productPriceList.size();
        int actualRows = context.page.locator(ROW_IN_SHOPPING_BAG_TABLE).count();

        if (actualRows != expectedRows) {
            throw new AssertionError("Expected " + expectedRows + " rows but got " + actualRows);
        }

        for (int i = 0; i < expectedRows; i++) {
            String expectedName = productNameList.get(i);
            String expectedPrice = productPriceList.get(i);

            String actualName = context.page.locator(ITEM_NAME_IN_SHOPPING_BAG_TABLE).nth(i).innerText().trim();
            String actualPrice = context.page.locator(ITEM_PRICE_IN_SHOPPING_BAG_TABLE).nth(i).innerText().replaceAll("[^0-9.,]", "").trim();

            if (!expectedName.equals(actualName)) {
                throw new AssertionError("Names mismatch: " + expectedName + " vs " + actualName);
            }
            if (!expectedPrice.equals(actualPrice)) {
                throw new AssertionError("Prices mismatch: " + expectedPrice + " vs " + actualPrice);
            }
        }
        return this;
    }

    @Step("Verify total amount")
    public void verifyAmountInShoppingBag() {
        double sum = productPriceList.stream()
                .map(p -> p.replace(",", "."))
                .mapToDouble(Double::parseDouble)
                .sum();

        String expectedAmount = new DecimalFormat("0.00").format(sum);
        String actualAmount = context.page.locator(AMOUNT_IN_SHOPPING_BAG)
                .innerText()
                .replaceAll("[^0-9.,]", "")
                .trim();

        if (!expectedAmount.equals(actualAmount)) {
            throw new AssertionError("Expected amount " + expectedAmount + " but got " + actualAmount);
        }
    }
}