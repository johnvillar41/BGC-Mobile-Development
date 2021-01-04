package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.OrdersModel;

public interface IOrders {
    interface IOrdersView {
        void initViews();

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayRecyclerView(List<OrdersModel> orderList);

    }

    interface IOrdersPresenter {
        /**
         * Handles the click events on the upper menu bar on the screen and displays the appropriate products on each click
         * ------------------------------------------
         */
        void onNavigationPendingOrders();

        void onNavigationFinishedOrders();

        void onNavigationCancelledOrders();
        /**
         * --------------------------------------------
         */

        /**
         * Handles the click event on the recyclerView and transfers the product to where the user will put the order
         *
         * ----------------------------------------------
         */
        void onMenuPendingClicked(String order_id);

        void onMenuFinishClicked(String order_id);

        void onMenuCancelClicked(String order_id);

        /**
         * -----------------------------------------------
         *
         */

        /**
         * Redirects a new notification on the service class everytime a new transaction on product is made
         * @param title passes the title of the transaction to the service class
         * @param content passes the content of the transaction to the service class
         */
        void addNotification(String title,String content);

    }

    interface IOrdersService extends IServiceStrictMode {
        /**
         * Fetches the all the orders in database in their corresponding statuses
         */
        List<OrdersModel> getOrdersFromDB(String status) throws ClassNotFoundException, SQLException;

        List<OrdersModel>getCustomerSpecificOrders(String order_id) throws ClassNotFoundException, SQLException;

        /**
         * Updates the order in the database
         */
        void updateOrderFromDB(String order_id,String status) throws ClassNotFoundException, SQLException;

        /**
         * Inserts a new notification on the database
         */
        void addNotificationInDB(String title,String content) throws ClassNotFoundException, SQLException;
    }
}
