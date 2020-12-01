package emp.project.softwareengineerproject.Interface.Inventory;

import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.InventoryModel;

public interface ISearchInventory {
    interface ISearchInventoryView{
        void displayRecyclerView(List<InventoryModel> product_list);
    }
    interface  ISearchInventoryPresenter{
        void onSearchItemProduct(String product_name) throws ClassNotFoundException;
    }
    interface ISearchInventoryService extends IServiceStrictMode {

        List<InventoryModel>getSearchedProductFromDB(String searchedItem) throws ClassNotFoundException;
    }
}
