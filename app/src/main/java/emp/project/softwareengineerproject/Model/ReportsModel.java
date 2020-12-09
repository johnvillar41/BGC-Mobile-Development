package emp.project.softwareengineerproject.Model;

public class ReportsModel {
    private int sales_transaction_value;
    private String sales_date;

    public ReportsModel(String sales_date, int sales_transaction_value) {
        this.sales_date = sales_date;
        this.sales_transaction_value = sales_transaction_value;
    }

    public ReportsModel() {
    }

    public String getSales_date() {
        return sales_date;
    }

    public int getSales_transaction_value() {
        return sales_transaction_value;
    }
}
