package pages.purchaseFunction;

import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.purchaseFunction.OrderConfirmationPageElements.*;

public class OrderConfirmationPage extends BasePage {

    private final String expectedOrderAmount;

    public OrderConfirmationPage(TestContext context, String expectedOrderAmount) {
        super(context);
        this.expectedOrderAmount = expectedOrderAmount;
    }

    @Step("Verify bank details are displayed on Order Confirmation Page")
    public void verifyBankDetailsAreDisplayedOnOrderConfirmationPage() {
        waitForVisibility(BANK_DETAILS_SECTION);
        assertTrue(getLocator(BANK_NAME_DETAILS).isVisible(), "Bank Name details are not displayed on Order Confirmation Page");
        assertTrue(getLocator(ACCOUNT_NUMBER_DETAILS).isVisible(), "Account Number details are not displayed on Order Confirmation Page");
        assertTrue(getLocator(SORT_CODE_DETAILS).isVisible(), "Sort Code details are not displayed on Order Confirmation Page");
        assertTrue(getLocator(IBAN_DETAILS).isVisible(), "IBAN details are not displayed on Order Confirmation Page");
        assertTrue(getLocator(BIC_DETAILS).isVisible(), "BIC details are not displayed on Order Confirmation Page");
    }

    @Step("Verify order information is correct on Order Confirmation Page")
    public void verifyOrderInformationIsCorrect() {
        LocalDate currentDate = LocalDate.now();
        String expectedOrderDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        waitForVisibility(ORDER_INFORMATION_SECTION);
        assertTrue(getLocator(ORDER_NUMBER).isVisible(), "Order Number is not displayed on Order Confirmation Page");
        assertEquals(expectedOrderDate, getLocator(DATE).textContent().trim(), "Order Date is incorrect on Order Confirmation Page");
        assertEquals(expectedOrderAmount.replace(",", "."),
                getLocator(TOTAL).textContent().replaceAll("[^0-9.]", "").trim(),
                "Total Amount is incorrect on Order Confirmation Page");
        assertEquals("Direct Banküberweisung", getLocator(PAYMENT_METHOD).textContent().trim(),
                "Payment Method is incorrect on Order Confirmation Page");
    }

    @Step("Verify order information is correct on Order Confirmation Page")
    public void verifyOrderDetailsOnCofirmationPage() {
        waitForVisibility(ORDER_DETAILS_SECTION);
        assertEquals(expectedOrderAmount.replace(",", "."),
                getLocator(TOTAL_AMOUNT_IN_ORDER_DETAILS).textContent().replaceAll("[^0-9.]", "").trim(),
                "Total Amount in order details is incorrect on Order Confirmation Page");
    }
}