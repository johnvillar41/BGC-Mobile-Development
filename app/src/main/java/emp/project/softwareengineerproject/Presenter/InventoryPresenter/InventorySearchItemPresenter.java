package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.os.StrictMode;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;

public class InventorySearchItemPresenter implements ISearchInventory.ISearchInventoryPresenter {
    ISearchInventory.ISearchInventoryView view;
    InventoryModel model;
    ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventorySearchItemService();
    }

    @Override
    public void onSearchItemProduct(String product_name) throws ClassNotFoundException {
        view.displayRecyclerView(service.getSearchedProductFromDB(product_name));
    }

    private class InventorySearchItemService implements ISearchInventory.ISearchInventoryService {

        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();


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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }

}
