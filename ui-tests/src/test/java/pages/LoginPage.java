package pages;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    private final String usernameInput;
    private final String passwordInput;
    private final String submitButton;
    private final String rememberMeCheckbox;
    private final String forgotPasswordLink;
    private final String showPasswordBtn;
    private final String errorContainer;
    private final String errorItemText;

    public LoginPage(TestContext context) {
        super(context);
        this.usernameInput = "#username";
        this.passwordInput = "#password";
        this.submitButton = "button[name='login']";
        this.rememberMeCheckbox = "#rememberme";
        this.forgotPasswordLink = "a[href*='lost-password']";
        this.showPasswordBtn = "button.show-password-input";
        this.errorContainer = "ul.woocommerce-error";
        this.errorItemText = "ul.woocommerce-error li";
    }

    @Step("Open Login page")
    public void openLoginPage() {
        open("/mein-konto/");
        waitForVisible(usernameInput);
    }

    @Step("Check login form is visible")
    public boolean isFormVisible() {
        return context.page.isVisible(usernameInput)
                && context.page.isVisible(passwordInput)
                && context.page.isVisible(submitButton);
    }

    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    @Step("Toggle 'Remember me' checkbox")
    public void toggleRememberMe() {
        click(rememberMeCheckbox);
    }

    @Step("Submit login form")
    public void submit() {
        click(submitButton);
    }

    @Step("Submit login with username & password")
    public void submitWith(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        submit();
    }

    @Step("Click 'Forgot password' link")
    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }

    @Step("Login error notice is visible")
    public boolean isErrorVisible() {
        return context.page.isVisible(errorContainer);
    }

    @Step("User is logged in")
    public boolean isUserLoggedIn() {
        return context.page.url().contains("/mein-konto")
                && !isFormVisible();
    }

    @Step("Toggle password visibility")
    public void togglePasswordVisibility() {
        click(showPasswordBtn);
    }

    @Step("Is password masked (type=password)?")
    public boolean isPasswordMasked() {
        String type = context.page.getAttribute(passwordInput, "type");
        return "password".equalsIgnoreCase(type);
    }

    @Step("Is password visible (type=text)?")
    public boolean isPasswordVisible() {
        String type = context.page.getAttribute(passwordInput, "type");
        return "text".equalsIgnoreCase(type);
    }

    @Step("Wait for Reset Password page to load")
    public void waitForResetPasswordPage() {
        context.page.locator("input[name='user_login']").waitFor(
                new Locator.WaitForOptions().setTimeout(5000)
        );
    }

    @Step("Wait for error notice to appear")
    public void waitForErrorNotice() {
        context.page.locator(errorContainer)
                .waitFor(new Locator.WaitForOptions().setTimeout(5000));
    }

    @Step("Get error message text")
    public String getErrorMessage() {
        String text = context.page.textContent(errorItemText);
        return text == null ? "" : text.trim();
    }

    @Step("Error text contains any of: {words}")
    public boolean errorContainsAny(String... words) {
        String text = getErrorMessage().toLowerCase();
        for (String w : words) {
            if (text.contains(w.toLowerCase())) return true;
        }
        return false;
    }

}