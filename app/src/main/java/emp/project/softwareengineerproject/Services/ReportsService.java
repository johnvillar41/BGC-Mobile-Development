package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class ReportsService implements IReports.IReportsService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private ReportsModel model;
    private static ReportsService SINGLE_INSTANCE = null;

    private ReportsService(ReportsModel model) {
        this.model = model;
    }

    public static ReportsService getInstance(ReportsModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ReportsService(model);
        }
        return SINGLE_INSTANCE;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }


    @Override
    public int[] computeAverages() throws ClassNotFoundException, SQLException {
        int[] averageValues = new int[3];
        int total = 0;
        int totalAverage;
        int counter = 0;
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetTotalValueOfSalesPerUser = "SELECT * FROM sales_table where user_username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetTotalValueOfSalesPerUser);
        preparedStatement.setString(1, LoginActivityView.USERNAME_VALUE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            total += resultSet.getInt(4);
            counter++;
        }
        totalAverage = total / counter;
        averageValues[0] = totalAverage;//average sales
        averageValues[1] = total;//total sales per user
        averageValues[2] = totalAverage;//total average monthly
        return averageValues;
    }
}
