package tests.footer;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.Cookie;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.components.Footer;
import pages.components.Header;
import tests.BaseTest;

import java.util.List;
import java.util.regex.Pattern;

@Epic("UI Tests")
@Feature("Footer")
@Story("Footer functionality")
@DisplayName("Footer tests")
@Owner("Oleksii Golos")
public class FooterTests extends BaseTest {

    @BeforeEach
    public void setupGermanLanguage() {
        Browser currentBrowser = context.browserContext.browser();
        context.browserContext.close();

        context.browserContext = currentBrowser.newContext(
                new Browser.NewContextOptions()
                        .setLocale("de-DE")
                        .setTimezoneId("Europe/Berlin")
                        .setGeolocation(52.5200, 13.4050)
                        .setPermissions(List.of("geolocation"))
                        .setExtraHTTPHeaders(java.util.Map.of("Accept-Language", "de-DE,de;q=0.9"))
        );

        context.browserContext.addCookies(List.of(
                new Cookie("googtrans", "/auto/de")
                        .setDomain(".ziyadem.de")
                        .setPath("/")
        ));

        context.browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));

        context.page = context.browserContext.newPage();
    }

    @Test
    @DisplayName("ZIYAD-T60: Verify presence and order of footer links")
    public void verifyFooterLinksOrder() {
        new Header(context).open();

        Footer footer = new Footer(context);
        footer.scrollToFooter();

        footer.footerLinks.first().filter(new Locator.FilterOptions().setHasText("Unternehmensinformationen")).waitFor();

        List<String> expectedLinks = List.of(
                "Unternehmensinformationen",
                "Nutzungsbedingungen",
                "Datenschutzrichtlinie",
                "Versand- und Rückgabeinformationen",
                "Stornierung und Rückerstattung",
                "Kommunikation"
        );

        List<String> actualLinks = footer.getFooterLinkTexts();

        Assertions.assertEquals(expectedLinks.size(), actualLinks.size(), "Number of footer links is incorrect");
        Assertions.assertEquals(expectedLinks, actualLinks, "Footer links order or text mismatch");
    }

    @Test
    @DisplayName("ZIYAD-T61 & T62: Verify link navigation and Back button")
    public void verifyLinkNavigationAndBack() {
        Header header = new Header(context);
        header.open();

        Footer footer = new Footer(context);
        footer.scrollToFooter();

        footer.footerLinks.first().filter(new Locator.FilterOptions().setHasText("Unternehmensinformationen")).waitFor();

        Locator linkElement = footer.footerLinks.filter(new Locator.FilterOptions().setHasText("Datenschutzrichtlinie")).first();
        String expectedUrl = linkElement.getAttribute("href");

        linkElement.click();

        context.page.waitForURL(expectedUrl);

        Assertions.assertEquals(expectedUrl, context.page.url(), "URL did not match the expected link attribute");

        context.page.goBack();
        context.page.waitForURL(Pattern.compile(".*ziyadem.de.*"));

        Assertions.assertTrue(context.page.url().contains("ziyadem.de"), "Back button did not return to homepage");
        Assertions.assertTrue(footer.isFooterVisible(), "Footer should be visible after back navigation");
    }

    @Test
    @DisplayName("ZIYAD-T67: Verify copyright text")
    public void verifyCopyrightText() {
        new Header(context).open();

        Footer footer = new Footer(context);
        footer.scrollToFooter();

        String actualText = footer.getCopyrightText();

        Assertions.assertTrue(actualText.contains("Copyright 2025"), "Copyright year is missing");
        Assertions.assertTrue(actualText.contains("Ziyadem Naturladen"), "Company name is missing");
    }

    @Test
    @DisplayName("ZIYAD-T65: Verify footer consistency across pages")
    public void verifyFooterConsistency() {
        Header header = new Header(context);
        Footer footer = new Footer(context);

        header.open();
        footer.scrollToFooter();

        footer.footerLinks.first().filter(new Locator.FilterOptions().setHasText("Unternehmensinformationen")).waitFor();
        List<String> homeLinks = footer.getFooterLinkTexts();

        context.page.navigate("https://ziyadem.de/shop/");

        footer.scrollToFooter();
        footer.footerLinks.first().filter(new Locator.FilterOptions().setHasText("Unternehmensinformationen")).waitFor();

        List<String> shopLinks = footer.getFooterLinkTexts();

        Assertions.assertEquals(homeLinks, shopLinks, "Footer links differ between Home and Shop pages");
    }
}