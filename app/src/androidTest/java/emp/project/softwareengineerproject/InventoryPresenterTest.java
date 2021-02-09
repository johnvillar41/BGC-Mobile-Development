package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;

public class InventoryPresenterTest {
    private static final String MOCK_ID = "666";
    private static final String MOCK_NAME = "sample";
    private static final String MOCK_CATEGORY = "Mock Category";

    IInvetory.IinventoryView view;
    IInvetory.IInventoryService service;
    IInvetory.IinventoryPresenter presenter;

    @Before
    public void setUp() {
        view = new MockInventoryView();
        service = new MockInventoryService();
        presenter = new InventoryPresenter(view, service);
    }

    @Test
    public void testGoToAddProductPage() {
        presenter.onAddProductButtonClicked();
        Assert.assertTrue(((MockInventoryView)view).goToProductPage);
    }

    @Test
    public void testDeleteProduct() throws SQLException, ClassNotFoundException {
        presenter.onCardViewLongClicked(MOCK_ID, MOCK_NAME);
        Assert.assertTrue(MockInventoryService.isItemDeleted);
    }

    @Test
    public void testRefresh() {
        presenter.onSwipeRefresh();
        Assert.assertTrue(((MockInventoryView)view).isRefreshing);
    }

    @Test
    public void testProgressBarOthersShowing() throws InterruptedException, SQLException, ClassNotFoundException {
        presenter.onItemSpinnerSelected(MOCK_CATEGORY);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventoryView)view).isProgressBarOthersShowing);
    }

    @Test
    public void testProgressBarOthersNotShowing() throws InterruptedException, SQLException, ClassNotFoundException {
        presenter.onItemSpinnerSelected(MOCK_CATEGORY);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventoryView)view).isProgressBarOthersHidden);
    }

    @Test
    public void testProductsFromCategories() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onItemSpinnerSelected(MOCK_CATEGORY);
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventoryView)view).areCategorizedItemsDisplayed);
    }

    static class MockInventoryView implements IInvetory.IinventoryView {

        boolean goToProductPage;
        boolean isProductDisplaying;
        boolean isProgressBarRecyclersShowing;
        boolean goToSearchPage;
        boolean isRefreshing;
        boolean areCategoriesDisplayed;
        boolean areCategorizedItemsDisplayed;
        boolean isProgressBarOthersShowing;
        boolean isProgressBarOthersHidden;

        @Override
        public void initViews() {

        }

        @Override
        public void displayRecyclerView(List<InventoryModel>[] productList) {
            if (productList[0].size() == 3
                    && productList[1].size() == 3
                    && productList[2].size() == 3) {
                isProductDisplaying = true;
            } else {
                isProductDisplaying = false;
            }
        }

        @Override
        public void goToAddProductPage() {
            goToProductPage = true;
        }

        @Override
        public void displayProgressBar() {
            isProgressBarRecyclersShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressBarRecyclersShowing = false;
        }

        @Override
        public void goToSearchPage() {
            goToSearchPage = true;
        }

        @Override
        public void refreshPage() {
            isRefreshing = true;
        }

        @Override
        public void displayCategory(List<String> categories) {
            if (categories.size() == 3) {
                areCategoriesDisplayed = true;
            } else {
                areCategoriesDisplayed = false;
            }
        }

        @Override
        public void displayRecyclerViewFromCategory(List<InventoryModel> list) {
            if (list.size() == 3) {
                areCategorizedItemsDisplayed = true;
            } else {
                areCategorizedItemsDisplayed = false;
            }
        }

        @Override
        public void displayProgressBarRecycler_Others() {
            isProgressBarOthersShowing = true;
        }

        @Override
        public void hideProgressBarRecycler_Others() {
            isProgressBarOthersHidden = true;
        }
    }

    static class MockInventoryService implements IInvetory.IInventoryService {

        static boolean isItemDeleted;

        @Override
        public List<InventoryModel>[] getProductFromDB() {
            List<InventoryModel>[] mockDatabase = new ArrayList[3];

            List<InventoryModel> mockList_1 = new ArrayList<>();
            mockList_1.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_1.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_1.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockDatabase[0] = mockList_1;

            List<InventoryModel> mockList_2 = new ArrayList<>();
            mockList_2.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_2.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_2.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockDatabase[1] = mockList_2;

            List<InventoryModel> mockList_3 = new ArrayList<>();
            mockList_3.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_3.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockList_3.add(new InventoryModel(null, null, null, 0, null, 0, null));
            mockDatabase[2] = mockList_3;

            return mockDatabase;
        }

        @Override
        public void deleteItem(String product_id, InventoryModel model) {
            List<InventoryModel> mockList_1 = new ArrayList<>();
            mockList_1.add(new InventoryModel("123", null, null, 0, null, 0, null));
            mockList_1.add(new InventoryModel(MOCK_ID, null, null, 0, null, 0, null));
            mockList_1.add(new InventoryModel("12322", null, null, 0, null, 0, null));

            for (int i = 0; i < mockList_1.size(); i++) {
                if (product_id.equals(mockList_1.get(i).getProduct_id())) {
                    mockList_1.remove(i);
                    isItemDeleted = true;
                    break;
                } else {
                    isItemDeleted = false;
                }
            }
        }

        @Override
        public List<String> getCategoriesFromDB() {
            List<String> mockCategories = new ArrayList<>();
            mockCategories.add("SAMPLE CATEGORY");
            mockCategories.add("SAMPLE CATEGORY1");
            mockCategories.add("SAMPLE CATEGORY2");
            return mockCategories;
        }

        @Override
        public List<InventoryModel> getCategorizedItemsFromDB(String category) {
            List<InventoryModel> mockList_1 = new ArrayList<>();
            List<InventoryModel> categorizedItems = new ArrayList<>();
            mockList_1.add(new InventoryModel("123", null, null, 0, null, 0, MOCK_CATEGORY));
            mockList_1.add(new InventoryModel(MOCK_ID, null, null, 0, null, 0, MOCK_CATEGORY));
            mockList_1.add(new InventoryModel("12322", null, null, 0, null, 0, MOCK_CATEGORY));
            for (InventoryModel model : mockList_1) {
                if (model.getProduct_category().equals(MOCK_CATEGORY)) {
                    categorizedItems.add(model);
                }
            }
            return categorizedItems;
        }
    }
}
