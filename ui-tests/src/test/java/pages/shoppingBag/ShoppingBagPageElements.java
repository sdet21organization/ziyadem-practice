package pages.shoppingBag;

public class ShoppingBagPageElements {
    public static final String SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']";
    public static final String ROW_IN_SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']/tbody/tr";
    public static final String REMOVE_ITEM_FROM_SHOPPING_BAG_BUTTON = "//td[@class='product-remove']//a";
    public static final String RETURN_ITEM_TO_SHOPPING_BAG_BUTTON = "//a[contains(., 'Rückgängig?')]";
    public static final String ITEM_NAME_IN_SHOPPING_BAG_TABLE = "//td[@class='product-name']//a";
    public static final String ITEM_COUNT_IN_SHOPPING_BAG_TABLE = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']//input[@type='number']";
    public static final String COUNTER_PLUS_BUTTON = "//input[@value='+']";
    public static final String COUNTER_MINUS_BUTTON = "//input[@value='-']";
    public static final String ITEM_PRICE_IN_SHOPPING_BAG_TABLE = "//td[@class='product-subtotal']//span[@class='woocommerce-Price-amount amount']";
    public static final String AMOUNT_IN_SHOPPING_BAG = "tr.cart-subtotal td .woocommerce-Price-amount.amount";
    public static final String SHIPPING_COST_IN_SHOPPING_BAG = "//tr[contains(@class,'woocommerce-shipping-totals')]//td//span[@class='woocommerce-Price-amount amount']";
    public static final String TOTAL_AMOUNT_IN_SHOPPING_BAG = "//tr[@class='order-total']//span[@class='woocommerce-Price-amount amount'][1]";
    public static final String SHOPPING_BAG_UPDATE_BUTTON = "//button[@value='Warenkorb aktualisieren']";
    public static final String SUCCESS_DELETION_MESSAGE = "//div[@class = 'woocommerce-message message-wrapper' and contains(., 'entfernt.')]";
    public static final String SHOPPING_BAG_IS_EMPTY_MESSAGE = "//div[@class = 'woocommerce-info message-wrapper' and contains(., 'Dein Warenkorb ist gegenwärtig leer.')]";
    public static final String RETURN_TO_SHOP_BUTTON = "//p[@class='return-to-shop']";
    public static final String PROCEED_TO_CHECKOUT_BUTTON = "//div[@class='wc-proceed-to-checkout']";
}
