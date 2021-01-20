package emp.project.softwareengineerproject;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter;

import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PICTURE;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PRODUCT_CATEGORY;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PRODUCT_DESCRIPTION;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PRODUCT_NAME;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PRODUCT_PRICE;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.EMPTY_PRODUCT_STOCKS;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.SUCCESSFULL_MESSAGE;
import static emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter.ZERO_VALUE_PRICE;

public class InventoryUpdatePresenterTest {
    private static final InputStream MOCK_PRODUCT_IMAGE = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };
    IUpdateInventory.IUupdateInventoryView view;
    IUpdateInventory.IUpdateInventoryService service;
    IUpdateInventory.IUpdatePresenter presenter;

    @Before
    public void setUp() {
        view = new MockUpdateInventoryView();
        service = new MockInventoryUpdateService();
        presenter = new InventoryUpdatePresenter(view, service);
    }

    @Test
    public void testSetErrorOnEmptyProductName() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                "",
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorOnEmptyProductDisplayed);
    }

    @Test
    public void testSetErrorOnEmptyProductDescription() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                "",
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorOnEmptyDescriptionDisplayed);
    }

    @Test
    public void testSetErrorOnEmptyProductPrice() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                "",
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorOnEmptyPriceDisplayed);
    }

    @Test
    public void testSetErrorOnEmptyProductStocks() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                "",
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorOnEmptyStocksDisplayed);
    }

    @Test
    public void testSetErrorOnEmptyCategoryStocks() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                "",
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorOnEmptyStocksDisplayed);
    }

    @Test
    public void testRemoveAllErrors() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isErrorRemoved);
    }

    @Test
    public void testSuccessAddProduct() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockInventoryUpdateService) service).isProductAdded);
    }

    @Test
    public void testHints() throws SQLException {
        InventoryModel model = new InventoryModel(
                MOCK_PRODUCT.MOCK_PRODUCT_ID.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                Long.parseLong(MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal()),
                Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal()),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal());
        presenter.onPageLoadHints(model);
        Assert.assertTrue(((MockUpdateInventoryView) view).isPageLoadedWithHints);
    }

    @Test
    public void testLoadGallery() {
        presenter.onImageButtonClicked();
        Assert.assertTrue(((MockUpdateInventoryView) view).isGalleryLoaded);
    }

    @Test
    public void testShowSuccessfulMessage() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isSuccessfullMessageShown);
    }

    @Test
    public void testShowEmptyPictureMessage() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                null,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isEmptyPictureMessageShown);
    }

    @Test
    public void testShowZeroPriceMessage() throws InterruptedException {
        presenter.onAddProductButtonClicked(
                MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal(),
                MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal(),
                "0",
                MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal(),
                MOCK_PRODUCT_IMAGE,
                MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal(),
                null
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUpdateInventoryView) view).isZeroPriceMessageShown);
    }

    enum MOCK_PRODUCT {
        MOCK_PRODUCT_ID("1"),
        MOCK_PRODUCT_NAME("MockName"),
        MOCK_PRODUCT_DESCRIPTION("MockDesc"),
        MOCK_PRODUCT_PRICE("100"),
        MOCK_PRODUCT_STOCKS("100"),
        MOCK_PRODUCT_CATEGORY("MockCategory");

        private String val;

        MOCK_PRODUCT(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    static class MockUpdateInventoryView implements IUpdateInventory.IUupdateInventoryView {
        boolean isErrorOnEmptyProductDisplayed;
        boolean isErrorOnEmptyDescriptionDisplayed;
        boolean isErrorOnEmptyPriceDisplayed;
        boolean isErrorOnEmptyStocksDisplayed;
        boolean isErrorOnEmptyCategoryDisplayed;

        boolean isErrorRemoved;
        boolean isPageLoadedWithHints;
        boolean isGalleryLoaded;
        boolean isSuccessfullMessageShown;
        boolean isEmptyPictureMessageShown;
        boolean isZeroPriceMessageShown;

        @Override
        public void initViews() {

        }

        @Override
        public void setHints(InventoryModel model) {
            if (model.getProduct_name().equals(MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal()) &&
                    model.getProduct_category().equals(MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal()) &&
                    model.getProduct_stocks() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal()) &&
                    model.getProduct_price() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal()) &&
                    model.getProduct_description().equals(MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal())) {
                isPageLoadedWithHints = true;
            }
        }

        @Override
        public void goBack() {

        }

        @Override
        public void displayStatusMessage(String message, View v) {
            switch (message) {
                case SUCCESSFULL_MESSAGE:
                    isSuccessfullMessageShown = true;
                    break;
                case EMPTY_PICTURE:
                    isEmptyPictureMessageShown = true;
                    break;
                case ZERO_VALUE_PRICE:
                    isZeroPriceMessageShown = true;
                    break;
            }
        }

        @Override
        public void loadImageFromGallery() {
            isGalleryLoaded = true;
        }

        @Override
        public void showProgressIndicator() {

        }

        @Override
        public void hideProgressIndicator() {

        }

        @Override
        public void showCheckAnimation() {

        }

        @Override
        public void setErrorProductName(String errorMessage) {
            if (errorMessage.equals(EMPTY_PRODUCT_NAME)) {
                isErrorOnEmptyProductDisplayed = true;
            }
        }

        @Override
        public void setErrorProductDescription(String errorMessage) {
            if (errorMessage.equals(EMPTY_PRODUCT_DESCRIPTION)) {
                isErrorOnEmptyDescriptionDisplayed = true;
            }
        }

        @Override
        public void setErrorProductPrice(String errorMessage) {
            if (errorMessage.equals(EMPTY_PRODUCT_PRICE)) {
                isErrorOnEmptyPriceDisplayed = true;
            }
        }

        @Override
        public void setErrorProductStocks(String errorMessage) {
            if (errorMessage.equals(EMPTY_PRODUCT_STOCKS)) {
                isErrorOnEmptyStocksDisplayed = true;
            }
        }

        @Override
        public void setErrorProductCategory(String errorMessage) {
            if (errorMessage.equals(EMPTY_PRODUCT_CATEGORY)) {
                isErrorOnEmptyCategoryDisplayed = true;
            }
        }

        @Override
        public void removeErrorProductName() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorProductDescription() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorProductPrice() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorProductStocks() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorProductCategory() {
            isErrorRemoved = true;
        }

        @Override
        public void displayCategoryList(List<String> categories) {

        }
    }

    static class MockInventoryUpdateService implements IUpdateInventory.IUpdateInventoryService {
        boolean isProductAdded;
        boolean isProductUpdated;

        @Override
        public void updateProductToDB(InventoryModel model) {
            if (model.getProduct_name().equals(MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal()) &&
                    model.getProduct_category().equals(MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal()) &&
                    model.getProduct_stocks() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal()) &&
                    model.getProduct_price() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal()) &&
                    model.getProduct_description().equals(MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal())) {
                isProductUpdated = true;
            }
        }

        @Override
        public void addNewProduct(InventoryModel model) {
            if (model.getProduct_name().equals(MOCK_PRODUCT.MOCK_PRODUCT_NAME.getVal()) &&
                    model.getProduct_category().equals(MOCK_PRODUCT.MOCK_PRODUCT_CATEGORY.getVal()) &&
                    model.getProduct_stocks() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_STOCKS.getVal()) &&
                    model.getProduct_price() == Integer.parseInt(MOCK_PRODUCT.MOCK_PRODUCT_PRICE.getVal()) &&
                    model.getProduct_description().equals(MOCK_PRODUCT.MOCK_PRODUCT_DESCRIPTION.getVal())) {
                isProductAdded = true;
            }
        }

        @Override
        public void addNotifications(Connection connection, String sqlNotification, NotificationModel notificationModel) {

        }

        @Override
        public HashSet<String> getCategories() throws ClassNotFoundException, SQLException {
            //TODO: CREATE TEST FOR THIS FUNCTION
            return null;
        }
    }

}
