package pages.purchaseFunction;

public class OrderConfirmationPageElements {
    public static final String ORDER_CONFIRMATION_PAGE_TITLE = "//font[contains(text(), 'Bestellung abgeschlossen ')]";
    public static final String SUCCESSFUL_ORDER_CREATION_MESSAGE = "//font[contains(text(), 'Vielen Dank. Deine Bestellung ist eingegangen.')]";

    // Bank Details Section
    public static final String BANK_DETAILS_SECTION = "//font[text()='Unsere Bankverbindung']";
    public static final String BANK_NAME_DETAILS = "//li[@class='bank_name']";
    public static final String ACCOUNT_NUMBER_DETAILS = "//li[@class='account_number']";
    public static final String SORT_CODE_DETAILS = "//li[@class='sort_code']";
    public static final String IBAN_DETAILS = "//li[@class='iban']";
    public static final String BIC_DETAILS = "//li[@class='bic']";

    // Order Information Section
    public static final String ORDER_INFORMATION_SECTION = "//ul[@class='woocommerce-order-overview woocommerce-thankyou-order-details order_details']";
    public static final String ORDER_NUMBER = "li.woocommerce-order-overview__order strong";
    public static final String DATE = "li.woocommerce-order-overview__date strong";
    public static final String EMAIL = "//li[@class='woocommerce-order-overview__email email']";
    public static final String TOTAL = "li.woocommerce-order-overview__total strong";
    public static final String PAYMENT_METHOD = "li.woocommerce-order-overview__payment-method strong";

    //Order Details
    public static final String ORDER_DETAILS_SECTION = "//h2//font[contains(text(), 'Bestelldetails')]";
    public static final String PRODUCT_NAME_IN_ORDER_DETAILS = "tr.woocommerce-table__line-item .product-name a";
    public static final String TOTAL_AMOUNT_IN_ORDER_DETAILS = "//tr[th[contains(., 'Gesamt')]]/td//span[contains(@class, 'amount')]";
}