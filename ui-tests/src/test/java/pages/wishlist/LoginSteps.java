package pages.wishlist;

import io.qameta.allure.Step;
import pages.LoginPage;
import context.TestContext;
import utils.ConfigurationReader;

public class LoginSteps {
    private final LoginPage loginPage;

    public LoginSteps(TestContext context) {
        this.loginPage = new LoginPage(context);
    }

    @Step("Login with credentials from config")
    public void login() {
        loginPage.openLoginPage();
        loginPage.submitWith(ConfigurationReader.get("email"), ConfigurationReader.get("password"));
    }
}
