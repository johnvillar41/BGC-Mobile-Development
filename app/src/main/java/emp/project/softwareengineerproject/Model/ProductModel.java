package emp.project.softwareengineerproject.Model;

import com.mysql.jdbc.Blob;

import java.io.InputStream;
import java.io.Serializable;

public class ProductModel implements Serializable {
    String product_id, product_name, product_description;
    long product_price;
    Blob product_picture;
    int product_stocks;

    java.sql.Blob upload_picture;


    public ProductModel(String product_id, String product_name, String product_description,
                        long product_price, Blob product_picture, int product_stocks) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_picture = product_picture;
        this.product_stocks = product_stocks;
    }

    public ProductModel(String product_name, String product_description,
                        long product_price, Blob product_picture, int product_stocks) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_picture = product_picture;
        this.product_stocks = product_stocks;
    }

    public java.sql.Blob getUpload_picture() {
        return upload_picture;
    }

    public ProductModel(String product_id, String product_name, String product_description, long product_price, int product_stocks, java.sql.Blob upload_picture) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.upload_picture = upload_picture;
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

    public String validateProduct(ProductModel model) {
        String message = null;
        if (model.getProduct_name().isEmpty()) {
            message = "Product name field is empty!\n";
        }
        if (model.getProduct_description().isEmpty()) {
            message = "Product description is empty!\n";
        }
        if (String.valueOf(model.getProduct_price()).isEmpty()) {
            message = "Product price is empty!\n";
        }
        /*if (model.getUpload_picture().isEmpty()) {
            message = "Product Image is empty!\n";
        }*/
        if (String.valueOf(model.getProduct_stocks()).isEmpty()) {
            message = "Product stocks is empty!\n";
        }
        return message;
    }
}
