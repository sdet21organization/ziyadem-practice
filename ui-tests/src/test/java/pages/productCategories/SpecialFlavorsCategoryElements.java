package pages.productCategories;

public class SpecialFlavorsCategoryElements {
    public static final String SPECIAL_FLAVORS_CATEGORY_TITLE = "//nav[contains(@class,'woocommerce-breadcrumb')]";
    public static final String PRODUCT_ITEM_BY_NAME = "//div[@class='col-inner']//font[@dir='auto']//font[normalize-space(.)='%s']";
    public static final String AVAILABLE_ITEM = "//div[contains(@class, 'product-small col') and not(.//*[contains(text(), 'Nicht vorrätig')])]";
    public static final String PRODUCT_TITLE = "//h1[@class='product-title product_title entry-title']";
    public static final String PRODUCT_PRICE = "//div[@class='product-main']//div[@class='price-wrapper']//span[@class='woocommerce-Price-amount amount']";
    public static final String PRODUCT_PRICE_DISCOUNTED = "div.product-info.summary.col-fit.col.entry-summary.product-summary.text-center.form-flat div.price-wrapper ins .woocommerce-Price-amount";
    public static final String QUANTITY_COUNTER_INPUT = "//input[@type='number']";
    public static final String ADD_TO_CART_BUTTON = "//button[@name='add-to-cart']";
    public static final String SUCCESS_ADDITION_ALERT = "//div[@class='message-container container success-color medium-text-center']";
}
