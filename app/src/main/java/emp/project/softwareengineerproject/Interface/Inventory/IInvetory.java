package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.InventoryModel;

public interface IInvetory {
    interface IinventoryView {
        void initViews() throws SQLException, ClassNotFoundException, InterruptedException;

        void displayRecyclerView(List<InventoryModel>[] productList);

        void goToAddProductPage();

        void showProgressBarRecyclers();

        void hideProgressBarReyclers();

        void goToSearchPage();

        void refreshPage();

        void displayCategory(List<String>categories);

        void displayRecyclerViewFromCategory(List<InventoryModel>list);

    }

    interface IinventoryPresenter {
        void getGreenHouseFromDB() throws InterruptedException, SQLException, ClassNotFoundException;

        void onAddProductButtonClicked();

        void searchButtonClicked();

        void onSwipeRefresh();

        void onCardViewLongClicked(String product_id,String product_name) throws SQLException, ClassNotFoundException;

        void onItemSpinnerSelected(String selectedItem) throws SQLException, ClassNotFoundException;
    }

    interface IInventoryService extends IServiceStrictMode {

        List<InventoryModel>[] getProductFromDB() throws ClassNotFoundException, SQLException, InterruptedException;

        void deleteItem(String product_id,InventoryModel model) throws ClassNotFoundException, SQLException;

        List<String>getCategoriesFromDB() throws ClassNotFoundException, SQLException;

        List<InventoryModel> getCategorizedItemsFromDB(String category) throws ClassNotFoundException, SQLException;
    }
}
