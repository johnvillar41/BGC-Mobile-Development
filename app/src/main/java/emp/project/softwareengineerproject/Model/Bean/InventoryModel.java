package emp.project.softwareengineerproject.Model.Bean;

import java.io.Serializable;
import java.sql.Blob;

public class InventoryModel implements Serializable {
    private int productID;
    private String productName;
    private String productDescription;
    private int productPrice;
    private Blob productPicture;
    private int productStocks;
    private String productCategory;

    public InventoryModel(int productID, String productName, String productDescription, int productPrice, Blob productPicture, int productStocks, String productCategory) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productPicture = productPicture;
        this.productStocks = productStocks;
        this.productCategory = productCategory;
    }

    public InventoryModel() {
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public Blob getProductPicture() {
        return productPicture;
    }

    public int getProductStocks() {
        return productStocks;
    }

    public String getProductCategory() {
        return productCategory;
    }
}
