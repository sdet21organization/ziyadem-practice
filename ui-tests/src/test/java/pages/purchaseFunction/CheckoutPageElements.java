package pages.purchaseFunction;

public class CheckoutPageElements {
    public static final String CHECKOUT_PAGE_TITLE = "//font[contains(text(), 'Bestellübersicht')]";
    public static final String CHECKOUT_DETAILS_HEADER = "//font[contains(text(), 'Rechnungsdetails')]";
    public static final String EMAIL_INPUT_FIELD = "//input[@name='billing_email']";
    public static final String FIRST_NAME_INPUT_FIELD = "//input[@name='billing_first_name']";
    public static final String LAST_NAME_INPUT_FIELD = "//input[@name='billing_last_name']";
    public static final String COUNTRY_SELECT = "//select[@name='billing_country']";
    public static final String STREET_INPUT_FIELD = "//input[@name='billing_address_1']";
    public static final String APARTMENT_NUMBER_INPUT_FIELD = "//input[@name='billing_address_2']";
    public static final String POSTCODE_INPUT_FIELD = "//input[@name='billing_postcode']";
    public static final String CITY_INPUT_FIELD = "//input[@name='billing_city']";
    public static final String PHONE_NUMBER_INPUT_FIELD = "//input[@name='billing_phone']";
    public static final String CREATE_ACCOUNT_CHECKBOX = "//input[@name='createaccount']";
    public static final String SHIP_TO_DIFFERENT_ADDRESS_CHECKBOX = "//input[@name='ship_to_different_address']";
    public static final String COMMENTS_TEXTAREA = "//textarea[@name='order_comments']";
    public static final String PLACE_ORDER_BUTTON = "//button[@id='place_order']";
    public static final String REQUIRED_FIELDS_ERROR_ALERT = "//div[@class='woocommerce-NoticeGroup woocommerce-NoticeGroup-checkout']";
    public static final String REQUIRED_FIELD_EMAIL_ALERT = "//li[@data-id='billing_email']";
    public static final String REQUIRED_FIELD_FIRST_NAME_ALERT = "//li[@data-id='billing_first_name']";
    public static final String REQUIRED_FIELD_LAST_NAME_ALERT = "//li[@data-id='billing_last_name']";
    public static final String REQUIRED_FIELD_STREET_ALERT = "//li[@data-id='billing_address_1']";
    public static final String REQUIRED_FIELD_POSTCODE_ALERT = "//li[@data-id='billing_postcode']";
    public static final String REQUIRED_FIELD_CITY_ALERT = "//li[@data-id='billing_city']";
    public static final String REQUIRED_FIELD_PHONE_ALERT = "//li[@data-id='billing_phone']";
    public static final String SHIPPING_COST_IN_CHECKOUT_PAGE = "//tr[contains(@class,'woocommerce-shipping-totals')]//td//span[@class='woocommerce-Price-amount amount']";
    public static final String DIRECT_BANK_TRANSFER_METHOD_RADIO = "//input[@id='payment_method_bacs']";
    public static final String DIRECT_BANK_TRANSFER_MESSAGE = "//p[contains(text(), 'Bankverbindung')]";
    public static final String PAYPAL_METHOD_RADIO = "//input[@id='payment_method_paypal']";
    public static final String PAYPAL_METHOD_MESSAGE = "//p[contains(text(), 'Paypal')]";
    public static final String TOTAL_AMOUNT_ON_CHECKOUT_PAGE = "//tr[@class='order-total']//span[@class='woocommerce-Price-amount amount'][1]";
    public static final String CONFIRM_LEGAL_CHECKBOX = "//input[@id='legal']";
}