package pages.components;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class LoginModal extends BasePage {

    public final Locator loginFormPopup;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginModal(TestContext context) {
        super(context);
        this.loginFormPopup = context.page.locator("#login-form-popup, .mfp-content #login-form-popup");
        this.usernameInput = context.page.locator("input[name='username'], #username");
        this.passwordInput = context.page.locator("input[name='password'], #password");
        this.loginButton = context.page.locator("button[name='login'], .woocommerce-form-login__submit, button[type='submit']");
    }

    @Step("Check if Login modal is opened")
    public boolean LoginModalIsOpened() {
        return loginFormPopup.isVisible();
    }

    @Step("Wait until Login modal is visible")
    public void waitVisible() {
        loginFormPopup.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }

    @Step("Login as {email}")
    public void login(String email, String password) {
        usernameInput.first().fill(email);
        passwordInput.first().fill(password);
        loginButton.first().click();
    }
}