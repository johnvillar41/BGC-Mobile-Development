package emp.project.softwareengineerproject.Presenter;

import android.graphics.Bitmap;
import android.os.StrictMode;
import android.view.View;


import com.mysql.jdbc.PreparedStatement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import emp.project.softwareengineerproject.Interface.IUpdateInventory;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.View.InventoryUpdateView;

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
    public void onSaveButtonClicked(String product_id, String product_name, String product_description, long product_price, int product_stocks, Blob upload_picture, View v) throws SQLException {
        model = new ProductModel(product_id, product_name, product_description, product_price,
                product_stocks, upload_picture);
        String isValid = model.validateProduct(model);
        if (isValid == null) {
            try {
                dBhelper.updateProductToDB(model);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            view.displayErrorMessage(model.validateProduct(model), v);
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
        public void updateProductToDB(ProductModel model) throws SQLException, ClassNotFoundException, FileNotFoundException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sql="UPDATE greenhouse_products SET product_picture=?" +
                    ",product_name=? WHERE product_id=" + "'" + model.getProduct_id() + "'";
            PreparedStatement preparedStatement= (PreparedStatement) connection.prepareStatement(sql);

            preparedStatement.setBlob(1, model.getUpload_picture());
            preparedStatement.setString(2,model.getProduct_name());
            preparedStatement.executeUpdate();

            /*String sqlcmd = "UPDATE greenhouse_products SET product_name=" + "'" + model.getProduct_name() + "'," + "product_description=" + "'" + model.getProduct_description() + "'," +
                    "product_price=" + model.getProduct_price() + "," + "product_stocks="  + model.getProduct_stocks() + ",product_picture='" + model.getUpload_picture() +
                    "' WHERE product_id=" + "'" + model.getProduct_id() + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlcmd);
            statement.close();*/
            connection.close();

        }
    }
}
