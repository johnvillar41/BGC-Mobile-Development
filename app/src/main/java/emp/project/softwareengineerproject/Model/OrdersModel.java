package emp.project.softwareengineerproject.Model;

import java.sql.Blob;

public class OrdersModel {

    private String order_id, customer_name, customer_email,
            order_total_price, order_status, order_date;

    private String product_id, total_number_of_orders, product_name;
    private Blob product_picture;

    /**
     * This Constructor is for the list of customer orders
     *
     * @param order_id
     * @param customer_name
     * @param customer_email
     * @param order_total_price
     * @param order_status
     * @param order_date
     */
    public OrdersModel(String order_id, String customer_name, String customer_email, String order_total_price, String order_status, String order_date) {
        this.order_id = order_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.order_total_price = order_total_price;
        this.order_status = order_status;
        this.order_date = order_date;
    }

    /**
     * This Constructor is for the specific orders of the customer
     */
    public OrdersModel(String order_id, String product_id, String total_number_of_orders, Blob product_picture, String product_name) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.total_number_of_orders = total_number_of_orders;
        this.product_picture = product_picture;
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getTotal_number_of_orders() {
        return total_number_of_orders;
    }

    public String getProduct_name() {
        return product_name;
    }

    public Blob getProduct_picture() {
        return product_picture;
    }

    public String getOrder_date() {
        return order_date;
    }

    public OrdersModel() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public String getOrder_status() {
        return order_status;
    }
}
