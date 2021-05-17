package emp.project.softwareengineerproject.Model.Database.Services.InventoryService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Model.Database.Services.InformationService;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;

public class InventoryUpdateService implements IUpdateInventory.IUpdateInventoryService {
    private static InventoryUpdateService SINGLE_INSTANCE = null;

    private InventoryUpdateService() {

    }

    public static InventoryUpdateService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventoryUpdateService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement;

        if (model.getProductPicture() == null) {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                    "product_description=?,product_price=?,product_stocks=?,product_category=? WHERE product_id=?");
            preparedStatement.setString(1, model.getProductName());
            preparedStatement.setString(2, model.getProductDescription());
            preparedStatement.setLong(3, model.getProductPrice());
            preparedStatement.setInt(4, model.getProductStocks());
            preparedStatement.setString(5, model.getProductCategory());
            preparedStatement.setInt(6, model.getProductID());
        } else {
            preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                    "product_description=?,product_price=?,product_stocks=?,product_category=?,product_picture=? WHERE product_id=?");
            preparedStatement.setString(1, model.getProductName());
            preparedStatement.setString(2, model.getProductDescription());
            preparedStatement.setInt(3, model.getProductPrice());
            preparedStatement.setInt(4, model.getProductStocks());
            preparedStatement.setString(5, model.getProductCategory());
            preparedStatement.setBlob(6, model.getProductPicture());
            preparedStatement.setInt(7, model.getProductID());
        }
        preparedStatement.executeUpdate();

        //Notifications
        NotificationModel newNotificationModel = NotificationService.getInstance().notificationFactory(model.getProductName(),NotificationService.NotificationStatus.UPDATED_PRODUCT);
        NotificationService.getInstance().insertNewNotifications(newNotificationModel);


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
        preparedStatement.setString(1, model.getProductName());
        preparedStatement.setString(2, model.getProductDescription());
        preparedStatement.setLong(3, model.getProductPrice());
        preparedStatement.setBlob(4, model.getProductPicture());
        preparedStatement.setInt(5, model.getProductStocks());
        preparedStatement.setString(6, model.getProductCategory());
        preparedStatement.execute();
        preparedStatement.close();

        //inserting values to notification_table
        NotificationModel newNotificationModel = NotificationService.getInstance().notificationFactory(model.getProductName(),NotificationService.NotificationStatus.ADDED_PRODUCT);
        NotificationService.getInstance().insertNewNotifications(newNotificationModel);

        //Inserting values to information_table
        InformationService.getInstance().insertNewInformation(connection);

        preparedStatement.close();
        connection.close();
    }

    @Override
    public HashSet<String> getCategories() throws ClassNotFoundException, SQLException {
        strictMode();
        HashSet<String> categories = new HashSet<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetCategory = "SELECT product_category from products_table";
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sqlGetCategory);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            categories.add(resultSet.getString("product_category"));
        }
        categories.add("GREENHOUSE");
        categories.add("HYDROPONICS");
        resultSet.close();
        connection.close();
        preparedStatement.close();
        return categories;
    }

    public void updateProductTotal(Connection connection, SalesModel model) throws SQLException {
        String sqlMinusStocks = "UPDATE products_table SET product_stocks=product_stocks-" + "'" + model.getTotal_number_of_products() + "' WHERE product_id=" + "'" +
                model.getProduct_id() + "'";
        Statement statement = connection.createStatement();
        statement.execute(sqlMinusStocks);
        statement.close();
    }


}
