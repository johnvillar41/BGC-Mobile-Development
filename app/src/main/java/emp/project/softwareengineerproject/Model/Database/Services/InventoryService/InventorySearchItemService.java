package emp.project.softwareengineerproject.Model.Database.Services.InventoryService;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class InventorySearchItemService implements ISearchInventory.ISearchInventoryService {
    private InventoryModel model;

    private static InventorySearchItemService SINGLE_INSTANCE = null;

    private InventorySearchItemService(InventoryModel model) {
        this.model = model;
    }

    public static InventorySearchItemService getInstance(InventoryModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventorySearchItemService(model);
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
    public List<InventoryModel> getSearchedProductFromDB(String searchedItem) throws ClassNotFoundException {
        strictMode();
        List<InventoryModel> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String searchSQL = "SELECT * FROM products_table WHERE product_name LIKE " + "'" + searchedItem + "%'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(searchSQL);
            while (resultSet.next()) {
                model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                        (Blob) resultSet.getBlob(5), resultSet.getInt(6), resultSet.getString(7));
                list.add(model);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void deleteItem(String product_id, InventoryModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String deleteItem = "DELETE FROM products_table WHERE product_id=" + "'" + product_id + "'";
        Statement statement = connection.createStatement();
        statement.execute(deleteItem);

        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel;
        notificationModel = new NotificationModel("Deleted product", "Deleted product " + model.getProduct_name(), String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sqlNotification);
        preparedStatement.setString(1, notificationModel.getNotif_title());
        preparedStatement.setString(2, notificationModel.getNotif_content());
        preparedStatement.setString(3, notificationModel.getNotif_date());
        preparedStatement.setString(4, notificationModel.getUser_name());
        preparedStatement.execute();
        preparedStatement.close();
        preparedStatement.close();

        statement.close();
        preparedStatement.close();
        connection.close();
    }
}
