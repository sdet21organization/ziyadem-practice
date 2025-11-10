package pages.startPageHeader;

import io.qameta.allure.Step;
import pages.BasePage;
import pages.shoppingBag.ShoppingBagPage;

import static pages.startPageHeader.StartPageHeaderElements.SHOPPING_BAG_ICON;

public class StartPage extends BasePage {
    public StartPage(context.TestContext context) {
        super(context);
    }

    @Step("Open shopping bag page")
    public ShoppingBagPage openShoppingBagPage() {
        click(SHOPPING_BAG_ICON);
        return new ShoppingBagPage(context);
    }
}
