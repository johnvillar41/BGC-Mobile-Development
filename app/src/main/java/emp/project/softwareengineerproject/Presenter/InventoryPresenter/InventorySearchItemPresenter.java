package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.app.Activity;

import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Services.InventoryService.InventorySearchItemService;

public class InventorySearchItemPresenter extends Activity implements ISearchInventory.ISearchInventoryPresenter {
    private ISearchInventory.ISearchInventoryView view;
    private InventoryModel model;
    private ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventorySearchItemService(this.model);
    }

    @Override
    public void onSearchItemProduct(final String product_name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressLoader();
                    }
                });
                List<InventoryModel> searchedlist = null;
                try {
                    searchedlist = service.getSearchedProductFromDB(product_name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                final List<InventoryModel> finalSearchedlist = searchedlist;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayRecyclerView(finalSearchedlist);
                        view.hideProgressLoader();
                    }
                });
            }
        });
        thread.start();

    }


}
