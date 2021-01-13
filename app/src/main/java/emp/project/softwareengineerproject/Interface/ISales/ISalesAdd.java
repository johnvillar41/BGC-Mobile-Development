package emp.project.softwareengineerproject.Interface.ISales;

import android.view.View;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;

public interface ISalesAdd {
    interface ISalesAddView {
        /**
         * Initializes all the items on xml
         */
        void initViews() throws SQLException, ClassNotFoundException;

        /**
         * Displays the popup message in which it shows the items selected to be transacted
         * @param cartList list of items inside the cart
         */
        void displayCart(List<InventoryModel> cartList);

        /**
         * Displays the product in a recyclerView in the activity
         * @param list a list of all the products in the database
         */
        void displayProducts(List<InventoryModel> list);

        /**
         * Displays a check animation whether a transaction is successfull
         */
        void displaySuccessfullPrompt();

        /**
         * Displays an error message to prompt the user that something is wrong
         * @param message the message to be displayed
         * @param v View so that the snackbar can be displayed
         */
        void displayOnErrorMessage(String message, View v);

        /**
         * Displays the Linear Progress Loader each time a transaction is created
         */
        void displayProgressIndicator();

        /**
         * Hides the Linear Progress Loader
         */
        void hideProgressIndicator();

        /**
         * Displays a Linear Progress Loader in the cartView when a transaction is triggered
         */
        void displayProgressIndicatorCart();

        /**
         * Hides the Linear Progress loader in the cartView
         */
        void hideProgressIndicatorCart();
    }

    interface ISalesAddPresenter {
        /**
         * Handles the click event when the user clicks the floating action button cart logo
         * to finalize his transactions and redirects it to the cartView Popup
         * @param salesModels list of the products displayed in the cartView
         */
        void onCartButtonClicked(List<InventoryModel> salesModels);

        /**
         * Updates the number of products in the SalesActivity each time a transaction is triggered
         * so that it can update the number of products present on the activity
         */
        void loadProductList() throws SQLException, ClassNotFoundException;

        /**
         * Handles the confirm button click event in the cartView and redirects all of the products
         * inside the cart to the service class
         */
        void onConfirmButtonClicked(View v);
    }

    interface ISalesAddService extends IServiceStrictMode {
        /**
         * Inserts all of the products in the cart inside the database.
         * This also adds a notification in the notifications table creating a new notification object.
         * Also updates the number of products inside the database
         * @param model SalesModel to be inserted inside the database
         * @return will return a boolean whether the transaction is successfull or not also
         *         will check if the product ordered has enough number of products inside the database.
         */
        boolean insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException;

        /**
         * Fetches the new updated version of products from the database
         * @return  returns a new List of products
         */
        List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException;

        /**
         * This will check wether the order is enough to make a transaction in the database
         * @param product_id checks the product id in the database for it to find the specific item
         * @param total_orders total number of orders.
         * @return returns if the product is enough.
         */
        boolean checkIfProductIsEnough(String product_id,String total_orders) throws ClassNotFoundException, SQLException;

    }
}
