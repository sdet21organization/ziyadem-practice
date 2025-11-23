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
import utils.ConfigurationReader;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Epic("UI Tests")
@Feature("Footer")
@Story("Footer functionality")
@DisplayName("Footer tests")
@Owner("Oleksii Golos")
public class FooterTests extends BaseTest {

    @Override
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        Browser currentBrowser = context.browserContext.browser();

        if (context.browserContext != null) {
            context.browserContext.tracing().stop();
            context.browserContext.close();
        }

        context.browserContext = currentBrowser.newContext(
                new Browser.NewContextOptions()
                        .setLocale("de-DE")
                        .setTimezoneId("Europe/Berlin")
                        .setGeolocation(52.5200, 13.4050)
                        .setPermissions(List.of("geolocation"))
                        .setExtraHTTPHeaders(Map.of("Accept-Language", "de-DE,de;q=0.9"))
        );

        String baseUrl = ConfigurationReader.get("URL");
        String domain = getDomainName(baseUrl);

        context.browserContext.addCookies(List.of(
                new Cookie("googtrans", "/auto/de")
                        .setDomain(domain)
                        .setPath("/")
        ));

        context.browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));

        context.page = context.browserContext.newPage();
    }

    private String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException e) {
            return ".ziyadem.de";
        }
    }

    @Test
    @DisplayName("ZIYAD-T60: Verify presence and order of footer links")
    public void verifyFooterLinksOrder() {
        new Header(context).open();

        Footer footer = new Footer(context);
        footer.scrollToFooter();
        footer.waitForFooterLinksToLoad();

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
        footer.waitForFooterLinksToLoad();

        String linkToTest = "Datenschutzrichtlinie";
        footer.clickFooterLink(linkToTest);

        context.page.locator("body").filter(new Locator.FilterOptions().setHasText("Datenschutz")).waitFor();

        String pageTitle = context.page.title();
        boolean isTitleCorrect = pageTitle.contains("Datenschutz") || pageTitle.contains("Privacy");
        boolean isUrlCorrect = context.page.url().contains("datenschutz");

        Assertions.assertTrue(isTitleCorrect || isUrlCorrect,
                "Navigation failed. Page title: " + pageTitle + ", URL: " + context.page.url());

        context.page.goBack();

        String baseUrl = ConfigurationReader.get("URL");
        context.page.waitForURL(Pattern.compile(".*" + Pattern.quote(baseUrl.replace("https://", "").replace("http://", "")) + ".*"));

        Assertions.assertTrue(footer.isFooterVisible(), "Footer should be visible after back navigation");
    }

    @Test
    @DisplayName("ZIYAD-T67: Verify copyright text")
    public void verifyCopyrightText() {
        new Header(context).open();

        Footer footer = new Footer(context);
        footer.scrollToFooter();

        String actualText = footer.getCopyrightText();
        String currentYear = String.valueOf(Year.now().getValue());

        Assertions.assertTrue(actualText.contains("Copyright " + currentYear),
                "Copyright year matches current year (" + currentYear + ")");
        Assertions.assertTrue(actualText.contains("Ziyadem Naturladen"), "Company name is missing");
    }

    @Test
    @DisplayName("ZIYAD-T65: Verify footer consistency across pages")
    public void verifyFooterConsistency() {
        Header header = new Header(context);
        Footer footer = new Footer(context);

        header.open();
        footer.scrollToFooter();
        footer.waitForFooterLinksToLoad();
        List<String> homeLinks = footer.getFooterLinkTexts();

        String shopUrl = ConfigurationReader.get("URL") + "shop/";
        context.page.navigate(shopUrl);

        footer.scrollToFooter();
        footer.waitForFooterLinksToLoad();

        List<String> shopLinks = footer.getFooterLinkTexts();

        Assertions.assertEquals(homeLinks, shopLinks, "Footer links differ between Home and Shop pages");
    }
}