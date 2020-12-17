package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;

public class ReportsService implements IReports.IReportsService {

    private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
    private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
    private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

    private ReportsModel model;

    public ReportsService(ReportsModel model) {
        this.model = model;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public List<ReportsModel> getMonthValuesFromDB() throws SQLException, ClassNotFoundException {
        strictMode();
        float total_value_per_month = 0;
        int months = 0;
        List<ReportsModel> reportsList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {

            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            String sql = "SELECT * from sales_table WHERE date_month=" + "'" + i + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total_value_per_month += resultSet.getInt(4);
            }
            model = new ReportsModel(months, total_value_per_month);

            months++;

            reportsList.add(model);
            if (true) {
                total_value_per_month = 0;
            }
        }
        return reportsList;
    }

    @Override
    public List<ReportsModel> getRecyclerViewValuesFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<ReportsModel> list = new ArrayList<>();
        int counter = 0;
        int total_transactions = 0;
        String date_month;
        String sales_date;
        for (int i = 0; i < 13; i++) {
            String sql = "SELECT date_month,sales_transaction_value,sales_date from sales_table WHERE date_month=" + "'" + (counter++) + "'";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total_transactions += Integer.parseInt(resultSet.getString(2));
                sales_date = resultSet.getString(3);
                date_month = resultSet.getString(1);
                model = new ReportsModel(Integer.parseInt(date_month), total_transactions, sales_date);
                list.add(model);

            }

        }
        return list;
    }


}
