package emp.project.softwareengineerproject.Model.Database.Services.InventoryService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;

public class InventoryService implements IInvetory.IInventoryService {

    private static InventoryService SINGLE_INSTANCE = null;


    private InventoryService() {
    }

    public static InventoryService getInstance(InventoryModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventoryService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @Override
    public List<InventoryModel>[] getProductFromDB() {

        final ArrayList<InventoryModel>[] list = new ArrayList[3];
        try {
            strictMode();
            list[0] = new ArrayList<>();
            list[1] = new ArrayList<>();
            list[2] = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlGetGreenHouse = "SELECT * FROM products_table WHERE product_category='Greenhouse'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetGreenHouse);
            while (resultSet.next()) {
                InventoryModel model = new InventoryModel(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getInt("product_price"),
                        (Blob) resultSet.getBlob("product_picture"),
                        resultSet.getInt("product_stocks"),
                        resultSet.getString("product_category"));
                list[0].add(model);
            }

            String sqlGetHydroPonics = "SELECT * FROM products_table WHERE product_category='Hydroponics'";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sqlGetHydroPonics);
            while (resultSet2.next()) {
                InventoryModel model = new InventoryModel(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getInt("product_price"),
                        (Blob) resultSet.getBlob("product_picture"),
                        resultSet.getInt("product_stocks"),
                        resultSet.getString("product_category"));
                list[1].add(model);
            }

            String sqlGetOtherProducts = "SELECT * FROM products_table";
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery(sqlGetOtherProducts);
            while (resultSet3.next()) {
                InventoryModel model = new InventoryModel(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getInt("product_price"),
                        (Blob) resultSet.getBlob("product_picture"),
                        resultSet.getInt("product_stocks"),
                        resultSet.getString("product_category"));
                list[2].add(model);
            }


            resultSet2.close();
            statement2.close();
            resultSet.close();
            statement.close();
            connection.close();
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

        String deleteInformationItem = "DELETE FROM information_table WHERE product_id = ?";
        PreparedStatement preparedStatementDelete = (PreparedStatement) connection.prepareStatement(deleteInformationItem);
        preparedStatementDelete.setString(1, product_id);
        preparedStatementDelete.execute();

        //Notification
        NotificationModel newNotificationModel = NotificationService.getInstance().notificationFactory(model.getProductName(), NotificationService.NotificationStatus.DELETED_PRODUCT);
        NotificationService.getInstance().insertNewNotifications(newNotificationModel);

        statement.close();
        connection.close();
    }

    @Override
    public List<String> getCategoriesFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<String> tempList = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String selectCategory = "SELECT * FROM products_table";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectCategory);
        while (resultSet.next()) {
            tempList.add(resultSet.getString(7));
        }
        for (int i = 0; i < tempList.size(); i++) {
            if (!finalList.contains(tempList.get(i))) {
                finalList.add(tempList.get(i));
            }
        }
        connection.close();
        resultSet.close();
        statement.close();
        return finalList;

    }

    @Override
    public List<InventoryModel> getCategorizedItemsFromDB(String category) throws ClassNotFoundException, SQLException {
        strictMode();
        List<InventoryModel> list = new ArrayList<>();
        String sql = "SELECT * FROM products_table WHERE product_category=?";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
        preparedStatement.setString(1,category);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
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
        preparedStatement.close();
        resultSet.close();
        return list;
    }

    @Override
    public InventoryModel fetchProductGivenByID(int id) throws ClassNotFoundException, SQLException {
        strictMode();
        InventoryModel model = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sql = "SELECT * FROM products_table WHERE product_id=?";
        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            model = new InventoryModel(
                    resultSet.getInt("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getInt("product_price"),
                    resultSet.getBlob("product_picture"),
                    resultSet.getInt("product_stocks"),
                    resultSet.getString("product_category")
                    );
        }
        return model;
    }


}
