package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.OrdersModel;

public interface IOrders {
    interface IOrdersView {
        void initViews();

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayRecyclerView(List<OrdersModel> orderList);
    }

    interface IOrdersPresenter {
        void onNavigationPendingOrders();

        void onNavigationFinishedOrders();

        void onNavigationCancelledOrders();
    }

    interface IOrdersService extends IServiceStrictMode {
        List<OrdersModel> getOrdersFromDB(String status) throws ClassNotFoundException, SQLException;
    }
}
