package pages.accountDetails;

public class OrdersPageElements {
    public static final String ORDERS_TABLE = "//table[@class='woocommerce-orders-table woocommerce-MyAccount-orders shop_table shop_table_responsive my_account_orders account-orders-table']";
    public static final String ORDER_NUMBER_IN_TABLE = "//tbody//tr//th//a[contains(@aria-label, '%s')]";
    public static final String ORDER_AMOUNT_IN_TABLE = "//tbody//tr[th/a[contains(@aria-label, '%s')]]/td[3]/span";
}