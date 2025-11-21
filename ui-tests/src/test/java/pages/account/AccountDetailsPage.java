package pages.account;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class AccountDetailsPage extends BasePage {

    public final Locator messageContainer;
    private final Locator currentPasswordInput;
    private final Locator newPasswordInput;
    private final Locator confirmPasswordInput;
    public final Locator saveChangesButton;

    public AccountDetailsPage(TestContext context) {
        super(context);
        this.messageContainer = context.page.locator(".message-container");
        ;
        this.currentPasswordInput = context.page.locator("input[name='password_current']");
        this.newPasswordInput = context.page.locator("input[name='password_1']");
        ;
        this.confirmPasswordInput = context.page.locator("input[name='password_2']");
        this.saveChangesButton = context.page.locator("button[name='save_account_details']");
    }

    @Step("Change user's current password")
    public void changeUserPassword(String currentPassword, String newPassword, String confirmPassword) {
        currentPasswordInput.fill(currentPassword);
        newPasswordInput.fill(newPassword);
        confirmPasswordInput.fill(confirmPassword);
        if (saveChangesButton.isEnabled()) {
            saveChangesButton.first().click();
        }
    }
}