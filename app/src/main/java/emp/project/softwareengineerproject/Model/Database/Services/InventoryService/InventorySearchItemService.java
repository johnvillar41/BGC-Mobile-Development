package emp.project.softwareengineerproject.Model.Database.Services.InventoryService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;

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
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class InventorySearchItemService implements ISearchInventory.ISearchInventoryService {

    private static InventorySearchItemService SINGLE_INSTANCE = null;

    private InventorySearchItemService() {

    }

    public static InventorySearchItemService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventorySearchItemService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
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
                InventoryModel model = new InventoryModel(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getInt("product_price"),
                        (Blob) resultSet.getBlob("product_picture"),
                        resultSet.getInt("product_stocks"),
                        resultSet.getString("product_category"));
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
    public void deleteItem(int product_id, InventoryModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String deleteItem = "DELETE FROM products_table WHERE product_id=" + "'" + product_id + "'";
        Statement statement = connection.createStatement();
        statement.execute(deleteItem);

        //Notifications
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel;
        notificationModel = new NotificationModel("Deleted product", "Deleted product " + model.getProductName(), String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        NotificationService.getInstance().insertNewNotifications(notificationModel);

        statement.close();
        connection.close();
    }
}
