package tests.registration;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pages.components.Header;
import pages.components.LoginModal;
import pages.myAccount.MyAccountPage;
import testdata.UserRegistrationValidation;
import tests.BaseTest;
import utils.ConfigurationReader;

@Epic("UI Tests")
@Feature("User Registration")
public class RegistrationTests extends BaseTest {

    @Test
    @DisplayName("Successful user registration with correct email")
    @Owner("Pavlusenko")
    public void userSuccessfulRegistration() {
        String email = ConfigurationReader.get("email");
        String user = email.substring(0,email.indexOf("@"));
        String domain = email.substring(email.indexOf("@"));
        String uniqueEmail = user + "+" + System.currentTimeMillis() + domain;

        Header header = new Header(context);
        header.open();
        header.clickAccountButton();
        LoginModal loginModal = new LoginModal(context);
        loginModal.newUserRegistration(uniqueEmail);
        header.clickAccountButtonLogged();
        MyAccountPage myAccountPage = new MyAccountPage(context);
        String actualMessage = myAccountPage.greetingMessage.first().textContent();

        Assertions.assertTrue(actualMessage.contains(user));
    }

    @ParameterizedTest
    @EnumSource(UserRegistrationValidation.class)
    @DisplayName("User registration with incorrect email")
    @Owner("Pavlusenko")
    public void userRegistrationErrorValidation(UserRegistrationValidation userRegistrationValidation) {

        Header header = new Header(context);
        header.open();
        header.clickAccountButton();
        LoginModal loginModal = new LoginModal(context);
        loginModal.newUserRegistration(userRegistrationValidation.getEmail());

        String validationMessage = (String) loginModal.emailInput.evaluate("el => el.validationMessage");

        Assertions.assertEquals(userRegistrationValidation.getExpectedErrorMessage(), validationMessage);
    }

    @Test
    @DisplayName("User registration with already existed email")
    @Owner("Pavlusenko")
    public void tryAlreadyRegisteredUser() {
        String email = ConfigurationReader.get("email");

        Header header = new Header(context);
        header.open();
        header.clickAccountButton();
        LoginModal loginModal = new LoginModal(context);
        loginModal.newUserRegistration(email);

        String actualMessage = loginModal.errorMessage.textContent().trim();
        String expectedMessage = "Fehler: Es ist bereits ein Konto für "+email+" registriert. Bitte melde dich an oder verwende eine andere E-Mail-Adresse.";

        Assertions.assertEquals(expectedMessage,actualMessage);
    }
}