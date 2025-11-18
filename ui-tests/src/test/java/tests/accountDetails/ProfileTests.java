package tests.accountDetails;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.accountDetails.ProfilePage;
import pages.accountDetails.ProfileValues;
import pages.wishlist.AuthSteps;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String oldValue = profilePage.getFirstName();
        profilePage.setFirstName("");
        String error = profilePage.getErrorMessage();
        String newValue = profilePage.getFirstName();
        assertTrue(error.contains("Vorname ist ein Pflichtfeld."), "Actual text: " + error);
        assertEquals(oldValue, newValue, "Value should remain unchanged");
    }

    @Test
    @DisplayName("Empty Last Name Field")
    void emptyLastName() {
        String oldValue = profilePage.getFirstName();
        profilePage.setLastName("");
        String error = profilePage.getErrorMessage();
        String newValue = profilePage.getFirstName();
        assertTrue(error.contains("Nachname ist ein Pflichtfeld."), "Actual text: " + error);
        assertEquals(oldValue, newValue, "Value should remain unchanged");
    }

    @Test
    @DisplayName("Empty Display Name Field")
    void emptyDisplayName() {
        String oldValue = profilePage.getFirstName();
        profilePage.setDisplayName("");
        String error = profilePage.getErrorMessage();
        String newValue = profilePage.getFirstName();
        assertTrue(error.contains("Anzeigename ist ein Pflichtfeld."), "Actual text: " + error);
        assertEquals(oldValue, newValue, "Value should remain unchanged");
    }

    @Test
    @DisplayName("Empty Email Field")
    void emptyEmail() {
        String oldValue = profilePage.getFirstName();
        profilePage.setEmail("");
        String error = profilePage.getErrorMessage();
        String newValue = profilePage.getFirstName();
        assertTrue(error.contains("E-Mail-Adresse ist ein Pflichtfeld."), "Actual text: " + error);
        assertEquals(oldValue, newValue, "Value should remain unchanged");
    }

    @Test
    @DisplayName("All fields empty")
    void allFieldsEmpty() {
        ProfileValues oldValues = profilePage.getValues();
        profilePage.setAllFields("", "", "", "");
        String error = profilePage.getErrorMessage();
        ProfileValues newValues = profilePage.getValues();
        assertTrue(error.contains("Vorname ist ein Pflichtfeld."), "Actual text: " + error);
        assertTrue(error.contains("Nachname ist ein Pflichtfeld."), "Actual text: " + error);
        assertTrue(error.contains("Anzeigename ist ein Pflichtfeld."), "Actual text: " + error);
        assertTrue(error.contains("E-Mail-Adresse ist ein Pflichtfeld."), "Actual text: " + error);
        assertEquals(oldValues.first, newValues.first, "First Name should remain unchanged");
        assertEquals(oldValues.last, newValues.last, "Last Name should remain unchanged");
        assertEquals(oldValues.display, newValues.display, "Display Name should remain unchanged");
        assertEquals(oldValues.email, newValues.email, "Email should remain unchanged");
    }

    @Test
    @DisplayName("Trim First Name")
    void trimFirstName() {
        profilePage.setFirstName("   Leonid   ");
        String value = profilePage.getFirstName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals("Leonid", value, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim Last Name")
    void trimLastName() {
        profilePage.setLastName("   Testoff   ");
        String value = profilePage.getLastName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals("Testoff", value, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim Display Name")
    void trimDisplayName() {
        profilePage.setDisplayName("   Test User   ");
        String value = profilePage.getDisplayName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals("Test User", value, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim Email")
    void trimEmail() {
        profilePage.setEmail("   my_test_email_rrr253010@mailnesia.com   ");
        String value = profilePage.getEmail();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals("my_test_email_rrr253010@mailnesia.com", value, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim All Fields")
    void trimAllFields() {
        profilePage.setAllFields(
                "   Leonid   ",
                "   Testoff   ",
                "   Leonid Testoff   ",
                "   my_test_email_rrr253010@mailnesia.com   "
        );
        ProfileValues values = profilePage.getValues();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals("Leonid", values.first, "Wrong trimmed First Name");
        assertEquals("Testoff", values.last, "Wrong trimmed Last Name");
        assertEquals("Leonid Testoff", values.display, "Wrong trimmed Display Name");
        assertEquals("my_test_email_rrr253010@mailnesia.com", values.email, "Wrong trimmed Email");
    }
}
