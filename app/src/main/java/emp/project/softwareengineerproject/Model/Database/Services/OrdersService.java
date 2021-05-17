package emp.project.softwareengineerproject.Model.Database.Services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Model.Bean.SpecificOrdersModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryService;
import emp.project.softwareengineerproject.View.OrdersView.OrdersRecyclerView;

public class OrdersService implements IOrders.IOrdersService {

    private static OrdersService SINGLE_INSTANCE = null;

    private OrdersService() {

    }

    public static OrdersService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new OrdersService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @Override
    public List<OrdersModel> getOrdersFromDB(String status) throws ClassNotFoundException, SQLException {
        strictMode();
        List<OrdersModel> list = new ArrayList<>();
        String sqlGetOrders = "SELECT * FROM customer_orders_table WHERE order_status=?";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetOrders);
        preparedStatement.setString(1, status);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            OrdersModel model = new OrdersModel(
                    resultSet.getInt("order_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("order_total_price"),
                    resultSet.getString("order_status"),
                    resultSet.getDate("order_date"),
                    resultSet.getInt("total_number_of_orders"));
            list.add(model);
        }
        connection.close();
        preparedStatement.close();
        resultSet.close();
        return list;
    }

    public List<SpecificOrdersModel> getCustomerSpecificOrders(int order_id) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        List<SpecificOrdersModel> list = new ArrayList<>();
        String sqlQuery = "SELECT * FROM specific_orders_table WHERE order_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, order_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(
                    new SpecificOrdersModel(
                            resultSet.getInt("specific_orders_id"),
                            resultSet.getInt("order_id"),
                            resultSet.getString("administrator_username"),
                            InventoryService.getInstance().fetchProductGivenByID(order_id),
                            resultSet.getInt("total_orders"),
                            resultSet.getInt("subtotal_price")
                    )
            );
        }
        return list;
    }

    @Override
    public void updateOrderFromDB(int order_id, String status) throws ClassNotFoundException, SQLException {
        strictMode();
        String sql = "UPDATE customer_orders_table SET order_status=? WHERE order_id=?";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, order_id);
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNotificationInDB(OrdersRecyclerView.STATUS status, String content) throws ClassNotFoundException, SQLException {
        strictMode();
        NotificationModel newNotoficationModel = null;

        switch (status) {
            case PENDING_NOTIF:
                newNotoficationModel = NotificationService.getInstance().notificationFactory(
                        OrdersRecyclerView.STATUS.PENDING_NOTIF.getStatus(),
                        NotificationService.NotificationStatus.ORDER_PENDING);
                break;
            case CANCELLED_NOTIF:
                newNotoficationModel = NotificationService.getInstance().notificationFactory(
                        OrdersRecyclerView.STATUS.CANCELLED_NOTIF.getStatus(),
                        NotificationService.NotificationStatus.ORDER_CANCEL);
                break;
            case FINISHED_NOTIF:
                newNotoficationModel = NotificationService.getInstance().notificationFactory(
                        OrdersRecyclerView.STATUS.FINISHED_NOTIF.getStatus(),
                        NotificationService.NotificationStatus.ORDER_FINISHED);
                break;
        }
        if (newNotoficationModel != null) {
            NotificationService.getInstance().insertNewNotifications(newNotoficationModel);
        }
    }

}
