package emp.project.softwareengineerproject.Model;

public class SalesModel {
    private String sales_id;
    private String sales_title;
    private String sales_content;
    private String sales_transaction;


    public SalesModel(String sales_id, String sales_title, String sales_content, String sales_transaction) {
        this.sales_id = sales_id;
        this.sales_title = sales_title;
        this.sales_content = sales_content;
        this.sales_transaction = sales_transaction;
    }

    public SalesModel(String sales_title, String sales_content, String sales_transaction) {
        this.sales_title = sales_title;
        this.sales_content = sales_content;
        this.sales_transaction = sales_transaction;
    }

    public SalesModel() {
    }

    public String getSales_id() {
        return sales_id;
    }

    public String getSales_title() {
        return sales_title;
    }

    public String getSales_content() {
        return sales_content;
    }

    public String getSales_transaction() {
        return sales_transaction;
    }
}
