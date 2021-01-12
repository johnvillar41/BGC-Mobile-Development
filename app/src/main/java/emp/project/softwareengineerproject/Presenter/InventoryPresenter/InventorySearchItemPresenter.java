package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public class InventorySearchItemPresenter implements ISearchInventory.ISearchInventoryPresenter {
    private ISearchInventory.ISearchInventoryView view;
    private InventoryModel model;
    private ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view, ISearchInventory.ISearchInventoryService service) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = service;
    }

    @Override
    public void onSearchItemProduct(final String product_name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressLoader();

                List<InventoryModel> searchedlist = null;
                try {
                    searchedlist = service.getSearchedProductFromDB(product_name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                final List<InventoryModel> finalSearchedlist = searchedlist;

                view.displayRecyclerView(finalSearchedlist);
                view.hideProgressLoader();

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
