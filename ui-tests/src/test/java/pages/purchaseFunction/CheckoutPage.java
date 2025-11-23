package pages.purchaseFunction;

import com.microsoft.playwright.options.LoadState;
import context.TestContext;
import io.qameta.allure.Step;
import pages.BasePage;
import pages.startPageHeader.StartPage;
import utils.ConfigurationReader;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pages.purchaseFunction.CheckoutPageElements.*;
import static pages.purchaseFunction.OrderConfirmationPageElements.ORDER_CONFIRMATION_PAGE_TITLE;
import static pages.purchaseFunction.OrderConfirmationPageElements.SUCCESSFUL_ORDER_CREATION_MESSAGE;

public class CheckoutPage extends BasePage {

    private final List<String> shoppingBagPriceList;
    private String totalAmount;

    public CheckoutPage(TestContext context) {
        this(context, null);
    }

    public CheckoutPage(TestContext context, List<String> shoppingBagPriceList) {
        super(context);
        this.shoppingBagPriceList = shoppingBagPriceList;
    }

    @Step("Verify checkout page is opened")
    public void verifyCheckoutPageIsOpened() {
        waitForVisibility(CHECKOUT_DETAILS_HEADER);
        assertTrue(getLocator(EMAIL_INPUT_FIELD).isVisible(), "Email input field is not visible on checkout page");
        assertTrue(getLocator(FIRST_NAME_INPUT_FIELD).isVisible(), "First name input field is not visible on checkout page");
        assertTrue(getLocator(LAST_NAME_INPUT_FIELD).isVisible(), "Last name input field is not visible on checkout page");
        assertTrue(getLocator(COUNTRY_SELECT).isVisible(), "Country select is not visible on checkout page");
        assertTrue(getLocator(STREET_INPUT_FIELD).isVisible(), "Street input field is not visible on checkout page");
        assertTrue(getLocator(APARTMENT_NUMBER_INPUT_FIELD).isVisible(), "Apartment number input field is not visible on checkout page");
        assertTrue(getLocator(POSTCODE_INPUT_FIELD).isVisible(), "Postcode input field is not visible on checkout page");
        assertTrue(getLocator(CITY_INPUT_FIELD).isVisible(), "City input field is not visible on checkout page");
        assertTrue(getLocator(PHONE_NUMBER_INPUT_FIELD).isVisible(), "Phone number input field is not visible on checkout page");
        assertTrue(getLocator(CREATE_ACCOUNT_CHECKBOX).isVisible(), "Create account checkbox is not visible on checkout page");
        assertTrue(getLocator(SHIP_TO_DIFFERENT_ADDRESS_CHECKBOX).isVisible(), "Ship to different address checkbox is not visible on checkout page");
        assertTrue(getLocator(COMMENTS_TEXTAREA).isVisible(), "Comments textarea is not visible on checkout page");
        assertTrue(getLocator(PLACE_ORDER_BUTTON).isVisible(), "Place order button is not visible on checkout page");
    }

    @Step("Validate required field error alerts")
    public void validateAllEmptyFields() {
        waitForVisibility(REQUIRED_FIELDS_ERROR_ALERT_CONTAINER);
        assertTrue(getLocator(REQUIRED_FIELD_EMAIL_ALERT).isVisible(), "Required field 'E-Mail address' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_FIRST_NAME_ALERT).isVisible(), "Required field 'First name' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_LAST_NAME_ALERT).isVisible(), "Required field 'Last name' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_STREET_ALERT).isVisible(), "Required field 'Street' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_POSTCODE_ALERT).isVisible(), "Required field 'Postcode' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_CITY_ALERT).isVisible(), "Required field 'City' alert is not visible");
        assertTrue(getLocator(REQUIRED_FIELD_PHONE_ALERT).isVisible(), "Required field 'Phone' alert is not visible");
    }

    @Step("Fill all required fields on checkout page")
    public CheckoutPage fillAllRequiredFields() {
        getLocator(EMAIL_INPUT_FIELD).fill(ConfigurationReader.get("email"));
        getLocator(FIRST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("firstName"));
        getLocator(LAST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("lastName"));
        context.page.selectOption(COUNTRY_SELECT, "DE");
        getLocator(STREET_INPUT_FIELD).fill(ConfigurationReader.get("street"));
        getLocator(POSTCODE_INPUT_FIELD).fill(ConfigurationReader.get("postcode"));
        getLocator(CITY_INPUT_FIELD).fill(ConfigurationReader.get("city"));
        getLocator(PHONE_NUMBER_INPUT_FIELD).fill(ConfigurationReader.get("phoneNumber"));
        return this;
    }

    @Step("Fill incorrect Postcode on checkout page")
    public CheckoutPage fillIncorrectPostcode() {
        getLocator(POSTCODE_INPUT_FIELD).fill(ConfigurationReader.get("incorrectPostcode"));
        return this;
    }

    @Step("Fill incorrect Phone number on checkout page")
    public CheckoutPage fillIncorrectPhoneNumber() {
        getLocator(PHONE_NUMBER_INPUT_FIELD).fill(ConfigurationReader.get("incorrectPhoneNumber"));
        return this;
    }

    @Step("Fill incorrect Name with digits on checkout page")
    public CheckoutPage fillIncorrectNameWithDigits() {
        getLocator(FIRST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("incorrectFirstNameDigits"));
        getLocator(LAST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("incorrectLastNameDigits"));
        return this;
    }

    @Step("Fill incorrect Name with special characters on checkout page")
    public CheckoutPage fillIncorrectNameWithSpecialCharacters() {
        getLocator(FIRST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("incorrectFirstNameSpecialChars"));
        getLocator(LAST_NAME_INPUT_FIELD).fill(ConfigurationReader.get("incorrectLastNameSpecialChars"));
        return this;
    }

    @Step("Confirm order with missing {fieldSelector} and validate error alert")
    public void confirmOrderAndValidateAlerts(String fieldSelector, String expectedAlertText) {
        getLocator(fieldSelector).clear();
        confirmOrder();
        waitForVisibility(REQUIRED_FIELDS_ERROR_ALERT);
        String actualAlertText = getText(REQUIRED_FIELDS_ERROR_ALERT);
        assertEquals(expectedAlertText, actualAlertText,
                "Expected alert text: '" + expectedAlertText + "', but got: '" + actualAlertText + "'");
    }

    @Step("Change country and verify delivery price update")
    public void changeCountryAndVerifyDeliveryPriceChange() {
        String countryName = "NL";
        String initialDeliveryPrice = getText(SHIPPING_COST_IN_CHECKOUT_PAGE).replaceAll("[^0-9.,]", "").trim();
        context.page.selectOption(COUNTRY_SELECT, countryName);
        context.page.waitForTimeout(2000);
        String updatedDeliveryPrice = getText(SHIPPING_COST_IN_CHECKOUT_PAGE).replaceAll("[^0-9.,]", "").trim();
        assertNotEquals(initialDeliveryPrice, updatedDeliveryPrice, "Delivery price did not update after changing country to " + countryName);
    }

    @Step("Select available payment methods and verify messages")
    public void selectAvailablePaymentMethodsAndVerifyMessages() {
        click(DIRECT_BANK_TRANSFER_METHOD_RADIO);
        waitForVisibility(DIRECT_BANK_TRANSFER_MESSAGE);
        String actualDirectBankMessage = getText(DIRECT_BANK_TRANSFER_MESSAGE);

        click(PAYPAL_METHOD_RADIO);
        waitForVisibility(PAYPAL_METHOD_MESSAGE);
        String actualPayPalMessage = getText(PAYPAL_METHOD_MESSAGE);

        assertEquals("Überweise direkt an unsere Bankverbindung. " +
                        "Bitte nutze die Bestellnummer als Verwendungszweck. " +
                        "Deine Bestellung wird erst nach Geldeingang auf unserem Konto versandt.",
                actualDirectBankMessage, "Direct Bank Transfer method message is incorrect");

        assertEquals("Mit Paypal bezahlen. Solltest du keinen Paypal-Account besitzen, " +
                        "kannst du auch mit deiner Kreditkarte bezahlen.",
                actualPayPalMessage, "PayPal method message is incorrect");
    }

    @Step("Verify final total amount on checkout page")
    public void verifyFinalTotalAmountOnCheckoutPage() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        context.page.waitForTimeout(2000);
        String expectedTotalAmount = shoppingBagPriceList.stream()
                .map(price -> price.replace(",", "."))
                .mapToDouble(Double::parseDouble)
                .sum() + "";
        String actualShippingCost = getText(SHIPPING_COST_IN_CHECKOUT_PAGE)
                .replaceAll("[^0-9,.]", "")
                .replace(",", ".").trim();
        expectedTotalAmount = new DecimalFormat("0.00").format(Double.parseDouble(expectedTotalAmount)
                + Double.parseDouble(actualShippingCost));

        String actualTotalAmount = getText(TOTAL_AMOUNT_ON_CHECKOUT_PAGE, 0)
                .replaceAll("[^0-9,]", "")
                .replace(",", ".").trim();

        assertEquals(expectedTotalAmount, actualTotalAmount,
                "Expected total amount: '" + expectedTotalAmount + "', but got: '" + actualTotalAmount + "'");
    }

    @Step("Choose Direct Bank Transfer method, check legal checkbox")
    public CheckoutPage chooseDirectBankTransferMethodAndCheckLegalCheckbox() {
        click(DIRECT_BANK_TRANSFER_METHOD_RADIO);
        getLocator(CONFIRM_LEGAL_CHECKBOX).check();
        return this;
    }

    @Step("Choose Paypal method, check legal checkbox")
    public CheckoutPage choosePayPalMethodAndCheckLegalCheckbox() {
        click(PAYPAL_METHOD_RADIO);
        getLocator(CONFIRM_LEGAL_CHECKBOX).check();
        return this;
    }

    @Step("Get total amount from checkout page")
    public CheckoutPage getTotalAmountFromCheckoutPage() {
        totalAmount = getText(TOTAL_AMOUNT_ON_CHECKOUT_PAGE, 0).replaceAll("[^0-9,]", "").trim();
        return this;
    }

    @Step("Confirm order")
    public CheckoutPage confirmOrder() {
        click(PLACE_ORDER_BUTTON);
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        return this;
    }

    @Step("Confirm order and go to Order Confirmation Page")
    public OrderConfirmationPage confirmOrderAndGoToOrderConfirmationPage() {
        click(PLACE_ORDER_BUTTON);
        verifyOrderReceivedPageIsOpened();
        return new OrderConfirmationPage(context, totalAmount);
    }

    @Step("Verify order received page is opened")
    public StartPage verifyOrderReceivedPageIsOpened() {
        waitForVisibility(SUCCESSFUL_ORDER_CREATION_MESSAGE);
        assertTrue(getLocator(SUCCESSFUL_ORDER_CREATION_MESSAGE).isVisible() && getLocator(ORDER_CONFIRMATION_PAGE_TITLE).isVisible(),
                "Order confirmation page is not opened or success message is not visible");
        return new StartPage(context);
    }

    @Step("Verify error message without legal checkbox checked")
    public void verifyErrorMessageWithoutLegalCheckboxChecked() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        waitForVisibility(REQUIRED_FIELDS_ERROR_ALERT_CONTAINER);
        String actualAlertText = getText(REQUIRED_CHECKBOX_LEGAL_ALERT);
        assertEquals("Bitte akzeptiere unsere Allgemeinen Geschäftsbedingungen und Widerrufsbestimmungen.", actualAlertText,
                "Actual alert text: '" + actualAlertText + "', does not match expected alert text for missing legal checkbox confirmation");
    }

    @Step("Verify error message for incorrect Postcode")
    public void verifyIncorrectPostcodeErrorMessage() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        assertFalse(getLocator(SUCCESSFUL_ORDER_CREATION_MESSAGE).isVisible(),
                "Order created and confirmation message is visible despite incorrect postcode input");
        waitForVisibility(REQUIRED_FIELD_POSTCODE_ALERT);
        String actualAlertText = getText(REQUIRED_FIELD_POSTCODE_ALERT);
        assertEquals("Rechnung: Postleitzahl ist keine gültige Postleitzahl / PLZ.", actualAlertText,
                "Actual alert text: '" + actualAlertText + "', does not match expected alert text for incorrect postcode");
    }

    @Step("Verify error message for incorrect Phone number")
    public void verifyPhoneNumberErrorMessage() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        assertFalse(getLocator(SUCCESSFUL_ORDER_CREATION_MESSAGE).isVisible(),
                "Order created and confirmation message is visible despite incorrect phone number input");
        waitForVisibility(REQUIRED_FIELD_PHONE_ALERT);
        String actualAlertText = getText(REQUIRED_FIELD_PHONE_ALERT);

        assertEquals("Rechnung: Telefon ist keine gültige Telefonnummer.", actualAlertText,
                "Actual alert text: '" + actualAlertText + "', does not match expected alert text for incorrect phone number");

    }

    @Step("Verify error message for incorrect Name")
    public void verifyIncorrectNameErrorMessage() {
        context.page.waitForLoadState(LoadState.NETWORKIDLE);
        assertFalse(getLocator(SUCCESSFUL_ORDER_CREATION_MESSAGE).isVisible(),
                "Order created and confirmation message is visible despite incorrect first name input");
        waitForVisibility(REQUIRED_FIELD_FIRST_NAME_ALERT);
        String actualAlertText = getText(REQUIRED_FIELD_FIRST_NAME_ALERT);
        assertEquals("Rechnung: Vorname enthält ungültige Zeichen.", actualAlertText,
                "Actual alert text: '" + actualAlertText + "', does not match expected alert text for incorrect first name");

        assertFalse(getLocator(SUCCESSFUL_ORDER_CREATION_MESSAGE).isVisible(),
                "Order created and confirmation message is visible despite incorrect last name input");
        waitForVisibility(REQUIRED_FIELD_LAST_NAME_ALERT);
        String actualLastNameAlertText = getText(REQUIRED_FIELD_LAST_NAME_ALERT);
        assertEquals("Rechnung: Nachname enthält ungültige Zeichen.", actualLastNameAlertText,
                "Actual alert text: '" + actualLastNameAlertText + "', does not match expected alert text for incorrect last name");
    }

    @Step("Confirm order and go to PayPal page")
    public PayPalPage confirmOrderAndGoToPayPalPage() {
        click(PLACE_ORDER_BUTTON);
        return new PayPalPage(context);
    }
}
