package emp.project.softwareengineerproject;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesAddPresenter;

public class SalesAddPresenterTest {
    private static final List<InventoryModel> MOCK_INVENTORY = new ArrayList<>();
    private static final String PRODUCT_ID = "666";
    private static final String TOTAL_ORDERS_ENOUGH = "1";
    private static final String TOTAL_ORDERS_NOT_ENOUGH = "100";
    private static final int MOCK_TOTAL_PRODUCTS = 2;
    ISalesAdd.ISalesAddView view;
    ISalesAdd.ISalesAddService service;
    ISalesAdd.ISalesAddPresenter presenter;

    @Before
    public void setUp() {
        view = new MockSalesAddView();
        service = new MockAddSalesService();
        presenter = new SalesAddPresenter(view, service);

        MOCK_INVENTORY.add(new InventoryModel(null, null, 0, 0, null, null));
        MOCK_INVENTORY.add(new InventoryModel(null, null, 0, 0, null, null));
        MOCK_INVENTORY.add(new InventoryModel(null, null, 0, 0, null, null));
    }

    @Test
    public void testDisplayCartValues() throws InterruptedException {
        presenter.onCartButtonClicked(MOCK_INVENTORY);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isCartDisplayed);
    }

    @Test
    public void testDisplayProducts() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadProductList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isProductsDisplayed);
    }

    @Test
    public void testDisplaySuccessFullPromt() throws InterruptedException {
        presenter.onConfirmButtonClicked(null);
        Thread.sleep(1000);
        //TODO:FIX THIS TEST
        Assert.assertTrue(((MockSalesAddView) view).isPromptDisplayed);
    }

    @Test
    public void testConfirmOrderOnEnoughProductsMessage() throws InterruptedException {
        presenter.onConfirmButtonClicked(null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddSalesService) service).checkIfProductIsEnough(PRODUCT_ID, TOTAL_ORDERS_ENOUGH));
    }

    @Test
    public void testConfirmOrderOnNotEnoughProductsMessage() throws InterruptedException {
        presenter.onConfirmButtonClicked(null);
        Thread.sleep(1000);
        Assert.assertFalse(((MockAddSalesService) service).checkIfProductIsEnough(PRODUCT_ID, TOTAL_ORDERS_NOT_ENOUGH));
    }

    @Test
    public void testShowProgressIndicator() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadProductList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isProgressIndicatorShowing);
    }

    @Test
    public void testHideProgressIndicator() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadProductList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isProgressIndicatorNotShowing);
    }

    @Test
    public void testDisplayProgressIndicatorCartShowing() throws InterruptedException {
        presenter.onConfirmButtonClicked(null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isProgressIndicatorCartShowing);
    }

    @Test
    public void testDisplayProgressIndicatorCartNotShowing() throws InterruptedException {
        presenter.onConfirmButtonClicked(null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesAddView) view).isProgressIndicatorCartNotShowing);
    }

    static class MockSalesAddView implements ISalesAdd.ISalesAddView {
        boolean isCartDisplayed;
        boolean isProductsDisplayed;
        boolean isPromptDisplayed;
        boolean isProgressIndicatorShowing;
        boolean isProgressIndicatorNotShowing;
        boolean isProgressIndicatorCartShowing;
        boolean isProgressIndicatorCartNotShowing;

        @Override
        public void initViews() {

        }

        @Override
        public void displayCart(List<InventoryModel> cartList) {
            if (cartList.size() == 3) {
                isCartDisplayed = true;
            }
        }

        @Override
        public void displayProducts(List<InventoryModel> list) {
            if (list.size() == 1) {
                isProductsDisplayed = true;
            }
        }

        @Override
        public void displaySuccessfullPrompt() {
            isPromptDisplayed = true;
        }

        @Override
        public void displayOnErrorMessage(String message, View v) {

        }

        @Override
        public void displayProgressIndicator() {
            isProgressIndicatorShowing = true;
        }

        @Override
        public void hideProgressIndicator() {
            isProgressIndicatorNotShowing = true;
        }

        @Override
        public void displayProgressIndicatorCart() {
            isProgressIndicatorCartShowing = true;
        }

        @Override
        public void hideProgressIndicatorCart() {
            isProgressIndicatorCartNotShowing = true;
        }
    }

    static class MockAddSalesService implements ISalesAdd.ISalesAddService {
        private List<InventoryModel> MOCK_DATABASE = new ArrayList<>();
        boolean isProductEnough = false;

        @Override
        public boolean insertOrderToDB(SalesModel model) {
            return false;
        }

        @Override
        public List<InventoryModel> getProductListFromDB() {
            MOCK_DATABASE.add(new InventoryModel(null, null, 0, 0, null, null));
            return MOCK_DATABASE;
        }

        @Override
        public boolean checkIfProductIsEnough(String product_id, String total_orders) {

            MOCK_DATABASE.add(new InventoryModel("null", "null", "null", 0, 0, null, "null"));
            MOCK_DATABASE.add(new InventoryModel("null", "null", "null", 0, 0, null, "null"));
            MOCK_DATABASE.add(new InventoryModel(PRODUCT_ID, "null", "null", 0, MOCK_TOTAL_PRODUCTS, null, "null"));

            for (int i = 0; i < MOCK_DATABASE.size(); i++) {
                if (product_id.equals(MOCK_DATABASE.get(i).getProduct_id())) {
                    if (MOCK_DATABASE.get(i).getProduct_stocks() > Integer.parseInt(total_orders)) {
                        isProductEnough = true;
                    } else {
                        isProductEnough = false;
                    }
                }
            }
            return isProductEnough;
        }
    }
}
