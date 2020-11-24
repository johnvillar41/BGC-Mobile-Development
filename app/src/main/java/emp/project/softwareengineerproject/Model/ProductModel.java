package emp.project.softwareengineerproject.Model;

import com.mysql.jdbc.Blob;

public class ProductModel {
    String product_id,product_name,product_description;
    long product_price;
    Blob product_picture;
    int product_stocks;

    public ProductModel(String product_id, String product_name, String product_description,
                        long product_price, Blob product_picture, int product_stocks) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_picture = product_picture;
        this.product_stocks = product_stocks;
    }

    public ProductModel() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public long getProduct_price() {
        return product_price;
    }

    public Blob getProduct_picture() {
        return product_picture;
    }

    public int getProduct_stocks() {
        return product_stocks;
    }
}
