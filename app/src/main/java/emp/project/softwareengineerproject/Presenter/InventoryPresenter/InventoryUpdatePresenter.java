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

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    IUpdateInventory.IUupdateInventoryView view;
    IUpdateInventory.IDbHelper dBhelper;
    InventoryModel model;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
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
            view.displayStatusMessage("Successfully Updated Product!", v);
        } catch (ClassNotFoundException e) {
            view.displayStatusMessage("Error Updating Product!", v);
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
                dBhelper.addNewProduct(model.validateProduct(product_name,
                        product_description,
                        product_price,
                        product_stocks,
                        inputStream, product_category));
                view.displayStatusMessage("Successfully creating product!", v);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            view.displayStatusMessage("Error creating product!", v);
        }
    }

    @Override
    public void displayHints(InventoryModel model) throws SQLException {
        view.setHints(model);
    }

    @Override
    public void ImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private static class DBhelper implements IUpdateInventory.IDbHelper {

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
        public void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            PreparedStatement preparedStatement;
            if (model.getUpload_picture() != null) {
                preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_picture=?" +
                        "WHERE product_id=" + "'" + model.getProduct_id() + "'");
                preparedStatement.setBlob(1, model.getUpload_picture());
                preparedStatement.setString(2, model.getProduct_id());
            } else {
                preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                        "product_description=?,product_price=?,product_stocks=?,product_category=? WHERE product_id=?");
                preparedStatement.setString(1, model.getProduct_name());
                preparedStatement.setString(2, model.getProduct_id());
                preparedStatement.setLong(3, model.getProduct_price());
                preparedStatement.setInt(4, model.getProduct_stocks());
                preparedStatement.setString(5, model.getProduct_category());
                preparedStatement.setString(6, model.getProduct_id());
            }
            preparedStatement.executeUpdate();
            connection.close();
        }

        @Override
        public void addNewProduct(InventoryModel model) throws ClassNotFoundException, SQLException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sql = "INSERT INTO products_table(product_name,product_description,product_price,product_picture,product_stocks,product_category)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, model.getProduct_name());
            preparedStatement.setString(2, model.getProduct_description());
            preparedStatement.setLong(3, model.getProduct_price());
            preparedStatement.setBlob(4, model.getUpload_picture());
            preparedStatement.setInt(5, model.getProduct_stocks());
            preparedStatement.setString(6, model.getProduct_category());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
    }
}
