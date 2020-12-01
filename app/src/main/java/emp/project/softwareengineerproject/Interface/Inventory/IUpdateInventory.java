package emp.project.softwareengineerproject.Interface.Inventory;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.NotificationModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView {
        void initViews();

        void setHints(InventoryModel model) throws SQLException;

        void goBack();

        void displayStatusMessage(String message, View v);

        void loadImageFromGallery();

        void showCheckAnimation();
    }

    interface IUpdatePresenter {
        void onCancelButtonClicked();

        void onSaveProductButtonClicked(String product_id, TextInputLayout editText_productTitle,
                                        TextInputLayout txt_product_description,
                                        TextInputLayout txt_product_Price,
                                        TextInputLayout txt_product_Stocks, InputStream upload_picture,
                                        TextInputLayout txt_product_category, View v) throws SQLException;

        void onAddProductButtonClicked(TextInputLayout product_name,
                                       TextInputLayout product_description,
                                       TextInputLayout product_price,
                                       TextInputLayout product_stocks,
                                       InputStream inputStream,
                                       TextInputLayout product_category,
                                       View v);

        void displayHints(InventoryModel model) throws SQLException;

        void ImageButtonClicked();

        void directCheckAnimation();
    }

    interface IUpdateInventoryService {

        void strictMode() throws ClassNotFoundException;

        void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException;

        void addNewProduct(InventoryModel model) throws ClassNotFoundException, SQLException;

        void addNotifications(Connection connection, String sqlNotification, NotificationModel notificationModel) throws ClassNotFoundException, SQLException;
    }
}
