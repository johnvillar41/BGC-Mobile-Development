package emp.project.softwareengineerproject.Model;

public class ReportsModel {
    private int date_month;
    private float total_transactions_monthly;
    private String date;

    /**
     * This is for the charts
     *
     * @param date_month
     * @param total_transactions_monthly
     */
    public ReportsModel(int date_month, float total_transactions_monthly) {
        this.date_month = date_month;
        this.total_transactions_monthly = total_transactions_monthly;
    }

    /**
     * This is for the recyclerview
     *
     * @param date_month
     * @param total_transactions_monthly
     * @param date
     */
    public ReportsModel(int date_month, float total_transactions_monthly, String date) {
        this.date_month = date_month;
        this.total_transactions_monthly = total_transactions_monthly;
        this.date = date;
    }

    public ReportsModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDate_month() {
        return date_month;
    }

    public float getTotal_transactions_monthly() {
        return total_transactions_monthly;
    }
}
