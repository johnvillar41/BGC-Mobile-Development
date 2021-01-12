package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;

public class OrdersPresenterTest {
    private static final String MOCK_STATUS_PENDING = "PENDING";
    private static final String MOCK_STATUS_CANCELLED = "CANCELLED";
    private static final String MOCK_STATUS_FINISHED = "FINISHED";
    IOrders.IOrdersView view;
    IOrders.IOrdersService service;
    IOrders.IOrdersPresenter presenter;

    @Before
    public void setUp() {
        view = new MockOrdersView();
        service = new MockOrdersService();
        presenter = new OrdersPresenter(view, service);
    }

    @Test
    public void testOrdersDisplayOnPendingOrders() throws InterruptedException {
        presenter.onNavigationPendingOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockOrdersView) view).isOrdersShowingOnPending);
    }

    @Test
    public void testOrdersDisplayOnFinishedOrders() throws InterruptedException {
        presenter.onNavigationPendingOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockOrdersView) view).isOrdersShowingOnFinished);
    }

    @Test
    public void testOrdersDisplayOnCancelledOrders() throws InterruptedException {
        presenter.onNavigationPendingOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockOrdersView) view).isOrdersShowingOnCancelled);
    }

    @Test
    public void testProgressIndicatorShowing() throws InterruptedException {
        presenter.onNavigationPendingOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockOrdersView) view).isProgressIndicatorShowing);
    }

    @Test
    public void testProgressIndicatorNotShowing() throws InterruptedException {
        presenter.onNavigationPendingOrders();
        Thread.sleep(1000);
        Assert.assertTrue(((MockOrdersView) view).isProgressIndicatorNotShowing);
    }


    static class MockOrdersView implements IOrders.IOrdersView {
        boolean isOrdersShowingOnPending;
        boolean isOrdersShowingOnFinished;
        boolean isOrdersShowingOnCancelled;
        boolean isProgressIndicatorShowing;
        boolean isProgressIndicatorNotShowing;
        @Override
        public void initViews() {

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
        public void displayRecyclerView(List<OrdersModel> orderList) {
            int counter_pending = 0;
            int counter_cancelled = 0;
            int counter_finished = 0;
            for (OrdersModel model : orderList) {
                if (model.getOrder_status().equals(MOCK_STATUS_PENDING)) {
                    counter_pending++;
                }
            }
            for (OrdersModel model : orderList) {
                if (model.getOrder_status().equals(MOCK_STATUS_CANCELLED)) {
                    counter_cancelled++;
                }
            }
            for (OrdersModel model : orderList) {
                if (model.getOrder_status().equals(MOCK_STATUS_FINISHED)) {
                    counter_finished++;
                }
            }
            if (counter_pending == 3) {
                isOrdersShowingOnPending = true;
            }
            if (counter_cancelled == 3) {
                isOrdersShowingOnCancelled = true;
            }
            if (counter_finished == 3) {
                isOrdersShowingOnFinished = true;
            }
        }
    }

    static class MockOrdersService implements IOrders.IOrdersService {
        List<OrdersModel> mockDatabase = new ArrayList<>();

        @Override
        public List<OrdersModel> getOrdersFromDB(String status) {

            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_PENDING, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_PENDING, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_PENDING, null));

            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_CANCELLED, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_CANCELLED, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_CANCELLED, null));

            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_FINISHED, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_FINISHED, null));
            mockDatabase.add(new OrdersModel(null, null, null, null, MOCK_STATUS_FINISHED, null));

            List<OrdersModel> mockOrderList = new ArrayList<>();
            switch (status) {
                case MOCK_STATUS_PENDING:
                    for (OrdersModel model : mockDatabase) {
                        if (status.equals(MOCK_STATUS_PENDING)) {
                            mockOrderList.add(model);
                        }
                    }
                    return mockOrderList;
                case MOCK_STATUS_FINISHED:
                    for (OrdersModel model : mockDatabase) {
                        if (status.equals(MOCK_STATUS_FINISHED)) {
                            mockOrderList.add(model);
                        }
                    }
                    return mockOrderList;
                case MOCK_STATUS_CANCELLED:
                    for (OrdersModel model : mockDatabase) {
                        if (status.equals(MOCK_STATUS_CANCELLED)) {
                            mockOrderList.add(model);
                        }
                    }
                    return mockOrderList;
                default:
                    return mockDatabase;

            }
        }

        @Override
        public void updateOrderFromDB(String order_id, String status) {

        }

        @Override
        public void addNotificationInDB(String title, String content) {

        }
    }
}
