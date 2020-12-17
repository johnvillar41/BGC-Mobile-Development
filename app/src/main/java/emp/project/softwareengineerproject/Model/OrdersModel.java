package emp.project.softwareengineerproject.Model;

public class OrdersModel {

    private String order_id, order_date_year, order_date_day,
            order_date_month, customer_name, customer_email,
            order_total_price, order_status;

    public OrdersModel(String order_id, String order_date_year, String order_date_day, String order_date_month, String customer_name, String customer_email, String order_total_price, String order_status) {
        this.order_id = order_id;
        this.order_date_year = order_date_year;
        this.order_date_day = order_date_day;
        this.order_date_month = order_date_month;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.order_total_price = order_total_price;
        this.order_status = order_status;
    }

    public OrdersModel() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_date_year() {
        return order_date_year;
    }

    public String getOrder_date_day() {
        return order_date_day;
    }

    public String getOrder_date_month() {
        return order_date_month;
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
