package tests.language;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BasePage;
import pages.language.LanguageSelector;
import tests.BaseTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("ZIYAD-24 Language selector")
@Feature("AC2: selector options")
@Owner("Kostiantyn Herasymets")
@DisplayName("Selector displays exactly 11 supported languages")
public class LanguageSelectorOptionsTests extends BaseTest {

    @Test
    @DisplayName("Guest: dropdown shows 11 options, supports scroll, reopen and close behaviour")
    void languageSelectorFullListTest() {
        BasePage page = new BasePage(context) {};
        page.open("/");
        page.acceptCookiesIfPresent();

        LanguageSelector selector = new LanguageSelector(context);

        selector.waitVisible();
        String currentBefore = selector.getCurrentLanguageLabel();

        selector.openDropdown();
        assertTrue(selector.isDropdownOpen(), "Dropdown must be open after click");

        selector.hoverOption(1);

        List<String> langsFirstOpen = selector.getAllLanguages();
        assertEquals(11, langsFirstOpen.size(), "Dropdown must contain 11 options");
        assertTrue(langsFirstOpen.stream().allMatch(s -> !s.isBlank()),
                "All language names must be non-empty");

        selector.scrollDown();
        double scrollBefore = selector.getScrollTop();

        selector.reopen();
        assertTrue(selector.isDropdownOpen(), "Dropdown must be open after reopen");
        double scrollAfter = selector.getScrollTop();
        assertEquals(scrollBefore, scrollAfter, 1.0,
                "Scroll position should be preserved after reopen");

        List<String> langsSecondOpen = selector.getAllLanguages();
        assertEquals(11, langsSecondOpen.size(), "Dropdown must still contain 11 options after reopen");
        assertTrue(langsSecondOpen.stream().allMatch(s -> !s.isBlank()),
                "All language names after reopen must be non-empty");

        selector.clickOutside();
        assertTrue(selector.isVisible(), "Selector toggle should remain visible after closing dropdown");
        assertTrue(!selector.isDropdownOpen(), "Dropdown must be closed after clicking outside");
        String currentAfter = selector.getCurrentLanguageLabel();
        assertEquals(currentBefore, currentAfter,
                "Previously active language should remain selected after closing dropdown");
    }
}