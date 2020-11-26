package emp.project.softwareengineerproject.Interface.Inventory;

import java.util.List;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface ISearchInventory {
    interface ISearchInventoryView{
        void displayRecyclerView(List<ProductModel> product_list);
    }
    interface  ISearchInventoryPresenter{
        void onSearchItemProduct(String product_name) throws ClassNotFoundException;
    }
    interface  ISearchInventoryDBhelper{
        void strictMode() throws ClassNotFoundException;

        List<ProductModel>getSearchedProductFromDB(String searchedItem) throws ClassNotFoundException;
    }
}
