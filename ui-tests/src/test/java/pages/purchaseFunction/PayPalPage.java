package pages.purchaseFunction;

import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PayPalPage extends BasePage {
    public PayPalPage(TestContext context) {
        super(context);
    }

    @Step("Verify PayPal page is opened")
    public void verifyPayPalPageIsOpened() {
        context.page.waitForURL(url -> url.contains("paypal.com"));
        assertTrue(context.page.url().contains("paypal.com"),
                "PayPal page is not opened after clicking Place Order button with PayPal method selected");
    }
}