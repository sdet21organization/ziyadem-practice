package pages.accountDetails;

import utils.ConfigurationReader;
import utils.TestData;

public class ProfileValues {
    public final String first;
    public final String last;
    public final String display;
    public final String email;

    public ProfileValues(String first, String last, String display, String email) {
        this.first = first;
        this.last = last;
        this.display = display;
        this.email = email;
    }

    public static ProfileValues random() {
        String first = TestData.randomFirstName();
        String last = TestData.randomLastName();
        String display = TestData.randomDisplayName(first, last);
        String email = ConfigurationReader.get("email");
        return new ProfileValues(first, last, display, email);
    }
}