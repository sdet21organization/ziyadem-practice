package pages.components;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class LoginModal extends BasePage {

    public final Locator loginFormPopup;
    private final Locator usernameInput;
    private final Locator passwordInput;
    public final Locator loginButton;
    public final Locator emailInput;
    private final Locator registrationButton;
    public final Locator errorMessage;

    public LoginModal(TestContext context) {
        super(context);
        this.loginFormPopup = context.page.locator("#login-form-popup, .mfp-content #login-form-popup");
        this.usernameInput = context.page.locator("input[name='username'], #username");
        this.passwordInput = context.page.locator("input[name='password'], #password");
        this.loginButton = context.page.locator("button[name='login'], .woocommerce-form-login__submit, button[type='submit']");
        this.emailInput =context.page.locator("input[name='email']");
        this.registrationButton =context.page.locator("button[name='register']");
        this.errorMessage =context.page.locator(".woocommerce-error");
    }

    @Step("Register new user with following email: {}")
    public void newUserRegistration(String email) {
        emailInput.fill(email);
        registrationButton.click();
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