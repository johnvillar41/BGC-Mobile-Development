package emp.project.softwareengineerproject.Services;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

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

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class OrdersService implements IOrders.IOrdersService {

    private OrdersModel model;
    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    public OrdersService(OrdersModel model) {
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
    public List<OrdersModel> getOrdersFromDB(String status) throws ClassNotFoundException, SQLException {
        strictMode();
        List<OrdersModel> list = new ArrayList<>();
        String sqlGetOrders = "SELECT * FROM customer_orders_table WHERE order_status=" + "'" + status + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetOrders);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            model = new OrdersModel(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));
            list.add(model);
        }
        connection.close();
        preparedStatement.close();
        resultSet.close();
        return list;
    }

    @Override
    public List<OrdersModel> getCustomerSpecificOrders(String customer_email, String order_date) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        List<OrdersModel> list = new ArrayList<>();
        /**
         * Select customer orders
         */
        String sqlGetSpecificOrders = "SELECT * FROM customer_orders_table WHERE customer_email=" + "'" + customer_email + "'" + " AND order_date=" + "'" + order_date + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetSpecificOrders);
        while (resultSet.next()) {
            /**
             * Get the product id from customer_orders_table to gather the information from the products_table
             */
            String sqlGetProducts = "SELECT product_picture,product_name FROM products_table WHERE product_id=" + "'" + resultSet.getString(7) + "'";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlGetProducts);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                model = new OrdersModel(resultSet.getString(7), resultSet.getString(8), resultSet2.getBlob(1), resultSet2.getString(2));
                list.add(model);
            }
            preparedStatement2.close();
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }

    @Override
    public void updateOrderFromDB(String order_id, String status) throws ClassNotFoundException, SQLException {
        strictMode();
        String sql = "UPDATE customer_orders_table SET order_status=? WHERE order_id=" + "'" + order_id + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, status);
        preparedStatement.executeUpdate();

        connection.close();
        preparedStatement.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNotificationInDB(String title, String content) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel = new NotificationModel(title, content, String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        PreparedStatement preparedStatementUpdateNotification = connection.prepareStatement(sqlNotification);
        preparedStatementUpdateNotification.setString(1, notificationModel.getNotif_title());
        preparedStatementUpdateNotification.setString(2, notificationModel.getNotif_content());
        preparedStatementUpdateNotification.setString(3, notificationModel.getNotif_date());
        preparedStatementUpdateNotification.setString(4, notificationModel.getUser_name());
        preparedStatementUpdateNotification.execute();

        connection.close();
        preparedStatementUpdateNotification.close();

    }


}
