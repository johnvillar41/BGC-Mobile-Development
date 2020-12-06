package emp.project.softwareengineerproject.Model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class SalesModel {
    private String sales_id;
    private Blob sales_image;
    private String sales_title;
    private String sales_transaction;
    public static List<InventoryModel> cartList = new ArrayList<>();


    public SalesModel(String sales_id, Blob sales_image, String sales_title, String sales_transaction) {
        this.sales_id = sales_id;
        this.sales_image = sales_image;
        this.sales_title = sales_title;
        this.sales_transaction = sales_transaction;
    }

    public SalesModel(Blob sales_image, String sales_title, String sales_transaction) {
        this.sales_image = sales_image;
        this.sales_title = sales_title;
        this.sales_transaction = sales_transaction;
    }

    public Blob getSales_image() {
        return sales_image;
    }

    public SalesModel() {
    }

    public String getSales_id() {
        return sales_id;
    }

    public String getSales_title() {
        return sales_title;
    }


    public String getSales_transaction() {
        return sales_transaction;
    }
}
