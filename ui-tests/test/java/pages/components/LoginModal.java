package pages.components;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class LoginModal extends BasePage {

    public final Locator loginFormPopup;

    public LoginModal(TestContext context) {
        super(context);
        this.loginFormPopup = context.page.locator("#login-form-popup");
    }

    @Step("Check if Login modal is opened")
    public boolean LoginModalIsOpened() {
        return loginFormPopup.isVisible();
    }
}