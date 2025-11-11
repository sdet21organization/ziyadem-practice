package pages.startPageHeader;

public class StartPageHeaderElements {
    public static final String SHOPPING_BAG_ICON = "//a[@title='Warenkorb']";
    public static final String PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON = "//i[@data-icon-label='%s']";
    public static final String MINI_CART_POPUP = "//ul[@class='nav-dropdown nav-dropdown-default']";
    public static final String PRODUCT_NAME_IN_MINI_CART_POPUP = "//ul[@class='nav-dropdown nav-dropdown-default']//a[contains(@href, '/produkt/')]/font[last()]";

    public static final String SHOPPING_CART_WINDOW_TITLE = "//div[@class='cart-popup-title text-center']//font[text()='Warenkorb']";
    public static final String SHOPPING_CART_WINDOW_PRODUCT_NAME = "//div[@id='cart-popup']//ul[contains(@class, 'cart_list')]//a[contains(@href, '/produkt/')]/font[last()]";
}
