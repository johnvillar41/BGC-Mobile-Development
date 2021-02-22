package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.View.OrdersView.OrdersRecyclerView;

public interface IOrders {
    interface IOrdersView extends IBaseView {
        void displayRecyclerView(List<OrdersModel> orderList);
    }

    interface IOrdersPresenter extends IBasePresenter{
        void onNavigationPendingOrders();

        void onNavigationFinishedOrders();

        void onNavigationCancelledOrders();

        void onMenuPendingClicked(String order_id);

        void onMenuFinishClicked(String order_id);

        void onMenuCancelClicked(String order_id);

        void addNotification(OrdersRecyclerView.STATUS status, String content);

    }

    interface IOrdersService extends IServiceStrictMode {
        List<OrdersModel> getOrdersFromDB(String status) throws ClassNotFoundException, SQLException;

        void updateOrderFromDB(String order_id, String status) throws ClassNotFoundException, SQLException;

        void addNotificationInDB(OrdersRecyclerView.STATUS status, String content) throws ClassNotFoundException, SQLException;
    }
}
