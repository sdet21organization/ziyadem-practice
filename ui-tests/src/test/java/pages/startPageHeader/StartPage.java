package pages.startPageHeader;

import io.qameta.allure.Step;
import pages.BasePage;
import pages.shoppingBag.ShoppingBagPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.shoppingBag.ShoppingBagElements.SHOPPING_BAG_IS_EMPTY_MESSAGE;
import static pages.startPageHeader.StartPageHeaderElements.SHOPPING_BAG_ICON;

public class StartPage extends BasePage {
    public StartPage(context.TestContext context) {
        super(context);
    }

    @Step("Open start page")
    public StartPage openStartPage() {
        open();
        return this;
    }

    @Step("Open shopping bag page")
    public ShoppingBagPage openShoppingBagPage() {
        click(SHOPPING_BAG_ICON);
        return new ShoppingBagPage(context);
    }

    @Step("Click shopping bag icon and verify it is clickable")
    public void clickShoppingBagIconAndVerify() {
        assertTrue(context.page.isEnabled(SHOPPING_BAG_ICON), "Shopping bag icon button is not active");
        for (int i = 0; i < 3; i++) {
            try {
                click(SHOPPING_BAG_ICON);
                waitForVisibility(SHOPPING_BAG_IS_EMPTY_MESSAGE);
                return;
            } catch (Exception ignored) {}
        }
        throw new AssertionError("Failed to click on the shopping bag icon after 3 attempts");
    }
}
