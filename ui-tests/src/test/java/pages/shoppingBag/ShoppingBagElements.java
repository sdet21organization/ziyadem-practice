package pages.shoppingBag;

public class ShoppingBagElements {
    public static final String SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']";
    public static final String ROW_IN_SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']/tbody/tr";
    public static final String ITEM_NAME_IN_SHOPPING_BAG_TABLE = "//td[@class='product-name']//a";
    public static final String ITEM_COUNT_IN_SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']//input[@type='number']";
    public static final String ITEM_PRICE_IN_SHOPPING_BAG_TABLE = "//td[@class='product-subtotal']//span[@class='woocommerce-Price-amount amount']";
    public static final String AMOUNT_IN_SHOPPING_BAG = "tr.cart-subtotal td .woocommerce-Price-amount.amount";
}
