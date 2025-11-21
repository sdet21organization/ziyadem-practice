package tests.changePassword;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.account.AccountDetailsPage;
import pages.components.Header;
import pages.components.LoginModal;
import tests.BaseTest;
import utils.ConfigurationReader;

@Epic("UI Tests")
@Feature("User Registration")
@DisplayName("Check change password functionality")
public class ChangePasswordTests extends BaseTest {

    @BeforeEach
    public void loginAsDefinedUser() {
        String email = ConfigurationReader.get("change-password-email");
        String password = ConfigurationReader.get("change-password-password");
        Header header = new Header(context);
        header.open();
        header.clickAccountButton();

        LoginModal loginModal = new LoginModal(context);
        loginModal.loginButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        loginModal.login(email, password);
        loginModal.open("mein-konto/edit-account");
    }

    @Test
    @DisplayName("Successful changing of user password")
    @Owner("Pavlusenko")
    public void changePasswordSuccessful() {
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(context);

        String currentPassword = ConfigurationReader.get("change-password-password");
        String newPassword = currentPassword + "new";

        boolean passwordIsChanged = false;
        try {
            accountDetailsPage.changeUserPassword(currentPassword, newPassword, newPassword);
            passwordIsChanged = true;

            String expectedMessage = "Kontodetails erfolgreich geändert.";
            String actualMessage = accountDetailsPage.messageContainer.first().textContent().trim();

            Assertions.assertEquals(expectedMessage, actualMessage,"Unexpected message: " + actualMessage);
        } finally {
            //return password to previous one
            if (passwordIsChanged) {
                accountDetailsPage.changeUserPassword(newPassword, currentPassword, currentPassword);
            }
        }
    }

    @Test
    @DisplayName("Check error message when current password is wrong")
    @Owner("Pavlusenko")
    public void changePasswordWithIncorrectCurrentPassword() {
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(context);

        String currentPassword = ConfigurationReader.get("change-password-password") + "wrong";
        String newPassword = ConfigurationReader.get("change-password-password") + "new";

        accountDetailsPage.changeUserPassword(currentPassword, newPassword, newPassword);
        String actualMessage = accountDetailsPage.messageContainer.first().textContent().trim();
        String pattern = "Dein (aktuelles|derzeitiges) Passwort ist nicht korrekt.";
        Assertions.assertTrue(
                actualMessage.matches(pattern),
                "Unexpected error message: " + actualMessage
        );

    }

    @Test
    @DisplayName("Check error message when password confirmation is wrong")
    @Owner("Pavlusenko")
    public void changePasswordWithIncorrectPasswordConfirmation() {
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(context);

        String currentPassword = ConfigurationReader.get("change-password-password");
        String newPassword = currentPassword + "new";
        String wrongPasswordConfirmation = newPassword + "wrong";


        accountDetailsPage.changeUserPassword(currentPassword, newPassword, wrongPasswordConfirmation);

        String expectedMessage = "Die neuen Passwörter stimmen nicht überein.";
        String actualMessage = accountDetailsPage.messageContainer.first().textContent().trim();

        Assertions.assertEquals(expectedMessage, actualMessage,"Unexpected error message: " + actualMessage);
    }

    @Test
    @DisplayName("Check that 'Save' button is inactive when new password doesn't match security rules")
    @Owner("Pavlusenko")
    public void changePasswordWithWeakNewPassword() {
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(context);

        String currentPassword = ConfigurationReader.get("change-password-password");
        String newPassword = "weak";
        accountDetailsPage.changeUserPassword(currentPassword, newPassword, newPassword);
        Assertions.assertTrue(accountDetailsPage.saveChangesButton.isDisabled());
    }
}