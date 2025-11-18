package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.components.Header;
import tests.BaseTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;


@Epic("UI Tests")
@Feature("Search")
@Owner("Kostiantyn Herasymets")
@DisplayName("Predictive search dropdown behavior")
public class SearchAutocompleteTests extends BaseTest {

    @Test
    @DisplayName("Guest user: predictive dropdown appears after typing 3+ characters")
    public void guest_predictive_dropdown() {
        Header header = openHomeAsGuest();

        header.typeSearch("ma");
        assertFalse(header.isPredictiveVisible(), "Should not appear before 3 characters");

        header.typeSearch("man");
        header.waitPredictiveNonEmpty();

        List<String> items = header.getPredictiveTexts(8);
        assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }

    @Test
    @DisplayName("Logged-in user: predictive dropdown appears after typing 3+ characters")
    public void loggedin_predictive_dropdown() {
        Header header = openHomeAsLoggedIn();

        header.typeSearch("ma");
        assertFalse(header.isPredictiveVisible(), "Should not appear before 3 characters");

        header.typeSearch("man");
        header.waitPredictiveNonEmpty();

        List<String> items = header.getPredictiveTexts(8);
        assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }

    private Header openHomeAsGuest() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();
        return new Header(context);
    }

    private Header openHomeAsLoggedIn() {
        BasePage base = new BasePage(context) {};
        base.open();
        base.acceptCookiesIfPresent();

        Header header = new Header(context);
        header.loginAsDefaultUser();
        return header;
    }
}