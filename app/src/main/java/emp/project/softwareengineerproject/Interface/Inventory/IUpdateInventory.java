package emp.project.softwareengineerproject.Interface.Inventory;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView {
        /**
         * Initializes all the objects from the xml
         */
        void initViews();

        /**
         * This function has 2 separate works
         * 1)This will be changed from an add activity to an update activity depending on its
         * product id, if the product id is null or a -1 meaning it has no selected product,
         * thus this will prompt the display as an add activity.
         * <p>
         * 2)This will be changed from an update activity to an add activity depending on its
         * product id, if the product id is not null meaning it has a selected product,
         * thus this will prompt the display as an update activity.
         *
         * @param model this will be used to set the hints on each EditText if update has been
         *              triggered
         */
        void setHints(InventoryModel model) throws SQLException;

        /**
         * finished the current activity
         */
        void goBack();

        /**
         * Displays the error messages in a snackbar that the user has encountered
         *
         * @param message the message of the status message that will be displayed
         * @param v       this will pass a view so that the snackbar can be displayed
         */
        void displayStatusMessage(String message, View v);

        /**
         * Will display the gallery of the users mobile phone
         */
        void loadImageFromGallery();

        /**
         * Displays loading bar
         */
        void showProgressIndicator();

        /**
         * Hides loading bar
         */
        void hideProgressIndicator();

        /**
         * Displays a check animation when the user has successfully did a transaction
         */
        void showCheckAnimation();

        void setErrorProductName(String errorMessage);

        void setErrorProductDescription(String errorMessage);

        void setErrorProductPrice(String errorMessage);

        void setErrorProductStocks(String errorMessage);

        void setErrorProductCategory(String errorMessage);

        void removeErrorProductName();

        void removeErrorProductDescription();

        void removeErrorProductPrice();

        void removeErrorProductStocks();

        void removeErrorProductCategory();

        void displayCategoryList(List<String>categories);
    }

    interface IUpdatePresenter {
        /**
         * Handles the cancel button clicked and finishes the activity
         */
        void onCancelButtonClicked();

        /**
         * Handles the save button event to update the product
         *
         * @param product_id
         * @param editText_productTitle
         * @param txt_product_description
         * @param txt_product_Price
         * @param txt_product_Stocks
         * @param upload_picture
         * @param txt_product_category
         * @param v
         */
        void onSaveProductButtonClicked(String product_id, TextInputLayout editText_productTitle,
                                        TextInputLayout txt_product_description,
                                        TextInputLayout txt_product_Price,
                                        TextInputLayout txt_product_Stocks, InputStream upload_picture,
                                        String txt_product_category, View v) throws SQLException;

        /**
         * Handles the addButton clicked from the toolbar
         *
         * @param product_name
         * @param product_description
         * @param product_price
         * @param product_stocks
         * @param inputStream
         * @param product_category
         * @param v                   Adds a new product to the database using all these params.
         */
        void onAddProductButtonClicked(String product_name,
                                       String product_description,
                                       String product_price,
                                       String product_stocks,
                                       InputStream inputStream,
                                       String product_category,
                                       View v);

        /**
         * When the activity loads this sets the hints to be displayed
         *
         * @param model parameter to be displayed in the editText
         */
        void onPageLoadHints(InventoryModel model) throws SQLException;

        /**
         * Handles the image click event to direct to the image Gallery of the users mobile phone
         */
        void onImageButtonClicked();

        void loadCategories();
    }

    interface IUpdateInventoryService extends IServiceStrictMode {
        /**
         * Updates the product in the database
         *
         * @param model this will be the new model for the Product
         */
        void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException;

        /**
         * Adds new product in the database
         *
         * @param model will add a new product model taking this parameter
         */
        void addNewProduct(InventoryModel model) throws ClassNotFoundException, SQLException;

        /**
         * This adds notifications on the database on each CRUD on the product a notification is created
         *
         * @param notificationModel parameter for the notfication object
         */
        void addNotifications(Connection connection, String sqlNotification, NotificationModel notificationModel) throws ClassNotFoundException, SQLException;

        HashSet<String> getCategories() throws ClassNotFoundException, SQLException;
    }
}
