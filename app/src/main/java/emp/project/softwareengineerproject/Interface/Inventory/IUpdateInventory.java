package emp.project.softwareengineerproject.Interface.Inventory;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView {
        void initViews();

        void setHints(ProductModel model) throws SQLException;

        void goBack();

        void displayStatusMessage(String message, View v);

        void loadImageFromGallery();
    }

    interface IUpdatePresenter {
        void onCancelButtonClicked();

        void onSaveProductButtonClicked(String product_id, TextInputLayout editText_productTitle,
                                        TextInputLayout txt_product_description,
                                        TextInputLayout txt_product_Price,
                                        TextInputLayout txt_product_Stocks, InputStream upload_picture,
                                        TextInputLayout txt_product_category,View v) throws SQLException;
        void onAddProductButtonClicked(TextInputLayout product_name,
                                       TextInputLayout product_description,
                                       TextInputLayout product_price,
                                       TextInputLayout product_stocks,
                                       InputStream inputStream,
                                       TextInputLayout product_category,
                                       View v);

        void displayHints(ProductModel model) throws SQLException;

        void ImageButtonClicked();
    }

    interface IDbHelper {

        void strictMode() throws ClassNotFoundException;

        void updateProductToDB(ProductModel model) throws SQLException, ClassNotFoundException;

        void addNewProduct(ProductModel model) throws ClassNotFoundException, SQLException;
    }
}
