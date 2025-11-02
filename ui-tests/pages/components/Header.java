package pages.components;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class Header extends BasePage {

    private final Locator accountButton;

    public Header(TestContext context) {
        super(context);
        this.accountButton = context.page.locator("a[data-open=\"#login-form-popup\"]\n");
    }

    @Step ("Click 'Account' button")
    public void clickAccountButton(){
        accountButton.click();
    }
}
