package pages.productCategories;

public class SpecialFlavorsCategoryElements {

    public static final String SPECIAL_FLAVORS_CATEGORY_TITLE =
            "nav.woocommerce-breadcrumb:has-text('Special Flavors'), " +
                    ".woocommerce-products-header__title.page-title:has-text('Special Flavors'), " +
                    "h1.entry-title:has-text('Special Flavors')";

    public static final String PRODUCT_ITEM_BY_NAME =
            "ul.products li.product:has(h2:has-text('%s'))";

    public static final String AVAILABLE_ITEM =
            "ul.products li.product:not(:has(:text('Nicht vorrätig')))";

    public static final String PRODUCT_TITLE =
            "h1.product_title, h1.product-title, .product_title";

    public static final String PRODUCT_PRICE =
            ".summary .price .woocommerce-Price-amount, " +
                    ".product-main .price .woocommerce-Price-amount";

    public static final String PRODUCT_PRICE_DISCOUNTED =
            "ins .woocommerce-Price-amount";

    public static final String QUANTITY_COUNTER_INPUT =
            "input.qty, input[name='quantity'][type='number']";

    public static final String COUNTER_PLUS_BUTTON = "button.plus";
    public static final String COUNTER_MINUS_BUTTON = "button.minus";

    public static final String ADD_TO_CART_BUTTON = "button[name='add-to-cart']";

    public static final String SUCCESS_ADDITION_ALERT =
            "div.woocommerce-message:has-text('Warenkorb hinzugefügt')";

    public static final String MINI_CART_POPUP =
            "div.widget_shopping_cart_content";

    public static final String PRODUCT_NAME_IN_MINI_CART_POPUP =
            "li.mini_cart_item a";
}