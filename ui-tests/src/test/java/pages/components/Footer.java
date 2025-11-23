package pages.components;

import com.microsoft.playwright.Locator;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;

import java.util.List;

public class Footer extends BasePage {

    public final Locator footerContainer;
    public final Locator footerLinks;
    public final Locator copyrightText;

    public Footer(TestContext context) {
        super(context);
        // Основной контейнер футера
        this.footerContainer = context.page.locator("#footer");
        // Ссылки меню
        this.footerLinks = context.page.locator(".menu-rechtliches-container li a");
        // Текст копирайта
        this.copyrightText = context.page.locator(".copyright-footer");
    }

    @Step("Get all footer link texts")
    public List<String> getFooterLinkTexts() {
        footerLinks.first().waitFor();
        return footerLinks.allInnerTexts();
    }

    @Step("Click footer link by name: {0}")
    public void clickFooterLink(String linkName) {
        Locator link = footerLinks.filter(new Locator.FilterOptions().setHasText(linkName)).first();
        link.click();
    }

    @Step("Get copyright text")
    public String getCopyrightText() {
        copyrightText.waitFor();
        return copyrightText.textContent().trim();
    }

    @Step("Scroll to footer")
    public void scrollToFooter() {
        footerContainer.scrollIntoViewIfNeeded();
    }

    @Step("Check if footer is visible")
    public boolean isFooterVisible() {
        return footerContainer.isVisible();
    }
}