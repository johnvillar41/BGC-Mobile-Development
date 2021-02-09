package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public interface ISearchInventory {
    interface ISearchInventoryView extends IBaseView {
        void displayRecyclerView(List<InventoryModel> product_list);
    }

    interface ISearchInventoryPresenter extends IBasePresenter {
        void onSearchItemProduct(String product_name);

        void onCardViewLongClicked(String product_id, String product_name);
    }

    interface ISearchInventoryService extends IServiceStrictMode {
        List<InventoryModel> getSearchedProductFromDB(String searchedItem) throws ClassNotFoundException;

        void deleteItem(String product_id, InventoryModel model) throws ClassNotFoundException, SQLException;
    }
}
