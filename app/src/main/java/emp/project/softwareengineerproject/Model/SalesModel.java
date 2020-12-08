package emp.project.softwareengineerproject.Model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class SalesModel {
    private String sales_id, sales_title,product_id,total_number_of_products,sales_date;
    private Blob sales_image;
    private long product_total;


    public static List<InventoryModel> cartList = new ArrayList<>();


    public SalesModel(String sales_id, String sales_title,Blob sales_image,long product_total, String product_id, String total_number_of_products, String sales_date ) {
        this.sales_id = sales_id;
        this.sales_title = sales_title;
        this.product_id = product_id;
        this.total_number_of_products = total_number_of_products;
        this.sales_date = sales_date;
        this.sales_image = sales_image;
        this.product_total = product_total;
    }

    public SalesModel(Blob sales_image, String sales_title, long product_total, String product_id, String total_number_of_products, String sales_date) {
        this.sales_image = sales_image;
        this.sales_title = sales_title;
        this.product_id = product_id;
        this.product_total = product_total;
        this.total_number_of_products = total_number_of_products;
        this.sales_date = sales_date;
    }

    public String getTotal_number_of_products() {
        return total_number_of_products;
    }

    public long getProduct_total() {
        return product_total;
    }


    public Blob getSales_image() {
        return sales_image;
    }

    public SalesModel() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getSales_id() {
        return sales_id;
    }

    public String getSales_title() {
        return sales_title;
    }

    public String getSales_date() {
        return sales_date;
    }
}
