package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.InventoryModel;

public interface ISearchInventory {
    interface ISearchInventoryView{
        /**
         * This will display the products from the searched items on the editText
         * @param product_list displays a list of items which has the similar names
         */
        void displayRecyclerView(List<InventoryModel> product_list);

        void displayProgressLoader();

        void hideProgressLoader();
    }
    interface  ISearchInventoryPresenter{
        /**
         * Handles the Search Button Click event then directs the display of product and
         * fethes the items from the service class.
         * @param product_name will direct this product name in the service class to be searched
         *                     then display the corresponding products
         */
        void onSearchItemProduct(String product_name) throws ClassNotFoundException;

        void onCardViewLongClicked(String product_id, String product_name) throws SQLException;
    }
    interface ISearchInventoryService extends IServiceStrictMode {
        /**
         * Fetches all the products from the database which has the similar name from the searched
         * text
         * @param searchedItem Name of the product to be searched in the database
         * @return returns all the products in a list form
         */
        List<InventoryModel>getSearchedProductFromDB(String searchedItem) throws ClassNotFoundException;

        void deleteItem(String product_id, InventoryModel model) throws ClassNotFoundException, SQLException;
    }
}
