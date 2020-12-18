package emp.project.softwareengineerproject.Model;

public class OrdersModel {

    private String order_id, customer_name, customer_email,
            order_total_price, order_status, order_date;

    public OrdersModel(String order_id, String customer_name, String customer_email, String order_total_price, String order_status, String order_date) {
        this.order_id = order_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.order_total_price = order_total_price;
        this.order_status = order_status;
        this.order_date = order_date;
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
