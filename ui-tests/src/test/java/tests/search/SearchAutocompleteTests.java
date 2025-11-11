package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.components.Header;
import tests.BaseTest;

import java.util.List;

@Epic("Search")
@Feature("Predictive dropdown")
public class SearchAutocompleteTests extends BaseTest {

    @Test
    public void guest_predictive_dropdown() {
        Header header = new Header(context);

        header.open();
        header.acceptCookiesIfPresent();

        header.typeSearch("man");
        Assertions.assertFalse(header.isPredictiveVisible(), "Predictive dropdown should appear only after typing 3 chars");
        header.typeSearch("man");
        Assertions.assertFalse(header.isPredictiveVisible(), "Predictive dropdown should be visible now");

        header.waitPredictiveNonEmpty();
        List<String> items = header.getPredictiveTexts(8);
        Assertions.assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }

    @Test
    public void loggedin_predictive_dropdown() {
        Header header = new Header(context);

        header.loginAsDefaultUser();

        header.typeSearch("m");
        Assertions.assertFalse(header.isPredictiveVisible(), "Predictive should not appear after 1 char");

        header.typeSearch("ma");
        Assertions.assertFalse(header.isPredictiveVisible(), "Predictive should not appear after 2 chars");

        header.typeSearch("man");
        Assertions.assertFalse(header.isPredictiveVisible(), "Predictive should be visible after 3 chars");

        header.waitPredictiveNonEmpty();
        List<String> items = header.getPredictiveTexts(8);
        Assertions.assertFalse(items.isEmpty(), "Predictive list must not be empty");
    }
}