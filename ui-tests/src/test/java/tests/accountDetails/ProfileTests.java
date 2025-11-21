package tests.accountDetails;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.accountDetails.ProfilePage;
import pages.accountDetails.ProfileValues;
import pages.wishlist.AuthSteps;
import tests.BaseTest;
import utils.ConfigurationReader;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.*;

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
        String firstName = TestData.randomFirstName();
        String withSpaces = "   " + firstName + "   ";
        profilePage.setFirstName(withSpaces);
        String value = profilePage.getFirstName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(firstName, value, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim Last Name")
    void trimLastName() {
        String lastName = TestData.randomLastName();
        String withSpaces = "   " + lastName + "   ";
        profilePage.setLastName(withSpaces);
        String actual = profilePage.getLastName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(lastName, actual, "Wrong trimmed value");

    }

    @Test
    @DisplayName("Trim Display Name")
    void trimDisplayName() {
        String firstName = TestData.randomFirstName();
        String lastName = TestData.randomLastName();
        String displayName = firstName + " " + lastName;
        String withSpaces = "   " + displayName + "   ";
        profilePage.setDisplayName(withSpaces);
        String actual = profilePage.getDisplayName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(displayName, actual, "Wrong trimmed value");
    }

    @Test
    @DisplayName("Trim Email")
    void trimEmail() {
        String email = ConfigurationReader.get("email");
        String withSpaces = "   " + email + "   ";
        profilePage.setEmail(withSpaces);
        String actual = profilePage.getEmail();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(email, actual, "Wrong trimmed value");
    }


    @Test
    @DisplayName("Trim All Fields")
    void trimAllFields() {
        String firstName = TestData.randomFirstName();
        String lastName = TestData.randomLastName();
        String displayName = firstName + " " + lastName;
        String email = ConfigurationReader.get("email");
        String spacedFirst = "   " + firstName + "   ";
        String spacedLast = "   " + lastName + "   ";
        String spacedDisplay = "   " + displayName + "   ";
        String spacedEmail = "   " + email + "   ";
        profilePage.setAllFields(spacedFirst, spacedLast, spacedDisplay, spacedEmail);
        ProfileValues values = profilePage.getValues();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(firstName, values.first, "Wrong trimmed First Name");
        assertEquals(lastName, values.last, "Wrong trimmed Last Name");
        assertEquals(displayName, values.display, "Wrong trimmed Display Name");
        assertEquals(email, values.email, "Wrong trimmed Email");
    }


    @Test
    @Disabled("Known bug ZIYAD-61: First Name accepts only special characters and saves successfully")
    @DisplayName("Special characters in First Name")
    void charsInFirstName() {
        String invalid = TestData.SPECIAL_CHARS;
        profilePage.setFirstName(invalid);
        String error = profilePage.getErrorMessage();

        // TODO: Replace expected message after bug fix
        assertTrue(error.contains("Ungültiger Vorname."), "Actual text: " + error);
    }

    @Test
    @Disabled("Known bug ZIYAD-62: Last Name accepts only special characters and saves successfully")
    @DisplayName("Special characters in Last Name")
    void charsInLastName() {
        String invalid = TestData.SPECIAL_CHARS;
        profilePage.setLastName(invalid);
        String error = profilePage.getErrorMessage();

        // TODO: Replace expected message after bug fix
        assertTrue(error.contains("Ungültiger Nachname."), "Actual text: " + error);
    }

    @Test
    @DisplayName("Special characters in Display Name")
    void charsInDisplayName() {
        String invalid = TestData.SPECIAL_CHARS;
        profilePage.setDisplayName(invalid);
        String actual = profilePage.getDisplayName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual text: " + success);
        assertEquals(invalid, actual, "Display Name was not saved correctly");
    }

    @Test
    @DisplayName("Special characters in Email")
    void specialCharsInEmail() {
        String invalid = TestData.SPECIAL_CHARS;
        profilePage.setEmail(invalid);
        String msg = profilePage.getEmailValidationMessage();
        assertFalse(msg.isEmpty(), "Expected browser validation message but got empty string");
    }

    @Test
    @Disabled("Known bug ZIYAD-63: First Name accepts digits and saves successfully")
    @DisplayName("Digits in First Name")
    void digitsInFirstName() {
        String invalid = TestData.DIGITS;
        profilePage.setFirstName(invalid);
        String error = profilePage.getErrorMessage();

        // TODO: Update expected message when bug is fixed
        assertTrue(error.contains("Ungültiger Vorname."), "Actual text: " + error);
    }

    @Test
    @Disabled("Known bug ZIYAD-64: Last Name accepts digits and saves successfully")
    @DisplayName("Digits in Last Name")
    void digitsInLastName() {
        String invalid = TestData.DIGITS;
        profilePage.setLastName(invalid);
        String error = profilePage.getErrorMessage();

        // TODO: Update expected message after bug fix
        assertTrue(error.contains("Ungültiger Nachname."), "Actual text: " + error);
    }

    @Test
    @DisplayName("Invalid email format")
    void invalidEmailFormat() {
        String invalid = TestData.INVALID_EMAIL;
        profilePage.setEmail(invalid);
        String msg = profilePage.getEmailValidationMessage();
        assertFalse(msg.isEmpty(), "Expected browser validation message but got empty string");
    }

    @Test
    @DisplayName("Valid First Name")
    void validFirstName() {
        String firstName = TestData.randomFirstName();
        profilePage.setFirstName(firstName);
        String value = profilePage.getFirstName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual: " + success);
        assertEquals(firstName, value, "First Name not saved");
    }

    @Test
    @DisplayName("Valid Last Name")
    void validLastName() {
        String lastName = TestData.randomLastName();
        profilePage.setLastName(lastName);
        String value = profilePage.getLastName();
        String success = profilePage.getSuccessMessage();
        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual: " + success);
        assertEquals(lastName, value, "Last Name not saved");
    }

    @Test
    @DisplayName("Valid Display Name")
    void validDisplayName() {
        String first = TestData.randomFirstName();
        String last = TestData.randomLastName();
        String display = TestData.randomDisplayName(first, last);

        profilePage.setDisplayName(display);
        String value = profilePage.getDisplayName();
        String success = profilePage.getSuccessMessage();

        assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual: " + success);
        assertEquals(display, value, "Display Name not saved");
    }

    @Test
    @DisplayName("Valid Email")
    void validEmail() {
        String original = ConfigurationReader.get("email"); // ← правильный email
        String newEmail = "new_" + original; // пример нового валидного email

        try {
            profilePage.setEmail(newEmail);
            String value = profilePage.getEmail();
            String success = profilePage.getSuccessMessage();

            assertTrue(success.contains("Kontodetails erfolgreich geändert."), "Actual: " + success);
            assertEquals(newEmail, value, "Email not saved");
        } finally {
            // ВАЖНО: возвращаем Email обратно
            profilePage.setEmail(original);
        }
    }


}