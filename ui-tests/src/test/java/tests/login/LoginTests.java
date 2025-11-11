package tests.login;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import tests.BaseTest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Login Functionality")
@DisplayName("Login Tests")
@Owner("Slawa Sliepchenko")
public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Login page opens and form is visible")
    void openLoginPage_andVerifyFormVisible() {
        LoginPage login = new LoginPage(context);
        login.openLoginPage();
        assertTrue(login.isFormVisible(),
                "Login form elements should be visible");
    }

    @Test
    @DisplayName("Successful login with valid credentials")
    void loginSuccess() {
        LoginPage login = new LoginPage(context);
        login.openLoginPage();
        login.enterUsername(utils.ConfigurationReader.get("email"));
        login.enterPassword(utils.ConfigurationReader.get("password"));
        login.submit();
        assertTrue(login.isUserLoggedIn(),
                "User should be logged in and see account page");
    }

    @Test
    @DisplayName("Login with incorrect password shows error and stays on login page")
    void loginWithIncorrectPassword_showsError() {
        LoginPage login = new LoginPage(context);

        login.openLoginPage();
        login.submitWith(
                utils.ConfigurationReader.get("email"),
                "wrong_password_123!"
        );
        login.waitForErrorNotice();
        assertTrue(login.isErrorVisible(), "Error message should be displayed.");
        assertTrue(login.isFormVisible(), "Login form should still be visible.");
        assertTrue(context.page.url().contains("mein-konto"),
                "User should remain on the login page (/mein-konto).");
        assertTrue(login.errorContainsAny("Passwort", "password"),
                "Error should mention password");
        assertTrue(login.errorContainsAny("nicht korrekt", "incorrect", "wrong"),
                "Error should indicate incorrect password");
    }

    @Test
    @DisplayName("Login with non-existing user shows error and stays on login page")
    void loginFailsWithNonExistingUser() {
        LoginPage login = new LoginPage(context);

        login.openLoginPage();
        login.submitWith(
                "not-existing-user-" + System.currentTimeMillis() + "@mail.com",
                "DummyPassword123!"
        );
        login.waitForErrorNotice();
        assertTrue(login.isErrorVisible(), "Error should appear.");
        assertTrue(login.isFormVisible(), "Login form stays visible.");
        assertTrue(context.page.url().contains("mein-konto"),
                "User must remain on /mein-konto");
        System.out.println(">>> Error text: " + login.getErrorMessage());
        assertTrue(
                login.errorContainsAny(
                        "Benutzername", "E-Mail", "email", "username",
                        "existiert", "exist", "kein", "keine", "Konto", "account"
                ),
                "Error should indicate unknown email/username"
        );
    }

    @Test
    @DisplayName("Login fails with empty username shows error and stays on login page")
    void loginFailsWithEmptyUsername_noTextCheck() {
        LoginPage login = new LoginPage(context);

        login.openLoginPage();
        login.enterPassword("AnyPassword123!");
        login.submit();
        login.waitForErrorNotice();
        assertTrue(login.isErrorVisible(), "Error block should be visible");
        assertTrue(login.isFormVisible(), "Login form must remain visible");
        assertTrue(context.page.url().contains("mein-konto"),
                "Should stay on /mein-konto");
        assertTrue(login.errorContainsAny("Benutzername", "username"),
                "Error should mention username");
        assertTrue(login.errorContainsAny("erforderlich", "required", "leer", "empty"),
                "Error should indicate requirement/emptiness");
    }

    @Test
    @DisplayName("Login fails with empty password shows error and stays on login page")
    void loginFailsWithEmptyPassword_noTextCheck() {
        LoginPage login = new LoginPage(context);

        login.openLoginPage();
        login.enterUsername(utils.ConfigurationReader.get("email"));
        login.submit();
        login.waitForErrorNotice();
        assertTrue(login.isErrorVisible(), "Error block should be visible");
        assertTrue(login.isFormVisible(), "Login form must remain visible");
        assertTrue(context.page.url().contains("mein-konto"),
                "Should stay on /mein-konto");
        assertTrue(login.errorContainsAny("Passwort", "password"),
                "Error should mention password");
        assertTrue(login.errorContainsAny("erforderlich", "required", "leer", "empty"),
                "Error should indicate requirement/emptiness");
    }

    @Test
    @DisplayName("Login fails with both fields empty shows error and stays on login page")
    void loginFailsWithBothEmpty_noTextCheck() {
        LoginPage login = new LoginPage(context);

        login.openLoginPage();
        login.submit();
        login.waitForErrorNotice();
        assertTrue(login.isErrorVisible(), "Error block should be visible");
        assertTrue(login.isFormVisible(), "Login form must remain visible");
        assertTrue(context.page.url().contains("mein-konto"),
                "Should stay on /mein-konto");
        assertTrue(login.errorContainsAny("Benutzername", "username"),
                "Error should mention username");
        assertTrue(login.errorContainsAny("erforderlich", "required", "leer", "empty"),
                "Error should indicate requirement/emptiness");
    }

    @Test
    @DisplayName("'Remember me' checkbox toggles")
    void rememberMeCheckboxToggles() {
        LoginPage login = new LoginPage(context);
        login.openLoginPage();
        boolean defaultState = context.page.isChecked("#rememberme");
        assertFalse(defaultState, "Checkbox should be unchecked by default");
        login.toggleRememberMe();
        boolean checkedState = context.page.isChecked("#rememberme");
        assertTrue(checkedState, "Checkbox should be checked after click");
        login.toggleRememberMe();
        boolean uncheckedAgain = context.page.isChecked("#rememberme");
        assertFalse(uncheckedAgain, "Checkbox should toggle off on second click");
    }

    @Test
    @DisplayName("Password visibility toggle switches between masked and visible")
    void passwordVisibilityToggle_works() {
        LoginPage login = new LoginPage(context);
        login.openLoginPage();
        assertTrue(login.isFormVisible(), "Login form should be visible");
        login.enterPassword("SomeSecret123!");
        assertTrue(login.isPasswordMasked(), "Password should be masked initially");
        login.togglePasswordVisibility();
        assertTrue(login.isPasswordVisible(), "Password should become visible after toggle");
        login.togglePasswordVisibility();
        assertTrue(login.isPasswordMasked(), "Password should return to masked after second toggle");
    }

    @Test
    @DisplayName("'Forgot password' link opens the reset page and shows the form")
    void forgotPasswordLink_opensResetPage() {
        LoginPage login = new LoginPage(context);
        login.openLoginPage();
        login.clickForgotPassword();
        login.waitForResetPasswordPage();
        assertTrue(context.page.url().contains("lost-password"), "URL should contain 'lost-password'");
        assertTrue(context.page.isVisible("input[name='user_login']"), "Reset form must contain username/email input");
        assertTrue(context.page.isVisible("button[type='submit']"), "Reset form must contain a submit button");
    }

}
