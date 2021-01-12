package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventorySearchItemPresenter;

public class InventorySearchPresenterTest {
    private static final String MOCK_PRODUCT_NAME = "Mock Product";
    private static final String MOCK_PRODUCT_ID = "666";
    ISearchInventory.ISearchInventoryView view;
    ISearchInventory.ISearchInventoryService service;
    ISearchInventory.ISearchInventoryPresenter presenter;

    @Before
    public void setUp() {
        view = new MockInventorySearchView();
        service = new MockInventorySearchService();
        presenter = new InventorySearchItemPresenter(view, service);
    }

    @Test
    public void testSearchSuccess() throws ClassNotFoundException, InterruptedException {
        presenter.onSearchItemProduct(MOCK_PRODUCT_NAME);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventorySearchView)view).isProductShowing);
    }

    @Test
    public void testShowProgressLoader() throws InterruptedException, ClassNotFoundException {
        presenter.onSearchItemProduct(MOCK_PRODUCT_NAME);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventorySearchView)view).isProductShowing);
    }

    @Test
    public void testNoShowProgressLoader() throws InterruptedException, ClassNotFoundException {
        presenter.onSearchItemProduct(MOCK_PRODUCT_NAME);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventorySearchView)view).isProgressLoaderNotShowing);
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        presenter.onCardViewLongClicked(MOCK_PRODUCT_ID, MOCK_PRODUCT_NAME);
        Assert.assertTrue(MockInventorySearchService.isProductDeleted);
    }

    static class MockInventorySearchView implements ISearchInventory.ISearchInventoryView {
        boolean isProductShowing;
        boolean isProgressLoaderShowing;
        boolean isProgressLoaderNotShowing;

        @Override
        public void displayRecyclerView(List<InventoryModel> product_list) {
            if (product_list.size() == 3) {
                isProductShowing = true;
            } else {
                isProductShowing = false;
            }
        }

        @Override
        public void displayProgressLoader() {
            isProgressLoaderShowing = true;
        }

        @Override
        public void hideProgressLoader() {
            isProgressLoaderNotShowing = true;
        }
    }

    static class MockInventorySearchService implements ISearchInventory.ISearchInventoryService {
        static boolean isProductDeleted;

        @Override
        public List<InventoryModel> getSearchedProductFromDB(String searchedItem) {
            List<InventoryModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new InventoryModel(MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            mockDatabase.add(new InventoryModel(MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            mockDatabase.add(new InventoryModel(MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            return mockDatabase;
        }

        @Override
        public void deleteItem(String product_id, InventoryModel model) {
            List<InventoryModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new InventoryModel(MOCK_PRODUCT_ID, MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            mockDatabase.add(new InventoryModel("123", MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            mockDatabase.add(new InventoryModel("324", MOCK_PRODUCT_NAME, null, 0, 0, null, null));
            for (int i = 0; i < mockDatabase.size(); i++) {
                if (mockDatabase.get(i).getProduct_id().equals(product_id)) {
                    mockDatabase.remove(i);
                }
            }
            if (mockDatabase.size() == 2) {
                isProductDeleted = true;
            } else {
                isProductDeleted = false;
            }
        }
    }

}
