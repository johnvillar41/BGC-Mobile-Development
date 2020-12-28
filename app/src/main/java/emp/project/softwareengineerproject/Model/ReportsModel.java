package emp.project.softwareengineerproject.Model;

public class ReportsModel {
   private String reports_id,user_username,
           sales_month_1,sales_month_2,sales_month_3,
           sales_month_4,sales_month_5,sales_month_6,
           sales_month_7,sales_month_8,sales_month_9,
           sales_month_10,sales_month_11,sales_month_12,sales_year;

    public ReportsModel(String user_username, String sales_month_1,
                        String sales_month_2, String sales_month_3,
                        String sales_month_4, String sales_month_5,
                        String sales_month_6, String sales_month_7,
                        String sales_month_8, String sales_month_9,
                        String sales_month_10, String sales_month_11,
                        String sales_month_12, String sales_year) {
        this.user_username = user_username;
        this.sales_month_1 = sales_month_1;
        this.sales_month_2 = sales_month_2;
        this.sales_month_3 = sales_month_3;
        this.sales_month_4 = sales_month_4;
        this.sales_month_5 = sales_month_5;
        this.sales_month_6 = sales_month_6;
        this.sales_month_7 = sales_month_7;
        this.sales_month_8 = sales_month_8;
        this.sales_month_9 = sales_month_9;
        this.sales_month_10 = sales_month_10;
        this.sales_month_11 = sales_month_11;
        this.sales_month_12 = sales_month_12;
        this.sales_year = sales_year;
    }

    public ReportsModel() {
    }

    public String getUser_username() {
        return user_username;
    }

    public String getSales_month_1() {
        return sales_month_1;
    }

    public String getSales_month_2() {
        return sales_month_2;
    }

    public String getSales_month_3() {
        return sales_month_3;
    }

    public String getSales_month_4() {
        return sales_month_4;
    }

    public String getSales_month_5() {
        return sales_month_5;
    }

    public String getSales_month_6() {
        return sales_month_6;
    }

    public String getSales_month_7() {
        return sales_month_7;
    }

    public String getSales_month_8() {
        return sales_month_8;
    }

    public String getSales_month_9() {
        return sales_month_9;
    }

    public String getSales_month_10() {
        return sales_month_10;
    }

    public String getSales_month_11() {
        return sales_month_11;
    }

    public String getSales_month_12() {
        return sales_month_12;
    }

    public String getSales_year() {
        return sales_year;
    }
}
