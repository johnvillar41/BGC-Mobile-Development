package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesPresenter;

public class SalesPresenterTest {
    ISales.ISalesView view;
    ISales.ISalesService service;
    ISales.ISalesPresenter presenter;

    @Before
    public void setUp() {
        view = new MockSalesView();
        service = new MockSalesService();
        presenter = new SalesPresenter(view, service);
    }

    @Test
    public void testLoadTotalBalance() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoadPage();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesView)view).isBalanceDisplayed);
    }
    @Test
    public void testShowProgressIndicator() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoadPage();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesView)view).isProgressIndicatorDisplayed);
    }

    @Test
    public void testHideProgressIndicator() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoadPage();
        Thread.sleep(1000);
        Assert.assertTrue(((MockSalesView)view).isProgressIndicatorHidden);
    }

    @Test
    public void testGoToSaleActivity() {
        presenter.onCreateSaleClicked();
        Assert.assertTrue(((MockSalesView)view).isSalesActivityDisplayed);
    }
    @Test
    public void testGoToTransactionActivity() {
        presenter.onViewTransactionClicked();
        Assert.assertTrue(((MockSalesView)view).isTransactionActivityDisplayed);
    }



    static class MockSalesView implements ISales.ISalesView {

        boolean isBalanceDisplayed;
        boolean isProgressIndicatorDisplayed;
        boolean isProgressIndicatorHidden;
        boolean isSalesActivityDisplayed;
        boolean isTransactionActivityDisplayed;

        @Override
        public void initViews() {

        }

        @Override
        public void displayTotalBalance(String totalBalance) {
            if(totalBalance.equals(String.valueOf(1000))){
                isBalanceDisplayed = true;
            }
        }

        @Override
        public void goToSaleActivity() {
            isSalesActivityDisplayed = true;
        }

        @Override
        public void goToTransActionActivity() {
            isTransactionActivityDisplayed = true;
        }

        @Override
        public void displayProgressIndicator() {
            isProgressIndicatorDisplayed = true;
        }

        @Override
        public void hideProgressIndicator() {
            isProgressIndicatorHidden = true;
        }
    }

    static class MockSalesService implements ISales.ISalesService {

        @Override
        public long getTotalTransactionsFromDB() {
            return 1000;
        }
    }
}
