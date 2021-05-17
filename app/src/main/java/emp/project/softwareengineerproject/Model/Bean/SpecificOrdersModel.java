package emp.project.softwareengineerproject.Model.Bean;

public class SpecificOrdersModel {
    private int specificOrderID;
    private int orderID;
    private String administratorUsername;
    private InventoryModel productModel;
    private int totalOrders;
    private int subTotalPrice;

    public SpecificOrdersModel(int specificOrderID, int orderID, String administratorUsername, InventoryModel productModel, int totalOrders, int subTotalPrice) {
        this.specificOrderID = specificOrderID;
        this.orderID = orderID;
        this.administratorUsername = administratorUsername;
        this.productModel = productModel;
        this.totalOrders = totalOrders;
        this.subTotalPrice = subTotalPrice;
    }

    public int getSpecificOrderID() {
        return specificOrderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getAdministratorUsername() {
        return administratorUsername;
    }

    public InventoryModel getProductModel() {
        return productModel;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getSubTotalPrice() {
        return subTotalPrice;
    }
}
