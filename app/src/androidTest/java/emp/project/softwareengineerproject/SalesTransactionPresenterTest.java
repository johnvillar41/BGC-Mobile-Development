package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesTransactionPresenter;

public class SalesTransactionPresenterTest {
    private static final String MOCK_DATE = "13/1/2020";
    private static final String MOCK_SALES_ID = "123";
    ISalesTransactions.ISalesTransactionsView view;
    ISalesTransactions.ISalesTransactionService service;
    ISalesTransactions.ISalesTransactionPresenter presenter;

    @Before
    public void setUp() {
        view = new MockSalesTransactionView();
        service = new MockSalesTransactionService();
        presenter = new SalesTransactionPresenter(view, service);
    }

    @Test
    public void testDisplayTransactions() throws InterruptedException {
        presenter.onLoadPageDisplay(MOCK_DATE);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesTransactionView) view).isTransactionsDisplayed);
    }

    @Test
    public void testDeleteItem() throws InterruptedException {
        presenter.onLongCardViewClicked(MOCK_SALES_ID);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesTransactionService) service).isProductDeleted);
    }

    @Test
    public void testDisplayProgressIndicator() throws InterruptedException {
        presenter.onLoadPageDisplay(MOCK_DATE);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesTransactionView) view).isProgressIndicatorShowing);
    }

    @Test
    public void testHideProgressIndicator() throws InterruptedException {
        presenter.onLoadPageDisplay(MOCK_DATE);
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesTransactionView) view).isProgressIndicatorNotShowing);
    }

    static class MockSalesTransactionView implements ISalesTransactions.ISalesTransactionsView {
        boolean isTransactionsDisplayed;
        boolean isProgressIndicatorShowing;
        boolean isProgressIndicatorNotShowing;

        @Override
        public void initViews() {

        }

        @Override
        public void displayTransactions(List<SalesModel> transactionList) {
            if (transactionList.size() == 2) {
                isTransactionsDisplayed = true;
            }
        }

        @Override
        public void displayProgressBar() {
            isProgressIndicatorShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressIndicatorNotShowing = true;
        }
    }

    static class MockSalesTransactionService implements ISalesTransactions.ISalesTransactionService {
        boolean isProductDeleted;


        @Override
        public List<SalesModel> getTransactionsFromDB() {
            return Arrays.asList(
                    new SalesModel(),
                    new SalesModel(),
                    new SalesModel());
        }

        @Override
        public List<SalesModel> getSearchedTransactionListFromDB(String date) {
            List<SalesModel> searchedList = new ArrayList<>();
            List<SalesModel> MOCK_DATABASE = new ArrayList<>();

            MOCK_DATABASE.add(new SalesModel(null, null, null, 0, null, null, MOCK_DATE, null));
            MOCK_DATABASE.add(new SalesModel(null, null, null, 0, null, null, null, null));
            MOCK_DATABASE.add(new SalesModel(null, null, null, 0, null, null, MOCK_DATE, null));
            for (SalesModel model : MOCK_DATABASE) {
                if (date.equals(model.getSales_date())) {
                    searchedList.add(model);
                }
            }
            return searchedList;
        }

        @Override
        public void deleteItem(String id) {
            List<SalesModel> MOCK_DATABASE = new ArrayList<>();
            MOCK_DATABASE.add(new SalesModel(null, null, null, 0, null, null, MOCK_DATE, null));
            MOCK_DATABASE.add(new SalesModel(MOCK_SALES_ID, null, null, 0, null, null, null, null));
            MOCK_DATABASE.add(new SalesModel(null, null, null, 0, null, null, MOCK_DATE, null));
            for (int i = 0; i < MOCK_DATABASE.size(); i++) {
                if (id.equals(MOCK_DATABASE.get(i).getSales_id())) {
                    MOCK_DATABASE.remove(i);
                }
            }

            if (MOCK_DATABASE.size() == 2) {
                isProductDeleted = true;
            }
        }
    }
}
