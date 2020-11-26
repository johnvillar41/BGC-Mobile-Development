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

import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.ProductModel;

public class InventoryPresenter extends Activity implements IInvetory.IinventoryPresenter {
    private IInvetory.IinventoryView view;
    private ProductModel model;
    private IInvetory.DBhelper dBhelper;

    public InventoryPresenter(IInvetory.IinventoryView view) {
        this.view = view;
        this.model = new ProductModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void getGreenHouseFromDB() throws InterruptedException, SQLException, ClassNotFoundException {
        view.showProgressDialog();
        view.displayRecyclerView(dBhelper.getGreenHouseDB());
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


    private class DBhelper implements IInvetory.DBhelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/agt_db";
        private String USER = "admin";
        private String PASS = "admin";

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public List<ProductModel>[] getGreenHouseDB() {

            final ArrayList<ProductModel>[] list = new ArrayList[3];
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
                    model = new ProductModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                            (Blob) resultSet.getBlob(5), resultSet.getInt(6), resultSet.getString(7));
                    list[0].add(model);
                }

                String sqlGetHydroPonics = "SELECT * FROM products_table WHERE product_category='Hydroponics'";
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(sqlGetHydroPonics);
                while (resultSet2.next()) {
                    model = new ProductModel(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getLong(4),
                            (Blob) resultSet2.getBlob(5), resultSet2.getInt(6), resultSet2.getString(7));
                    list[1].add(model);
                }

                String sqlGetOtherProducts = "SELECT * FROM products_table";
                Statement statement3 = connection.createStatement();
                ResultSet resultSet3 = statement3.executeQuery(sqlGetOtherProducts);
                while (resultSet3.next()) {
                    model = new ProductModel(resultSet3.getString(1), resultSet3.getString(2), resultSet3.getString(3), resultSet3.getLong(4),
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




    }
}
