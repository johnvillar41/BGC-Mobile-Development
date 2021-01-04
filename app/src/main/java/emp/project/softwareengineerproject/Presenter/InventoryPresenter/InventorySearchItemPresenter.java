package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.app.Activity;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventorySearchItemService;

public class InventorySearchItemPresenter extends Activity implements ISearchInventory.ISearchInventoryPresenter {
    private ISearchInventory.ISearchInventoryView view;
    private InventoryModel model;
    private ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = InventorySearchItemService.getInstance(this.model);
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
        thread.interrupt();
    }

    @Override
    public void onCardViewLongClicked(String product_id, String product_name) {
        model.setProduct_name(product_name);
        try {
            service.deleteItem(product_id, model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
