package tests.purchaseFunction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.productCategories.SpecialFlavorsCategoryPage;
import pages.startPageHeader.StartPage;
import tests.BaseTest;

import java.util.stream.Stream;

import static pages.purchaseFunction.CheckoutPageElements.*;

@Epic("UI Tests")
@Feature("Purchase Function")
@Story("Logged Out User Order Placement")
@DisplayName("Purchase Function Tests")
@Owner("Yuliia Nazarenko")
public class PurchaseFunctionLoggedOutUserTests extends BaseTest {

    @Test
    @DisplayName("Proceed to checkout from shopping bag page")
    public void proceedToCheckoutFromShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .verifyCheckoutPageIsOpened();
    }

    @Test
    @DisplayName("Proceed to checkout through the quick view of the shopping cart")
    public void proceedToCheckoutWithMiniCartPopup() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .hoverToShoppingBagAndVerifyMiniCartPopup()
                .proceedToCheckoutFromMiniCartPopup()
                .verifyCheckoutPageIsOpened();
    }

    @Test
    @DisplayName("Cannot proceed with empty cart")
    public void proceedWithEmptyCart() {
        new StartPage(context)
                .openStartPage()
                .openShoppingBagPage()
                .verifyCannotProceedToCheckoutWithEmptyCart();
    }

    @Test
    @DisplayName("Required fields validation - validate all empty required fields")
    public void validateAllEmptyRequiredFields() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .confirmOrder()
                .validateAllEmptyFields();
    }

    static Stream<Object[]> requiredFields() {
        return Stream.of(
                new Object[]{EMAIL_INPUT_FIELD, "Rechnung: E-Mail-Adresse ist ein Pflichtfeld."},
                new Object[]{FIRST_NAME_INPUT_FIELD, "Rechnung: Vorname ist ein Pflichtfeld."},
                new Object[]{LAST_NAME_INPUT_FIELD, "Rechnung: Nachname ist ein Pflichtfeld."},
                new Object[]{STREET_INPUT_FIELD, "Rechnung: Straße ist ein Pflichtfeld."},
                new Object[]{POSTCODE_INPUT_FIELD, "Rechnung: Postleitzahl ist ein Pflichtfeld."},
                new Object[]{CITY_INPUT_FIELD, "Rechnung: Ort / Stadt ist ein Pflichtfeld."},
                new Object[]{PHONE_NUMBER_INPUT_FIELD, "Rechnung: Telefon ist ein Pflichtfeld."}
        );
    }

    @ParameterizedTest
    @MethodSource("requiredFields")
    @DisplayName("Validate required field and its alert message")
    public void validateEachRequiredField(String fieldSelector, String expectedAlertText) {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .confirmOrderAndValidateAlerts(fieldSelector, expectedAlertText);
    }

    @Test
    @DisplayName("Delivery price changes with country changing")
    public void verifyDeliveryPriceChanged() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .changeCountryAndVerifyDeliveryPriceChange();
    }

    @Test
    @DisplayName("User can complete the purchase even if he/she doesn't login")
    public void createOrderWithValidData() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyOrderReceivedPageIsOpened();
    }

    @Test
    @DisplayName("Verify Order Confirmation Page contains Bank Details for Direct Bank Transfer")
    public void verifyBankDetailsOnConfirmationPage() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrderAndGoToOrderConfirmationPage()
                .verifyBankDetailsAreDisplayedOnOrderConfirmationPage();
    }

    @Test
    @DisplayName("Verify order information on Order Confirmation Page")
    public void verifyOrderInformation() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .getTotalAmountFromCheckoutPage()
                .confirmOrderAndGoToOrderConfirmationPage()
                .verifyOrderInformationIsCorrect();
    }

    @Test
    @DisplayName("Verify order details on Order Confirmation Page")
    public void verifyOrderDetails() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .getTotalAmountFromCheckoutPage()
                .confirmOrderAndGoToOrderConfirmationPage()
                .verifyOrderDetailsOnConfirmationPage();
    }
}
