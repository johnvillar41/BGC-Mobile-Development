package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.OrdersModel;

public class OrdersService implements IOrders.IOrdersService {

    private OrdersModel model;
    private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
    private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
    private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();
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
    public List<OrdersModel> getOrdersFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<OrdersModel> list = new ArrayList<>();
        String sqlGetOrders = "SELECT * FROM customer_orders_table";
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
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8));
            list.add(model);
        }
        return list;
    }


}
