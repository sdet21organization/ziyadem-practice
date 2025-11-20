package pages.login;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class LogoutPage extends BasePage {

    private final Locator logoutLink;

    public LogoutPage(TestContext context) {
        super(context);
        this.logoutLink = context.page.locator("a[href*='action=logout']");
    }

    @Step("Click 'Abmelden' on /mein-konto/ (no confirmation)")
    public void clickLogoutLink() {
        logoutLink.first().click();
    }

}

