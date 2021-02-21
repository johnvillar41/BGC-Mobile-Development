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
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryUpdateService;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;
import emp.project.softwareengineerproject.Model.Database.Services.ReportsService;
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
            InventoryUpdateService.getInstance().updateProductTotal(connection, model);

            //Update Notifications
            NotificationModel newNotificationModel = NotificationService.getInstance().notificationFactory(model.getSales_title(),NotificationService.NotificationStatus.ADDED_SALES);
            NotificationService.getInstance().insertNewNotifications(newNotificationModel);

            //Update Reports Table
            ReportsService.getInstance().updateReportsTable(connection, model);

            preparedStatement.close();
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
