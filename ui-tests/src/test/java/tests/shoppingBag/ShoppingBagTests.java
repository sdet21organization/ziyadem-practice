package tests.shoppingBag;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.productCategories.SpecialFlavorsCategoryPage;
import tests.BaseTest;

@Epic("UI Tests")
@Feature("Shopping Bag")
@DisplayName("Shopping Bag tests")
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
}
