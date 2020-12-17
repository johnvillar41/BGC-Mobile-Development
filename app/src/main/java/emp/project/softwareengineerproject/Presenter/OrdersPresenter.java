package emp.project.softwareengineerproject.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.Services.OrdersService;
import emp.project.softwareengineerproject.View.OrdersView.OrdersActivityView;

public class OrdersPresenter implements IOrders.IOrdersPresenter {

    private IOrders.IOrdersView view;
    private OrdersModel model;
    private OrdersActivityView context;
    private OrdersService service;

    public OrdersPresenter(IOrders.IOrdersView view, OrdersActivityView context) {
        this.view = view;
        this.model = new OrdersModel();
        this.context = context;
        this.service = new OrdersService(this.model);
    }

    @Override
    public void onPageLoad() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });

                try {
                    final List<OrdersModel> ordersList =  service.getOrdersFromDB();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(ordersList);
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });thread.start();
    }
}
