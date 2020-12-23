package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.app.Activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Services.InventoryService.InventoryService;

public class InventoryPresenter extends Activity implements IInvetory.IinventoryPresenter {
    private IInvetory.IinventoryView view;
    private InventoryModel model;
    private IInvetory.IInventoryService service;

    public InventoryPresenter(IInvetory.IinventoryView view) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventoryService(this.model);
    }

    @Override
    public void getGreenHouseFromDB() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showProgressBarRecyclers();
                    }
                });
                final List<InventoryModel> productlistDb[] = new ArrayList[3];
                List<String> productList = new ArrayList<>();
                try {
                    productlistDb[0] = service.getProductFromDB()[0];
                    productlistDb[1] = service.getProductFromDB()[1];
                    productlistDb[2] = service.getProductFromDB()[2];
                    productList = service.getCategoriesFromDB();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final List<String> finalProductList = productList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayRecyclerView(productlistDb);
                        view.displayCategory(finalProductList);
                        view.hideProgressBarReyclers();
                    }
                });

            }

        });
        thread.start();
    }

    @Override
    public void onAddProductButtonClicked() {
        view.goToAddProductPage();
    }

    @Override
    public void searchButtonClicked() {
        view.goToSearchPage();
    }

    @Override
    public void onSwipeRefresh() {
        view.refreshPage();
    }

    @Override
    public void onCardViewLongClicked(String product_id, String product_name) throws SQLException, ClassNotFoundException {
        model.setProduct_name(product_name);
        service.deleteItem(product_id, model);
    }

    @Override
    public void onItemSpinnerSelected(final String selectedItem) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBarRecycler_Others();
                    }
                });
                try {
                    final List<InventoryModel> categrized_items = service.getCategorizedItemsFromDB(selectedItem);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerViewFromCategory(categrized_items);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressBarRecycler_Others();
                    }
                });
            }
        });
        thread.start();

    }


}
