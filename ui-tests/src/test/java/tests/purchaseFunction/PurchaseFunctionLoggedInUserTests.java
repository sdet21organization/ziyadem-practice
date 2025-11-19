package tests.purchaseFunction;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
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
                .confirmOrderAndVerifyOrderReceivedPageIsOpened();
    }
}
