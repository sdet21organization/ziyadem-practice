package tests.accountDetails;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.accountDetails.ProfilePage;
import pages.wishlist.AuthSteps;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@Feature("Account Details")
@DisplayName("Account Details Tests")
@Owner("Oleksiy Korniyenko")
public class ProfileTests extends BaseTest {

    private AuthSteps authSteps;
    private ProfilePage profilePage;

    @BeforeEach
    public void setUp() {
        authSteps = new AuthSteps(context);
        authSteps.login();

        profilePage = new ProfilePage(context);
        profilePage.open();
    }

    @Test
    @DisplayName("Empty First Name Field")
    void emptyFirstName() {
        profilePage.setFirstName("");
        String error = profilePage.getErrorMessage();
        assertTrue(error.contains("Vorname ist ein Pflichtfeld."), "Wrong error: " + error);
    }

    @Test
    @DisplayName("Empty Last Name Field")
    void emptyLastName() {
        profilePage.setLastName("");
        String error = profilePage.getErrorMessage();
        assertTrue(error.contains("Nachname ist ein Pflichtfeld."), "Wrong error: " + error);
    }

    @Test
    @DisplayName("Empty Display Name Field")
    void emptyDisplayName() {
        profilePage.setDisplayName("");
        String error = profilePage.getErrorMessage();
        assertTrue(error.contains("Anzeigename ist ein Pflichtfeld."), "Wrong error: " + error);
    }

    @Test
    @DisplayName("Empty Email Field")
    void emptyEmail() {
        profilePage.setEmail("");
        String error = profilePage.getErrorMessage();
        assertTrue(error.contains("E-Mail-Adresse ist ein Pflichtfeld."), "Wrong error: " + error);
    }

    @Test
    @DisplayName("All fields empty")
    void allFieldsEmpty() {
        profilePage.setAllFields("", "", "", "");
        String error = profilePage.getErrorMessage();
        assertTrue(error.contains("Vorname ist ein Pflichtfeld."), "Wrong error: " + error);
        assertTrue(error.contains("Nachname ist ein Pflichtfeld."), "Wrong error: " + error);
        assertTrue(error.contains("Anzeigename ist ein Pflichtfeld."), "Wrong error: " + error);
        assertTrue(error.contains("E-Mail-Adresse ist ein Pflichtfeld."), "Wrong error: " + error);
    }
}
