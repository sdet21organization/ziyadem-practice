package pages.language;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelector extends BasePage {

    private static final String TOGGLE = "div.gtranslate_wrapper div.gt_selected a";
    private static final String OPTIONS_CONTAINER = "div.gt_option";
    private static final String OPTIONS_ITEMS = "div.gt_option a";

    public LanguageSelector(TestContext context) {
        super(context);
    }

    @Step("Wait until language selector is visible")
    public void waitVisible() {
        Locator toggle = context.page.locator(TOGGLE).first();
        toggle.waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }

    @Step("Check if language selector is visible")
    public boolean isVisible() {
        Locator toggle = context.page.locator(TOGGLE).first();
        return toggle.count() > 0 && toggle.isVisible();
    }

    @Step("Open language dropdown")
    public void openDropdown() {
        waitVisible();
        Locator toggle = context.page.locator(TOGGLE).first();
        toggle.scrollIntoViewIfNeeded();
        context.page.waitForTimeout(200);
        toggle.click();
        Locator list = context.page.locator(OPTIONS_CONTAINER).first();
        list.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
    }

    @Step("Reopen language dropdown")
    public void reopen() {
        context.page.keyboard().press("Escape");
        context.page.waitForTimeout(300);
        openDropdown();
    }

    @Step("Scroll language list down")
    public void scrollDown() {
        Locator container = context.page.locator(OPTIONS_CONTAINER).first();
        container.evaluate("el => el.scrollBy(0, 200)");
        context.page.waitForTimeout(300);
    }

    @Step("Scroll language list up")
    public void scrollUp() {
        Locator container = context.page.locator(OPTIONS_CONTAINER).first();
        container.evaluate("el => el.scrollBy(0, -200)");
        context.page.waitForTimeout(300);
    }

    @Step("Get list of all language options")
    public List<String> getAllLanguages() {
        if (context.page.locator(OPTIONS_ITEMS).count() == 0) {
            openDropdown();
        }

        Locator items = context.page.locator(OPTIONS_ITEMS);
        items.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(5000));

        int count = items.count();
        List<String> langs = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            langs.add(items.nth(i).innerText().trim());
        }
        return langs;
    }

    @Step("Get current selected language label in widget")
    public String getCurrentLanguageLabel() {
        Locator toggle = context.page.locator(TOGGLE).first();
        return toggle.innerText().trim();
    }

    @Step("Check if language dropdown is open")
    public boolean isDropdownOpen() {
        Locator container = context.page.locator(OPTIONS_CONTAINER).first();
        return container.count() > 0 && container.isVisible();
    }

    @Step("Get current scrollTop of language dropdown")
    public double getScrollTop() {
        Locator container = context.page.locator(OPTIONS_CONTAINER).first();
        Object value = container.evaluate("el => el.scrollTop");
        if (value instanceof Double) return (Double) value;
        if (value instanceof Integer) return ((Integer) value).doubleValue();
        return 0.0;
    }

    @Step("Hover language option at index {index}")
    public void hoverOption(int index) {
        Locator items = context.page.locator(OPTIONS_ITEMS);
        items.nth(index).hover();
    }

    @Step("Click outside language dropdown")
    public void clickOutside() {
        context.page.locator("body").click();
        context.page.waitForTimeout(300);
    }

    @Step("Switch language to English")
    public void switchToEnglish() {
        if (isEnglishUi()) {
            return;
        }

        openDropdown();

        String englishSelector =
                "div.gt_option a[data-gt-lang='en'], div.gt_option a[title='English']";

        Locator english = context.page.locator(englishSelector).first();

        if (!english.isVisible()) {
            reopen();
            english = context.page.locator(englishSelector).first();
        }

        english.click(new Locator.ClickOptions().setTimeout(10000));
        context.page.waitForTimeout(1500);
    }

    @Step("Check that UI is in English (by language widget state)")
    public boolean isEnglishUi() {
        Locator selected = context.page.locator("div.gt_selected a").first();
        if (selected.count() > 0) {
            String selectedText = selected.innerText().toLowerCase().trim();
            if (selectedText.contains("english")) return true;
            if (selected.locator("img[alt='en']").count() > 0) return true;
        }

        String[] samples = new String[]{
                "Wishlist",
                "Cart",
                "Further information",
                "Checkout",
                "Login",
                "Imprint"
        };

        for (String text : samples) {
            Locator loc = context.page.locator("text=" + text);
            if (loc.count() > 0 && loc.first().isVisible()) {
                return true;
            }
        }

        return false;
    }
}