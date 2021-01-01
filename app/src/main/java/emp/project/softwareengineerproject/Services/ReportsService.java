package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Model.UserModel;

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

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }


    @Override
    public int[] computeAverages(String username) throws ClassNotFoundException, SQLException {
        int[] averageValues = new int[3];
        int total = 0;
        int totalAverage;
        int counter = 0;
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetTotalValueOfSalesPerUser = "SELECT * FROM sales_table where user_username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetTotalValueOfSalesPerUser);
        preparedStatement.setString(1, username);
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

    @Override
    public ReportsModel getMonthlySales(String username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetMonthlyVals = "SELECT * FROM reports_table WHERE user_username=" + "'" + username + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetMonthlyVals);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            model = new ReportsModel(resultSet.getString("reports_id"), resultSet.getString("user_username"),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10),
                    resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14),
                    resultSet.getString(15));
        }

        return model;
    }

    @Override
    public List<String> getListOfAdministrators() throws ClassNotFoundException, SQLException {
        strictMode();
        List<String> adminList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetUsers = "SELECT user_username FROM login_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetUsers);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            adminList.add(resultSet.getString(1));
        }
        return adminList;
    }

    @Override
    public List<UserModel> getAdminsFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<UserModel> adminList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetAdmins = "SELECT * FROM login_table";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAdmins);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserModel userModel = new UserModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                    resultSet.getBlob(5));
            adminList.add(userModel);
        }
        return adminList;
    }

}
