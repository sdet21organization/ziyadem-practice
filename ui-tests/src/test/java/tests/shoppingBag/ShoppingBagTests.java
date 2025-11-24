package tests.shoppingBag;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.productCategories.SpecialFlavorsCategoryPage;
import pages.startPageHeader.StartPage;
import pages.wishlist.AuthSteps;
import tests.BaseTest;

import java.util.List;
import java.util.Map;

@Epic("UI Tests")
@Feature("Shopping Bag")
@Story("Shopping Bag Management by Users")
@DisplayName("Shopping Bag Tests")
@Owner("Yuliia Nazarenko")
public class ShoppingBagTests extends BaseTest {

    @Test
    @DisplayName("Add item to shopping bag")
    public void addItemToShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .verifyProductAddedToShoppingBag();
    }

    @Test
    @DisplayName("Shopping cart icon shows correct item count")
    public void addItemToShoppingBagAndVerifyItemsCount() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .verifyItemsCount()
                .addOneMoreItemAndVerifyQuantity();
    }

    @Test
    @DisplayName("Adding same product increases quantity")
    public void addItemAndVerifyQuantityInShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .verifyItemsCount()
                .addOneMoreItemAndOpenShoppingCart()
                .verifyIncreasedQuantityInShoppingBag();
    }

    @Test
    @DisplayName("Shopping cart display")
    public void verifyInShoppingBagDisplay() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .addSomeProductsToShoppingBag()
                .verifyAllProductsAddedToShoppingBag()
                .verifyAmountInShoppingBag();
    }

    @Test
    @DisplayName("Edit shopping cart")
    public void editToShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .editShoppingBagAndVerifyQuantityAndTotalPrice();
    }

    @Test
    @DisplayName("Remove product from cart")
    public void removeItemFromShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .removeItemFromShoppingBag()
                .verifyShoppingBagIsEmpty();
    }

    @Test
    @DisplayName("Empty cart message")
    public void verifyEmptyCartMessage() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .removeItemAndVerifyShoppingBagMessage();
    }

    @Test
    @DisplayName("Shopping cart persists after page reload")
    public void verifyShoppingBagAfterReload() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .verifyShoppingBagAfterReload();
    }

    @Test
    @DisplayName("Shopping cart persists after logout/login (logged-in users)")
    public void verifyShoppingBagAfterReLogin() {
        new AuthSteps(context)
                .login();
        Map<String, List<String>> expectedData =
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .getItemsListInShoppingBag();
        new AuthSteps(context)
                .logout();
        new StartPage(context)
                .openShoppingBagPage()
                .verifyShoppingBagIsEmpty();
        new AuthSteps(context)
                .login();
        new StartPage(context)
                .openShoppingBagPage()
                .verifyItemsListInShoppingBag(expectedData);
    }

    @Test
    @DisplayName("New user sees empty cart")
    public void verifyNewUserSeesEmptyCart() {
        new StartPage(context)
                .openStartPage()
                .openShoppingBagPage()
                .verifyShoppingBagIsEmpty();
    }

    @Test
    @DisplayName("Price check")
    public void verifyPricesInProductAndInShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .addSomeProductsToShoppingBagAndGetPrices()
                .verifyProductPricesInShoppingBag();
    }

    @Test
    @DisplayName("Remove product from cart with counter")
    public void removeProductFromShoppingBagWithCounter() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .removeProductWithCounter()
                .verifyShoppingBagIsEmpty();
    }

    @Test
    @DisplayName("Hovering over the cart icon, a quick view of the cart contents is displayed")
    public void hoverToShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .hoverToShoppingBagAndVerifyMiniCartPopup();
    }

    @Test
    @DisplayName("Correct total calculation")
    public void verifyTotalCalculationInShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .addSomeProductsToShoppingBag()
                .verifyTotalAmountInShoppingBag();
    }

    @Test
    @DisplayName("Return item to cart after removing")
    public void returnItemToShoppingBag() {
        new SpecialFlavorsCategoryPage(context)
                .openSpecialFlavorsCategoryPage()
                .chooseProduct()
                .addProductToShoppingBag()
                .goToShoppingBagPage()
                .getItemsInShoppingBag()
                .removeItemFromShoppingBag()
                .verifyShoppingBagIsEmpty()
                .returnItemToShoppingBag()
                .verifyItemsInShoppingBagAfterReturn();
    }

    @Test
    @DisplayName("Shopping cart icon is clickable")
    public void verifyShoppingCartIconClickable() {
        new StartPage(context)
                .openStartPage()
                .clickShoppingBagIconAndVerify();
    }
}
