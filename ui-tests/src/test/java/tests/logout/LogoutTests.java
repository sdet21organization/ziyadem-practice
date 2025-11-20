package tests.logout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.components.Header;
import pages.login.LoginPage;
import pages.login.LogoutConfirmPage;
import pages.login.LogoutPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Logout Functionality")
@DisplayName("Logout Tests")
@Owner("Slawa Sliepchenko")
public class LogoutTests extends BaseTest {

    @Test
    @DisplayName("Logout from account page shows login/register form")
    void logoutFromAccountPage_showsLoginForm() {
        Header header = new Header(context);
        LoginPage login = new LoginPage(context);
        LogoutPage logoutPage = new LogoutPage(context);
        header.logoutIfLoggedIn();
        login.openLoginPage();
        login.enterUsername(utils.ConfigurationReader.get("email"));
        login.enterPassword(utils.ConfigurationReader.get("password"));
        login.submit();
        assertTrue(login.isUserLoggedIn(),
                "User should be logged in and see account page before logout");
        logoutPage.clickLogoutLink();
        assertFalse(header.isLoggedIn(),
                "User should be logged out after clicking Abmelden");
        assertTrue(login.isFormVisible(),
                "Login form should be visible after logout");
    }

    @Test
    @DisplayName("Logout from header dropdown shows login/register form")
    void logoutFromHeaderDropdown_showsLoginForm() {
        Header header = new Header(context);
        LoginPage login = new LoginPage(context);
        LogoutConfirmPage confirm = new LogoutConfirmPage(context);
        header.logoutIfLoggedIn();
        login.openLoginPage();
        login.enterUsername(utils.ConfigurationReader.get("email"));
        login.enterPassword(utils.ConfigurationReader.get("password"));
        login.submit();
        assertTrue(login.isUserLoggedIn(),
                "User must be logged in before opening the header dropdown");
        header.hoverUserIcon();
        header.clickLogoutInDropdown();
        confirm.waitForConfirmationVisible();
        confirm.clickConfirmLogout();
        assertFalse(header.isLoggedIn(),
                "User should be logged out after confirming logout");
        assertTrue(login.isFormVisible(),
                "Login form should be visible after logout");
    }
}
