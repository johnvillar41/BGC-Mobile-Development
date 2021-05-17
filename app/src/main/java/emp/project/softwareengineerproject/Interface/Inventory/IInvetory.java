package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public interface IInvetory {
    interface IinventoryView extends IBaseView {
        void displayRecyclerView(List<InventoryModel>[] productList);

        void goToAddProductPage();

        void goToSearchPage();

        void refreshPage();

        void displayCategory(List<String> categories);

        void displayRecyclerViewFromCategory(List<InventoryModel> list);

        void displayProgressBarRecycler_Others();

        void hideProgressBarRecycler_Others();

    }

    interface IinventoryPresenter extends IBasePresenter {
        void loadData();

        void onAddProductButtonClicked();

        void searchButtonClicked();

        void onSwipeRefresh();

        void onCardViewLongClicked(int product_id, String product_name);

        void onItemSpinnerSelected(String selectedItem);
    }

    interface IInventoryService extends IServiceStrictMode {
        List<InventoryModel>[] getProductFromDB() throws ClassNotFoundException, SQLException, InterruptedException;

        void deleteItem(int product_id, InventoryModel model) throws ClassNotFoundException, SQLException;

        List<String> getCategoriesFromDB() throws ClassNotFoundException, SQLException;

        List<InventoryModel> getCategorizedItemsFromDB(String category) throws ClassNotFoundException, SQLException;

        InventoryModel fetchProductGivenByID(int id) throws ClassNotFoundException, SQLException;
    }
}
