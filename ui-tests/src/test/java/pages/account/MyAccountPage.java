package pages.account;

import com.microsoft.playwright.Locator;
import context.TestContext;
import pages.BasePage;

public class MyAccountPage extends BasePage {

    public final Locator greetingMessage;

    public MyAccountPage(TestContext context) {
        super(context);
        this.greetingMessage = context.page.locator(".row-main p");
    }
}