package emp.project.softwareengineerproject.Model.Database.Services.SalesService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;

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

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.View.LoginActivityView;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class SalesAddService implements ISalesAdd.ISalesAddService {
    private static SalesAddService SINGLE_INSTANCE = null;

    private SalesAddService() {

    }

    public static SalesAddService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new SalesAddService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException {
        strictMode();
        if (checkIfProductIsEnough(model.getProduct_id(), model.getTotal_number_of_products())) {
            String sql = "INSERT INTO sales_table(sales_title,sales_image,sales_transaction_value,product_id,total_number_of_products,sales_date,date_month,user_username) VALUES(?,?,?,?,?,?,?,?)";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getSales_title());
            preparedStatement.setBlob(2, model.getSales_image());
            preparedStatement.setLong(3, model.getProduct_total());
            preparedStatement.setString(4, model.getProduct_id());
            preparedStatement.setString(5, model.getTotal_number_of_products());
            preparedStatement.setString(6, model.getSales_date());
            preparedStatement.setString(7, model.getDate_month());
            preparedStatement.setString(8, LoginActivityView.USERNAME_VALUE);
            preparedStatement.execute();

            //Update Products
            String sqlMinusStocks = "UPDATE products_table SET product_stocks=product_stocks-" + "'" + model.getTotal_number_of_products() + "' WHERE product_id=" + "'" +
                    model.getProduct_id() + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlMinusStocks);


            //Update Notifications
            String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            NotificationModel notificationModel;
            notificationModel = new NotificationModel("Added sales", "Added sales " + model.getSales_title(), String.valueOf(dtf.format(now)),
                    MainMenuActivityView.GET_PREFERENCES_REALNAME);
            com.mysql.jdbc.PreparedStatement preparedStatement2 = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(sqlNotification);
            preparedStatement2.setString(1, notificationModel.getNotif_title());
            preparedStatement2.setString(2, notificationModel.getNotif_content());
            preparedStatement2.setString(3, notificationModel.getNotif_date());
            preparedStatement2.setString(4, notificationModel.getUser_name());

            //Update Reports Table
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
                preparedStatement_Insert.close();

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

                preparedStatement_Update_Sale.close();
            }


            preparedStatement2.execute();
            preparedStatement2.close();
            preparedStatement.close();
            statement.close();
            connection.close();
            return true;
        } else {
            return false;
        }


    }

    @Override
    public List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        InventoryModel model;
        List<InventoryModel> list = new ArrayList<>();
        String sql = "SELECT * FROM products_table";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                    (Blob) resultSet.getBlob(5),
                    resultSet.getInt(6), resultSet.getString(7));
            list.add(model);
        }
        statement.close();
        resultSet.close();
        connection.close();
        return list;
    }

    @Override
    public boolean checkIfProductIsEnough(String product_id, String total_orders) throws ClassNotFoundException, SQLException {
        strictMode();
        boolean isValid = false;
        String sqlSelect = "SELECT product_stocks FROM products_table WHERE product_id=" + "'" + product_id + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelect);
        while (resultSet.next()) {
            try {
                if (resultSet.getInt(1) < Integer.parseInt(total_orders)) {
                    isValid = false;
                } else {
                    isValid = true;
                }
            } catch (Exception e) {
                isValid = false;
            }

        }
        resultSet.close();
        connection.close();
        statement.close();
        return isValid;
    }


}
