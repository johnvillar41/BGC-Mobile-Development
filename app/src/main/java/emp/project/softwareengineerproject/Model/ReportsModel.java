package emp.project.softwareengineerproject.Model;

public class ReportsModel {
    private int date_month;
    private float total_transactions_monthly;

    public ReportsModel(int date_month, float total_transactions_monthly) {
        this.date_month = date_month;
        this.total_transactions_monthly = total_transactions_monthly;
    }

    public ReportsModel() {
    }

    public int getDate_month() {
        return date_month;
    }

    public float getTotal_transactions_monthly() {
        return total_transactions_monthly;
    }
}
