package emp.project.softwareengineerproject.Model.Bean;

public class InformationModel {
    private String product_information_tutorial;
    private InventoryModel inventoryModel;

    public InformationModel(String product_information_tutorial, InventoryModel productModel) {
        this.product_information_tutorial = product_information_tutorial;
        this.inventoryModel = productModel;
    }

    public InventoryModel getProductModel() {
        return inventoryModel;
    }

    public String getProduct_information_tutorial() {
        return product_information_tutorial;
    }
}