package emp.project.softwareengineerproject.Model.Database.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.Bean.ReportsModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class ReportsService implements IReports.IReportsService {

    private ReportsModel model;
    private static ReportsService SINGLE_INSTANCE = null;

    private ReportsService() {

    }

    public static ReportsService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ReportsService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
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
        try {
            totalAverage = total / counter;
        } catch (ArithmeticException e) {
            totalAverage = total;
        }
        averageValues[0] = totalAverage;//average sales
        averageValues[1] = total;//total sales per user
        averageValues[2] = totalAverage;//total average monthly
        connection.close();
        preparedStatement.close();
        resultSet.close();
        return averageValues;
    }

    @Override
    public ReportsModel getMonthlySales(String username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetMonthlyVals = "SELECT * FROM reports_table WHERE user_username=" + "'" + username + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetMonthlyVals);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            model = new ReportsModel(
                    resultSet.getString("reports_id"),
                    resultSet.getString("user_username"),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10),
                    resultSet.getString(11), resultSet.getString(12), resultSet.getString(13), resultSet.getString(14),
                    resultSet.getString(15));
        }
        connection.close();
        preparedStatement.close();
        resultSet.close();
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
        connection.close();
        preparedStatement.close();
        resultSet.close();
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
            UserModel userModel = new UserModel(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getBlob(5));
            //Add new parameter of total sales
            adminList.add(userModel);
        }
        connection.close();
        preparedStatement.close();
        resultSet.close();
        return adminList;
    }

    public void updateReportsTable(Connection connection, SalesModel model) throws SQLException {
        DateTimeFormatter dtf_year = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now_year = LocalDateTime.now();
        /**
         * Select latest year in dbase
         */
        String sqlGetLatestYear = "SELECT sales_year FROM reports_table WHERE user_username=" + "'" + LoginActivityView.USERNAME_VALUE + "'AND sales_year=" +
                "'" + dtf_year.format(now_year) + "'";
        Statement statement_SearchYear = connection.createStatement();
        ResultSet resultSet_Year = statement_SearchYear.executeQuery(sqlGetLatestYear);
        if (resultSet_Year.next()) {
            DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("M");
            LocalDateTime now_month = LocalDateTime.now();
            String sqlUpdateReports = "UPDATE reports_table SET sales_month_" + Integer.parseInt(dtf_month.format(now_month)) + "=" +
                    "sales_month_" + Integer.parseInt(dtf_month.format(now_month)) + "+ ?" +
                    " WHERE user_username=? AND sales_year=" + "'" + dtf_year.format(now_year) + "'";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlUpdateReports);
            preparedStatement3.setLong(1, model.getProduct_total());
            preparedStatement3.setString(2, LoginActivityView.USERNAME_VALUE);
            preparedStatement3.execute();

            preparedStatement3.close();
        } else {
            /**
             * Insert new row in dbase if year is not equal to year.now
             */
            String sqlInsertNewRow = "INSERT INTO reports_table(user_username," +
                    "sales_month_1,sales_month_2,sales_month_3,sales_month_4," +
                    "sales_month_5,sales_month_6,sales_month_7,sales_month_8," +
                    "sales_month_9,sales_month_10,sales_month_11,sales_month_12," +
                    "sales_year) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement_Insert = connection.prepareStatement(sqlInsertNewRow);
            preparedStatement_Insert.setString(1, LoginActivityView.USERNAME_VALUE);
            preparedStatement_Insert.setString(2, "0");
            preparedStatement_Insert.setString(3, "0");
            preparedStatement_Insert.setString(4, "0");
            preparedStatement_Insert.setString(5, "0");
            preparedStatement_Insert.setString(6, "0");
            preparedStatement_Insert.setString(7, "0");
            preparedStatement_Insert.setString(8, "0");
            preparedStatement_Insert.setString(9, "0");
            preparedStatement_Insert.setString(10, "0");
            preparedStatement_Insert.setString(11, "0");
            preparedStatement_Insert.setString(12, "0");
            preparedStatement_Insert.setString(13, "0");
            preparedStatement_Insert.setString(14, dtf_year.format(now_year));
            preparedStatement_Insert.execute();


            /**
             * Update the new dbase of the newly created sales product
             */
            DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("M");
            LocalDateTime now_month = LocalDateTime.now();
            String sqlUpdateReports = "UPDATE reports_table SET sales_month_" + Integer.parseInt(dtf_month.format(now_month)) + "=" +
                    "sales_month_" + Integer.parseInt(dtf_month.format(now_month)) + "+ ?" +
                    " WHERE user_username=? AND sales_year=" + "'" + dtf_year.format(now_year) + "'";
            PreparedStatement preparedStatement_Update_Sale = connection.prepareStatement(sqlUpdateReports);
            preparedStatement_Update_Sale.setLong(1, model.getProduct_total());
            preparedStatement_Update_Sale.setString(2, LoginActivityView.USERNAME_VALUE);
            preparedStatement_Update_Sale.execute();

            //Close all
            preparedStatement_Update_Sale.close();
            preparedStatement_Insert.close();
            resultSet_Year.close();
        }
    }

}
