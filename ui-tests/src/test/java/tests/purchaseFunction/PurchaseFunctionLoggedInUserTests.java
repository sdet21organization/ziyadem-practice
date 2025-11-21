package tests.purchaseFunction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.productCategories.SpecialFlavorsCategoryPage;
import pages.wishlist.AuthSteps;
import tests.BaseTest;

@Epic("UI Tests")
@Feature("Purchase Function")
@Story("Logged In User Order Placement")
@DisplayName("Purchase Function Tests")
@Owner("Yuliia Nazarenko")
public class PurchaseFunctionLoggedInUserTests extends BaseTest {

    @BeforeEach
    void login() {
        new AuthSteps(context)
                .login();
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
    @DisplayName("Payment method selection")
    public void verifyPaymentMethods() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .selectAvailablePaymentMethodsAndVerifyMessages();
    }

    @Test
    @DisplayName("Final total displayed correctly on checkout page")
    public void verifyFinalTotalAmount() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .getProductAmountPriceInShoppingBag()
                .proceedToCheckout()
                .verifyFinalTotalAmountOnCheckoutPage();
    }

    @Test
    @DisplayName("Successful order creation with valid data")
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
    @DisplayName("Unsuccessful order creation with incorrect Postcode")
    public void createOrderWithIncorrectPostcode() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .fillIncorrectPostcode()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyIncorrectPostcodeErrorMessage();
    }

    @Test
    @DisplayName("Unsuccessful order creation with incorrect Phone number")
    public void createOrderWithIncorrectPhoneNumber() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .fillIncorrectPhoneNumber()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyPhoneNumberErrorMessage();
    }

    @Test
    @Disabled("Disabled until bug is fixed, link: https://ets-sdet.atlassian.net/browse/ZIYAD-59")
    @DisplayName("Unsuccessful order creation with incorrect Name with digits")
    public void createOrderWithIncorrectNameWithDigits() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .fillIncorrectNameWithDigits()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyIncorrectNameErrorMessage();
    }

    @Test
    @Disabled("Disabled until bug is fixed, link: https://ets-sdet.atlassian.net/browse/ZIYAD-59")
    @DisplayName("Unsuccessful order creation with incorrect Name with special characters")
    public void createOrderWithIncorrectNameWithSpecialCharacters() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .fillIncorrectNameWithSpecialCharacters()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyIncorrectNameErrorMessage();
    }

    @Test
    @DisplayName("Successful order creation with PayPal payment method")
    public void createOrderWithPayPalPaymentMethod() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .choosePayPalMethodAndCheckLegalCheckbox()
                .confirmOrderAndGoToPayPalPage()
                .verifyPayPalPageIsOpened();
    }

    @Test
    @DisplayName("Cart cleared after successful order placement")
    public void verifyShoppingBagClearedAfterOrderCreation() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .proceedToCheckout()
                .fillAllRequiredFields()
                .chooseDirectBankTransferMethodAndCheckLegalCheckbox()
                .confirmOrder()
                .verifyOrderReceivedPageIsOpened()
                .openShoppingBagPage()
                .verifyShoppingBagIsEmpty();
    }
}
