package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.StrictMode;

import com.mysql.jdbc.Blob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    ISalesAdd.ISalesAddView view;
    ISalesAdd.ISalesAddService service;
    SalesModel model;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesAddService();
    }

    @Override
    public void onCartButtonClicked(List<InventoryModel> cartList) {
        view.displayCart(cartList);
    }

    @Override
    public void directProductList() throws SQLException, ClassNotFoundException {
        view.displayProductRecyclerView(service.getProductListFromDB());
    }

    @Override
    public void onConfirmButtonClicked() {
        //calculate total value
    }

    private class SalesAddService implements ISalesAdd.ISalesAddService {

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
        public void insertOrderToDB() throws SQLException, ClassNotFoundException {
            strictMode();
            String sql = "INSERT INTO sales_table VALUES(?,?,?)";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,model.getSales_title());
            preparedStatement.setBlob(2,model.getSales_image());
            //preparedStatement
        }

        @Override
        public List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException {
            strictMode();
            InventoryModel model;
            List<InventoryModel> list = new ArrayList<>();
            String sql = "SELECT * FROM products_table";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                model = new InventoryModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4),
                        (Blob) resultSet.getBlob(5),
                        resultSet.getInt(6), resultSet.getString(7));
                list.add(model);
            }
            return list;
        }


    }
}
