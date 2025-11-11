package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;

public class LoginModal {
    private final TestContext context;
    private final Locator root;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator submitButton;

    public LoginModal(TestContext context) {
        this.context = context;
        this.root = context.page.locator("form.login").first();
        this.usernameInput = root.locator("input#username, input[name='username'], input[name='log']").first();
        this.passwordInput = root.locator("input#password, input[name='password'], input[name='pwd']").first();
        this.submitButton = root.locator("button[name='login'], button[type='submit'], input[type='submit']").first();
    }

    @Step("Wait login modal visible")
    public void waitVisible() {
        root.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public boolean isVisible() {
        return root.isVisible();
    }

    public boolean LoginModalIsOpened() {
        return isVisible();
    }

    @Step("Login")
    public void login(String email, String password) {
        usernameInput.fill(email);
        passwordInput.fill(password);
        submitButton.click();
    }
}