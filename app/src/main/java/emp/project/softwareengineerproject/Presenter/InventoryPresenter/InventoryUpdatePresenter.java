package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.os.Build;
import android.os.StrictMode;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PacketTooBigException;
import com.mysql.jdbc.PreparedStatement;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    IUpdateInventory.IUupdateInventoryView view;
    IUpdateInventory.IUpdateInventoryService service;
    InventoryModel model;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventoryUpdateService();
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
            TextInputLayout[] texts = new TextInputLayout[5];
            texts[0] = editText_productTitle;
            texts[1] = txt_product_description;
            texts[2] = txt_product_Price;
            texts[3] = txt_product_Stocks;
            texts[4] = txt_product_category;

            if (model.validateProductOnUpdate(texts, upload_picture, product_id) != null) {
                service.updateProductToDB(model.validateProductOnUpdate(texts, upload_picture, product_id));
                view.showCheckAnimation();
                view.displayStatusMessage("Successfully Updated Product!", v);
            }

        } catch (ClassNotFoundException e) {
            view.displayStatusMessage("Error Updating Product!", v);
        } catch (PacketTooBigException e) {
            view.displayStatusMessage(e.getMessage(), v);
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


        if (model.validateProductOnAdd(product_name,
                product_description,
                product_price,
                product_stocks,
                inputStream, product_category) != null) {
            try {
                service.addNewProduct(model.validateProductOnAdd(product_name,
                        product_description,
                        product_price,
                        product_stocks,
                        inputStream, product_category));
                view.showCheckAnimation();
                view.displayStatusMessage("Successfully creating product!", v);
            } catch (PacketTooBigException e) {
                view.displayStatusMessage(e.getMessage(), v);
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


    private static class InventoryUpdateService implements IUpdateInventory.IUpdateInventoryService {

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

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            PreparedStatement preparedStatement;
            if (model.getUpload_picture() != null) {
                preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_picture=?" +
                        "WHERE product_id=" + "'" + model.getProduct_id() + "'");
                preparedStatement.setBlob(1, model.getUpload_picture());
            } else {
                preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE products_table SET product_name=?," +
                        "product_description=?,product_price=?,product_stocks=?,product_category=? WHERE product_id=?");
                preparedStatement.setString(1, model.getProduct_name());
                preparedStatement.setString(2, model.getProduct_description());
                preparedStatement.setLong(3, model.getProduct_price());
                preparedStatement.setInt(4, model.getProduct_stocks());
                preparedStatement.setString(5, model.getProduct_category());
                preparedStatement.setString(6, model.getProduct_id());
            }
            preparedStatement.executeUpdate();

            String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            NotificationModel notificationModel;
            notificationModel = new NotificationModel("Updated product", "Updated product " + model.getProduct_name(), String.valueOf(dtf.format(now)),
                    MainMenuActivityView.GET_PREFERENCES_REALNAME);
            addNotifications(connection, sqlNotification, notificationModel);
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
            preparedStatement.setString(1, model.getProduct_name());
            preparedStatement.setString(2, model.getProduct_description());
            preparedStatement.setLong(3, model.getProduct_price());
            preparedStatement.setBlob(4, model.getUpload_picture());
            preparedStatement.setInt(5, model.getProduct_stocks());
            preparedStatement.setString(6, model.getProduct_category());
            preparedStatement.execute();
            preparedStatement.close();

            String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            NotificationModel notificationModel;
            notificationModel = new NotificationModel("Added product", "Added product " + model.getProduct_name(), String.valueOf(dtf.format(now)),
                    MainMenuActivityView.GET_PREFERENCES_REALNAME);
            addNotifications(connection, sqlNotification, notificationModel);
            preparedStatement.close();
            connection.close();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void addNotifications(Connection connection, String sqlNotification, NotificationModel notificationModel) throws ClassNotFoundException, SQLException {
            strictMode();
            //Adding notifications on database

            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sqlNotification);
            preparedStatement.setString(1, notificationModel.getNotif_title());
            preparedStatement.setString(2, notificationModel.getNotif_content());
            preparedStatement.setString(3, notificationModel.getNotif_date());
            preparedStatement.setString(4, notificationModel.getUser_name());
            preparedStatement.execute();
            preparedStatement.close();

        }
    }
}
