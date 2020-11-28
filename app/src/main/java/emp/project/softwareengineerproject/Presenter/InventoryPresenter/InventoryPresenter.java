package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.app.Activity;
import android.os.StrictMode;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.InventoryModel;

public class InventoryPresenter extends Activity implements IInvetory.IinventoryPresenter {
    private IInvetory.IinventoryView view;
    private InventoryModel model;
    private IInvetory.DBhelper dBhelper;

    public InventoryPresenter(IInvetory.IinventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void getGreenHouseFromDB() throws InterruptedException, SQLException, ClassNotFoundException {
        view.showProgressDialog();
        view.displayRecyclerView(dBhelper.getProductFromDB());
        view.hideProgressDialog();
    }

    @Override
    public void onAddProductButtonClicked() {
        view.goToAddProductPage();
    }

    @Override
    public void searchButtonClicked() {
        view.goToSearchPage();
    }

    @Override
    public void onSwipeRefresh() {
        view.refreshPage();
    }

    @Override
    public void onCardViewLongClicked(String product_id) throws SQLException, ClassNotFoundException {
        dBhelper.deleteItem(product_id);
    }


    private class DBhelper implements IInvetory.DBhelper {

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

        @Override
        public void deleteItem(String product_id) throws ClassNotFoundException, SQLException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String deleteItem = "DELETE FROM products_table WHERE product_id=" + "'" + product_id + "'";
            Statement statement = connection.createStatement();
            statement.execute(deleteItem);
            statement.close();
            connection.close();
        }


    }
}
