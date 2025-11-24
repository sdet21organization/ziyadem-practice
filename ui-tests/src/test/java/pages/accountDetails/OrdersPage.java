package pages.accountDetails;

import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.accountDetails.OrdersPageElements.*;

public class OrdersPage extends BasePage {
    private final String expectedOrderNumber;
    private final String expectedOrderAmount;

    public OrdersPage(TestContext context, String expectedOrderNumber, String expectedOrderAmount) {
        super(context);
        this.expectedOrderNumber = expectedOrderNumber;
        this.expectedOrderAmount = expectedOrderAmount;
    }

    @Step("Verify that the order with number and amount is displayed in the Orders table")
    public void verifyOrderExistsInUserAccount() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        waitForVisibility(ORDERS_TABLE);
        assertTrue(getLocator(String.format(ORDER_NUMBER_IN_TABLE, expectedOrderNumber)).isVisible(),
                "Order with number " + expectedOrderNumber + " is not displayed in the Orders table");
        String actualOrderAmount = getText(String.format(ORDER_AMOUNT_IN_TABLE, expectedOrderNumber))
                .replaceAll("[^0-9.]", "").trim();
        assertEquals(expectedOrderAmount, actualOrderAmount,
                "Order amount for order number " + expectedOrderNumber + " is incorrect in the Orders table");
    }
}