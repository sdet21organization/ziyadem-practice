package pages.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class LogoutConfirmPage extends BasePage {

    private final Locator confirmText;
    private final Locator confirmButton;
    private final Locator loggedInMarker;

    public LogoutConfirmPage(TestContext context) {
        super(context);
        this.confirmText = context.page.getByText("Bist du sicher, dass du dich abmelden willst?");
        this.confirmButton = context.page.getByText("Bestätigen und abmelden");
        this.loggedInMarker = context.page.locator("body.logged-in");
    }

    @Step("Wait for logout confirmation message to be visible")
    public void waitForConfirmationVisible() {
        confirmText.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(8000));
    }

    @Step("Click 'Bestätigen und abmelden' button")
    public void clickConfirmLogout() {
        confirmButton.click();
        loggedInMarker.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.DETACHED)
                .setTimeout(8000));
    }
}