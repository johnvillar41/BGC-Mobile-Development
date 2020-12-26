package emp.project.softwareengineerproject.Services.InventoryService;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class InventoryUpdateService implements IUpdateInventory.IUpdateInventoryService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private static InventoryUpdateService SINGLE_INSTANCE = null;

    private InventoryUpdateService(){

    }

    public static InventoryUpdateService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventoryUpdateService();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement;

        if (model.getProduct_picture() == null) {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                    "product_description=?,product_price=?,product_stocks=?,product_category=? WHERE product_id=?");
            preparedStatement.setString(1, model.getProduct_name());
            preparedStatement.setString(2, model.getProduct_description());
            preparedStatement.setLong(3, model.getProduct_price());
            preparedStatement.setInt(4, model.getProduct_stocks());
            preparedStatement.setString(5, model.getProduct_category());
            preparedStatement.setString(6, model.getProduct_id());
        } else {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                    "product_description=?,product_price=?,product_stocks=?,product_category=?,product_picture=? WHERE product_id=?");
            preparedStatement.setString(1, model.getProduct_name());
            preparedStatement.setString(2, model.getProduct_description());
            preparedStatement.setLong(3, model.getProduct_price());
            preparedStatement.setInt(4, model.getProduct_stocks());
            preparedStatement.setString(5, model.getProduct_category());
            preparedStatement.setBlob(6, model.getUpload_picture());
            preparedStatement.setString(7, model.getProduct_id());
        }

        preparedStatement.executeUpdate();

        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel;
        notificationModel = new NotificationModel("Updated product", "Updated product " + model.getProduct_name(), String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        addNotifications(connection, sqlNotification, notificationModel);


        preparedStatement.close();
        connection.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNewProduct(InventoryModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sql = "INSERT INTO products_table(product_name,product_description,product_price,product_picture,product_stocks,product_category)" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
        preparedStatement.setString(1, model.getProduct_name());
        preparedStatement.setString(2, model.getProduct_description());
        preparedStatement.setLong(3, model.getProduct_price());
        preparedStatement.setBlob(4, model.getUpload_picture());
        preparedStatement.setInt(5, model.getProduct_stocks());
        preparedStatement.setString(6, model.getProduct_category());
        preparedStatement.execute();
        preparedStatement.close();

        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel;
        notificationModel = new NotificationModel("Added product", "Added product " + model.getProduct_name(), String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        addNotifications(connection, sqlNotification, notificationModel);

        preparedStatement.close();
        connection.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNotifications(Connection connection, String sqlNotification, NotificationModel notificationModel) throws ClassNotFoundException, SQLException {
        strictMode();
        //Adding notifications on database

        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sqlNotification);
        preparedStatement.setString(1, notificationModel.getNotif_title());
        preparedStatement.setString(2, notificationModel.getNotif_content());
        preparedStatement.setString(3, notificationModel.getNotif_date());
        preparedStatement.setString(4, notificationModel.getUser_name());
        preparedStatement.execute();
        preparedStatement.close();

    }
}
