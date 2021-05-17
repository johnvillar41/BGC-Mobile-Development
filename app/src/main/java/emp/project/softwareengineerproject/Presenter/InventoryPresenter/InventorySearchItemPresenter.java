package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public class InventorySearchItemPresenter implements ISearchInventory.ISearchInventoryPresenter {
    private ISearchInventory.ISearchInventoryView view;
    private ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view, ISearchInventory.ISearchInventoryService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onSearchItemProduct(final String product_name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();

                List<InventoryModel> searchedlist = null;
                try {
                    searchedlist = service.getSearchedProductFromDB(product_name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                final List<InventoryModel> finalSearchedlist = searchedlist;

                view.displayRecyclerView(finalSearchedlist);
                view.hideProgressBar();

            }
        });
        thread.start();
        thread.interrupt();
    }

    @Override
    public void onCardViewLongClicked(int product_id, String product_name) {
        InventoryModel model = new InventoryModel();
        model.setProductName(product_name);
        try {
            service.deleteItem(product_id, model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
