package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Services.InventoryService.InventorySearchItemService;

public class InventorySearchItemPresenter implements ISearchInventory.ISearchInventoryPresenter {
    ISearchInventory.ISearchInventoryView view;
    InventoryModel model;
    ISearchInventory.ISearchInventoryService service;

    public InventorySearchItemPresenter(ISearchInventory.ISearchInventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventorySearchItemService(this.model);
    }

    @Override
    public void onSearchItemProduct(String product_name) throws ClassNotFoundException {
        view.displayRecyclerView(service.getSearchedProductFromDB(product_name));
    }


}
