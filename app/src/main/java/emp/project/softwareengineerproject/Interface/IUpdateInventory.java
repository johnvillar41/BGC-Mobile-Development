package emp.project.softwareengineerproject.Interface;

import android.view.View;

import java.sql.Blob;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView{
        void initViews();
        void setHints(ProductModel model) throws SQLException;
        void goBack();
        void displayErrorMessage(String message, View v);
        void loadImageFromGallery();
    }
    interface  IUpdatePresenter{
        void onCancelButtonClicked();
        void onSaveButtonClicked(String product_title, String product_description,
                                 long product_price, int product_stocks, Blob picture,View v);
        void displayHints(ProductModel model) throws SQLException;
        void goToImageLibrary();
    }
    interface IDbHelper{
        void updateProductToDB(ProductModel model);
    }
}
