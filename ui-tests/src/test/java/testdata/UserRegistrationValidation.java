package testdata;

public enum UserRegistrationValidation {

    INVALID_EMAIL_EMPTY("", "Please fill out this field."),
    INVALID_EMAIL_NO_AT("testexample.com", "Please include an '@' in the email address. 'testexample.com' is missing an '@'."),
    INVALID_EMAIL_NO_DOMAIN("test@", "Please enter a part following '@'. 'test@' is incomplete."),
    INVALID_EMAIL_NO_USERNAME("@example.com", "Please enter a part followed by '@'. '@example.com' is incomplete."),
    INVALID_EMAIL_SPACES("test @example.com", "A part followed by '@' should not contain the symbol ' '.");

    private final String email;
    private final String expectedErrorMessage;

    UserRegistrationValidation(String email, String expectedErrorMessage) {
        this.email = email;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    public String getEmail() {
        return email;
    }

    public String getExpectedErrorMessage() {
        return expectedErrorMessage;
    }
}