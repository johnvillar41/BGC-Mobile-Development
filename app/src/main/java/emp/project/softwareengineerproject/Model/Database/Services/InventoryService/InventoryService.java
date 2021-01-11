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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class InventoryService implements IInvetory.IInventoryService {

    private static InventoryService SINGLE_INSTANCE = null;
    private InventoryModel model;

    private InventoryService(InventoryModel model) {
        this.model = model;
    }

    public static InventoryService getInstance(InventoryModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new InventoryService(model);
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
                model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                        (Blob) resultSet.getBlob(5), resultSet.getInt(6), resultSet.getString(7));
                list[0].add(model);
            }

            String sqlGetHydroPonics = "SELECT * FROM products_table WHERE product_category='Hydroponics'";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(sqlGetHydroPonics);
            while (resultSet2.next()) {
                model = new InventoryModel(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getLong(4),
                        (Blob) resultSet2.getBlob(5), resultSet2.getInt(6), resultSet2.getString(7));
                list[1].add(model);
            }

            String sqlGetOtherProducts = "SELECT * FROM products_table";
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery(sqlGetOtherProducts);
            while (resultSet3.next()) {
                model = new InventoryModel(resultSet3.getString(1), resultSet3.getString(2), resultSet3.getString(3), resultSet3.getLong(4),
                        (Blob) resultSet3.getBlob(5), resultSet3.getInt(6), resultSet3.getString(7));
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
        String sql = "SELECT * FROM products_table WHERE product_category=" + "'" + category + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                    (Blob) resultSet.getBlob(5), resultSet.getInt(6), resultSet.getString(7));
            list.add(model);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }


}
