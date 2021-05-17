package emp.project.softwareengineerproject.Model.Bean;

import java.util.Date;

public class OrdersModel {
    private int orderID;
    private int userID;
    private int orderTotalPrice;
    private String orderStatus;
    private Date orderDate;
    private int totalNumberOfOrders;

    public OrdersModel(int orderID, int userID, int orderTotalPrice, String orderStatus, Date orderDate, int totalNumberOfOrders) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.totalNumberOfOrders = totalNumberOfOrders;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public int getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getTotalNumberOfOrders() {
        return totalNumberOfOrders;
    }
}
