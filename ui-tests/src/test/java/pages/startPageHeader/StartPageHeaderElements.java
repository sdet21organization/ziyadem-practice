package pages.startPageHeader;

public class StartPageHeaderElements {
    public static final String SHOPPING_BAG_ICON = "//a[@title='Warenkorb']";
    public static final String PRODUCT_QUANTITY_IN_SHOPPING_BAG_ICON = "//i[@data-icon-label='%s']";
    public static final String MINI_CART_POPUP_HOVER = "//li[@class='cart-item has-icon has-dropdown current-dropdown']//ul[@class='nav-dropdown nav-dropdown-default']";
    public static final String MINI_CART_POPUP = "//li[@class='cart-item has-icon has-dropdown current-dropdown cart-active']// ul[@class='nav-dropdown nav-dropdown-default']";
    public static final String PRODUCT_NAME_IN_MINI_CART_POPUP = "//ul[@class='nav-dropdown nav-dropdown-default']//a[contains(@href, '/produkt/')]/font[last()]";
    public static final String KASSE_BUTTON_IN_MINI_CART_POPUP = "//ul[@class='nav-dropdown nav-dropdown-default']//a[contains(@href, '/checkout')]";
}
