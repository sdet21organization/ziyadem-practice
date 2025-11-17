package pages.accountDetails;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

public class ProfilePage extends BasePage {

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator displayNameInput;
    private final Locator emailInput;
    private final Locator saveButton;
    private final Locator successMessage;
    private final Locator errorMessages;

    public ProfilePage(TestContext context) {
        super(context);
        this.firstNameInput = context.page.locator("#account_first_name");
        this.lastNameInput = context.page.locator("#account_last_name");
        this.displayNameInput = context.page.locator("#account_display_name");
        this.emailInput = context.page.locator("#account_email");
        this.saveButton = context.page.locator("button[name='save_account_details']");
        this.successMessage = context.page.locator("div.woocommerce-message");
        this.errorMessages = context.page.locator("ul.woocommerce-error");
    }

    @Step("Open Profile edit page")
    @Override
    public void open() {
        open("mein-konto/edit-account");
    }

    @Step("Set First Name and save")
    public void setFirstName(String value) {
        firstNameInput.fill(value);
        saveButton.click();
    }

    @Step("Set Last Name and save")
    public void setLastName(String value) {
        lastNameInput.fill(value);
        saveButton.click();
    }

    @Step("Set Display Name and save")
    public void setDisplayName(String value) {
        displayNameInput.fill(value);
        saveButton.click();
    }

    @Step("Set Email and save")
    public void setEmail(String value) {
        emailInput.fill(value);
        saveButton.click();
    }

    @Step("Set all fields and save")
    public void setAllFields(String firstName, String lastName, String displayName, String email) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        displayNameInput.fill(displayName);
        emailInput.fill(email);
        saveButton.click();
    }

    @Step("Get success message")
    public String getSuccessMessage() {
        return successMessage.innerText().trim();
    }

    @Step("Get error message")
    public String getErrorMessage() {
        errorMessages.first().waitFor();
        return errorMessages.first().innerText().trim();
    }
}
