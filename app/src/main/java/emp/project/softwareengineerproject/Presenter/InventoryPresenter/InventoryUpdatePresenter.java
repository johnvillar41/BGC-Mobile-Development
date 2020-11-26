package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.os.StrictMode;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PreparedStatement;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.ProductModel;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    IUpdateInventory.IUupdateInventoryView view;
    DBhelper dBhelper;
    ProductModel model;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view) {
        this.view = view;
        this.model = new ProductModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void onCancelButtonClicked() {
        view.goBack();
    }


    @Override
    public void onSaveProductButtonClicked(String product_id,
                                           TextInputLayout editText_productTitle,
                                           TextInputLayout txt_product_description,
                                           TextInputLayout txt_product_Price,
                                           TextInputLayout txt_product_Stocks,
                                           InputStream upload_picture,
                                           TextInputLayout txt_product_category, View v) throws SQLException {
        try {
            dBhelper.updateProductToDB(model.validateUpdate(editText_productTitle,
                    txt_product_description, txt_product_Price,
                    txt_product_Stocks, product_id, upload_picture, txt_product_category));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddProductButtonClicked(TextInputLayout product_name,
                                          TextInputLayout product_description,
                                          TextInputLayout product_price,
                                          TextInputLayout product_stocks,
                                          InputStream inputStream,
                                          TextInputLayout product_category,
                                          View v) {
        if (model.validateProduct(product_name,
                product_description,
                product_price,
                product_stocks,
                inputStream, product_category) != null) {
            try {
                dBhelper.addNewProduct(model);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            view.displayErrorMessage("Error creating product!", v);
        }
    }

    @Override
    public void displayHints(ProductModel model) throws SQLException {
        view.setHints(model);
    }

    @Override
    public void ImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private static class DBhelper implements IUpdateInventory.IDbHelper {

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
        public void updateProductToDB(ProductModel model) throws SQLException, ClassNotFoundException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sql = "UPDATE products_table SET product_picture=?" +
                    ",product_name=? WHERE product_id=" + "'" + model.getProduct_id() + "'";
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            if (model.getUpload_picture() != null) {
                preparedStatement.setBlob(1, model.getUpload_picture());
            } else {
                preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=? WHERE product_id=?");
                preparedStatement.setString(1,model.getProduct_name());
                preparedStatement.setString(2,model.getProduct_id());
            }
            preparedStatement.executeUpdate();

            String sqlcmd = "UPDATE products_table SET product_description=" + "'" + model.getProduct_description() + "'," +
                    "product_price=" + model.getProduct_price() + "," + "product_stocks=" + model.getProduct_stocks() +
                    " WHERE product_id=" + "'" + model.getProduct_id() + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlcmd);
            statement.close();
            connection.close();
        }

        @Override
        public void addNewProduct(ProductModel model) throws ClassNotFoundException, SQLException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sql = "INSERT INTO products_table(product_name,product_description,product_price,product_picture,product_stocks)" +
                    "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getProduct_name());
            preparedStatement.setString(2, model.getProduct_description());
            preparedStatement.setLong(3, model.getProduct_price());
            preparedStatement.setBlob(4, model.getUpload_picture());
            preparedStatement.setInt(5, model.getProduct_stocks());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
    }
}
