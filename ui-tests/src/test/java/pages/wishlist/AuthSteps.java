package pages.wishlist;

import context.TestContext;
import io.qameta.allure.Step;
import pages.login.LoginPage;
import utils.ConfigurationReader;

public class AuthSteps {
    private final TestContext context;
    private final LoginPage loginPage;

    public AuthSteps(TestContext context) {
        this.context = context;
        this.loginPage = new LoginPage(context);
    }

    @Step("Login with credentials from config")
    public void login() {
        loginPage.openLoginPage();
        loginPage.submitWith(ConfigurationReader.get("email"), ConfigurationReader.get("password"));
    }

    @Step("Logout current user")
    public void logout() {
        context.page.navigate(utils.ConfigurationReader.get("URL") + "mein-konto");
        context.page.locator("a[href*='wp-login.php?action=logout']").first().click();
        context.page.waitForURL("**/mein-konto**");
    }
}
