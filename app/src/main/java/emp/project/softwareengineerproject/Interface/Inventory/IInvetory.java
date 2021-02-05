package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public interface IInvetory {
    interface IinventoryView {
        /**
         * Initializes all the objects from the xml in here
         */
        void initViews() throws SQLException, ClassNotFoundException, InterruptedException;

        /**
         * This will display the list of products into the recyclerView
         *
         * @param productList is a list of all the products agt company has in the database
         */
        void displayRecyclerView(List<InventoryModel>[] productList);

        /**
         * Displays InventoryProductActivityView
         */
        void goToAddProductPage();

        /**
         * Displays Circular Progress Loaders
         */
        void showProgressBarRecyclers();

        /**
         * Hides Circular Progress Loaders
         */
        void hideProgressBarReyclers();

        /**
         * Display InventorySearchItemView Activity
         */
        void goToSearchPage();

        /**
         * Refreshes the whole activity
         */
        void refreshPage();

        /**
         * Will display list of categories
         *
         * @param categories are passed here as parameters to be displayed in the spinner
         *                   to be displayed
         */
        void displayCategory(List<String> categories);

        /**
         * Will display the product list based on a specific category
         *
         * @param list of products in which id displayed per category
         */
        void displayRecyclerViewFromCategory(List<InventoryModel> list);

        void displayProgressBarRecycler_Others();

        void hideProgressBarRecycler_Others();

    }

    interface IinventoryPresenter {
        /**
         * Redirects and commands the getProductsFromDB function from service to be displayed on
         * their own recycler views
         */
        void loadData() throws InterruptedException, SQLException, ClassNotFoundException;

        /**
         * Handles the click event of add button and redirects into InventoryUpdateActivity
         */
        void onAddProductButtonClicked();

        /**
         * Handles the click event of search button and redirects into InventorySearchItemView
         */
        void searchButtonClicked();

        /**
         * Refreshes the whole activity when swiped
         */
        void onSwipeRefresh();

        /**
         * This will delete the product by clicking the specific item long clciked
         *
         * @param product_id   gets the product id to be passed on the service class
         * @param product_name gets the name of the product to be inserted in the notfications
         *                     table in the database
         */
        void onCardViewLongClicked(String product_id, String product_name) throws SQLException, ClassNotFoundException;

        /**
         * Handled the spinner click event
         *
         * @param selectedItem gets the selectedItem from the spinner
         */
        void onItemSpinnerSelected(String selectedItem) throws SQLException, ClassNotFoundException;
    }

    interface IInventoryService extends IServiceStrictMode {
        /**
         * @return returns all the products from the database
         */
        List<InventoryModel>[] getProductFromDB() throws ClassNotFoundException, SQLException, InterruptedException;

        /**
         * Deletes item from the database
         *
         * @param product_id Item will be deleted from the database using the product_id parameter
         * @param model      only gets the product name, this should be changed to a string not an object
         */
        void deleteItem(String product_id, InventoryModel model) throws ClassNotFoundException, SQLException;

        /**
         * @return fetches categories from the database.
         */
        List<String> getCategoriesFromDB() throws ClassNotFoundException, SQLException;

        /**
         * This will fetch the categorized items from the database
         *
         * @param category will fetch the items based on its category
         * @return will then return the products from the database
         */
        List<InventoryModel> getCategorizedItemsFromDB(String category) throws ClassNotFoundException, SQLException;
    }
}
