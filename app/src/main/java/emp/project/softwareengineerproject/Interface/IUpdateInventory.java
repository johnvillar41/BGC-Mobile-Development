package emp.project.softwareengineerproject.Interface;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView {
        void initViews();

        void setHints(ProductModel model) throws SQLException;

        void goBack();

        void displayErrorMessage(String message, View v);

        void loadImageFromGallery();
    }

    interface IUpdatePresenter {
        void onCancelButtonClicked();

        void onSaveButtonClicked(String product_id, TextInputLayout editText_productTitle,
                                 TextInputLayout txt_product_description,
                                 TextInputLayout txt_product_Price,
                                 TextInputLayout txt_product_Stocks, InputStream upload_picture, View v) throws SQLException;

        void displayHints(ProductModel model) throws SQLException;

        void ImageButtonClicked();
    }

    interface IDbHelper {

        void strictMode() throws ClassNotFoundException;

        void updateProductToDB(ProductModel model) throws SQLException, ClassNotFoundException, FileNotFoundException;
    }
}
