package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.SalesModel;

public interface ISalesTransactions {
    interface ISalesTransactionsView{
        /**
         * Initializes all the objects in the xml
         */
        void initViews();

        /**
         * Displays all the transaction lists in the activity
         * @param transactionList list of the transactions
         */
        void displayRecyclerView(List<SalesModel>transactionList);

        /**
         * Displays Linear Progress Loader everytime the activity is displayed
         */
        void displayProgressIndicator();

        /**
         * Hides the linear loader after the products is displayed
         */
        void hideProgressIndicator();
    }
    interface ISalesTransactionPresenter{
        /**
         * Redirects all the list of notifications for the day and redirects it to be displayed on the
         * recycler view or list
         */
        void onLoadPageDisplay();

        /**
         * Handles the calendar icon button click then redirects all the list of of transactions on the date specified
         * @param date date to be searched
         */
        void onSearchNotificationYesClicked(String date);

        /**
         * Handles the display all menu item clicked to display all the notifications overall.
         */
        void onShowAllListClicked();
    }
    interface ISalesTransactionService extends IServiceStrictMode {
        /**
         * Will fetch all the transactions item on the database
         * @return returns a list of all the transactions
         */
        List<SalesModel> getTransactionsFromDB() throws ClassNotFoundException, SQLException;

        /**
         * Fetches speficific lists based on the date
         * @param date date to be searched in the database
         * @return returns a list of transactions on a specific date
         */
        List<SalesModel> getSearchedTransactionListFromDB(String date) throws ClassNotFoundException, SQLException;
    }
}
