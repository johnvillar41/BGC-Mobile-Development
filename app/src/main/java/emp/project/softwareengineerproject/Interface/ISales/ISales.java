package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;

public interface ISales {
    interface ISalesView{
        /**
         * Initializes all the objects on the xml
         */
        void initViews() throws SQLException, ClassNotFoundException;

        /**
         * Displays the total balance money on the SalesActivityView
         * @param totalBalance total money parameters
         */
        void displayTotalBalance(String totalBalance);

        /**
         * Displays the SalesAddActivityView Activity
         */
        void goToSaleActivity();

        /**
         * Displays the SalesTransactionView Activity
         */
        void goToTransActionActivity();

        /**
         * Displays Linear Progress Loader
         */
        void displayProgressIndicator();

        /**
         * Hides Linear Progress Loader
         */
        void hideProgressIndicator();
    }
    interface ISalesPresenter{
        /**
         * Handles the Create Button Event then redirects it into the SalesTransactionView activity
         */
        void onCreateSaleClicked();

        /**
         * Handles the ViewTransaction Button Event then redirects it into the SalesTransactionView
         */
        void onViewTransactionClicked();

        /**
         * Redirects the total amount of transactions
         * */
        void onLoadPage() throws SQLException, ClassNotFoundException;
    }
    interface ISalesService extends IServiceStrictMode {
        /**
         * Fetches and calculates all the transactions made in the database.
         * Then returns the total amount
         */
        long getTotalTransactionsFromDB() throws ClassNotFoundException, SQLException;
    }
}
