package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;

/**
 * TODO:
 * ====================================================================================================
 * NOTES (01/09/2021)
 *
 * Employee App
 *
 * - Add: Customer Info (when making Transactions -> Sales, for receipt, transaction records)
 * - Check employee roles (to see if there are changes to be made for database)
 * - Bug: Profile not updated
 * - Prompt user: "Are you sure you want to finish this order?" (in Customer Orders)
 * - If in "Finished Orders", no need to delete (create filtering?)
 *     (my suggestion: default setting is current day?)
 * - Inventory: no deleting records/products (maybe change status to archive records and remove from view, not actually delete)
 * - (from me) Question: are usernames unique?
 * - (current plan) Top Salesperson: sort, add ranking (for company's monitoring purposes)
 *     (question by ma'am rhea) is there a quota for every employee? (ask company)
 * - Add list of product types
 * - Add updates made (in notifications)
 * - Add reports for inventory (changes in number of stocks? popular and unpopular items)
 * - Account management: encrypt password
 *
 * * ALSO: Research Paper title needs to be more specific. Also, app names
 *
 *
 * From Ma'am Rhea:
 * {
 * History of Operations very good
 *
 * Inventory
 *
 * List
 *     Product type
 *
 * Update:
 * - More details
 *
 * Product status
 * - Low stock
 * - Fast Moving
 * - Slow Moving
 *
 * Sales
 *
 * Customer Name
 *
 * Contact Number
 *
 * Transaction No.
 *
 * Account Management
 * - Password mask (encryption)
 * - Level/Role*
 *
 * Orders
 * -No delete*
 * -Filtering
 *
 * Reports
 * -Sales Report
 * -Inventory Report
 *
 * Fix update issues
 * }
 *
 *
 * (BONUS: Software Engineering 2: preferred improved quality of project due to more requirements. Both new and old (SoftEng1) projects allowed)
 */

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
