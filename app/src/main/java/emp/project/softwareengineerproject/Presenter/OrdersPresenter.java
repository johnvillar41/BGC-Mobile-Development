package emp.project.softwareengineerproject.Presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;

public class OrdersPresenter implements IOrders.IOrdersPresenter {

    private IOrders.IOrdersView view;
    private OrdersModel model;
    private IOrders.IOrdersService service;

    public OrdersPresenter(IOrders.IOrdersView view, IOrders.IOrdersService service) {
        this.view = view;
        this.model = new OrdersModel();
        this.service = service;
    }

    @Override
    public void onNavigationPendingOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.PENDING.getStatus());
                    view.displayRecyclerView(ordersList);
                    view.hideProgressBar();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    @Override
    public void onNavigationFinishedOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.FINISHED.getStatus());
                    view.displayRecyclerView(ordersList);
                    view.hideProgressBar();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    @Override
    public void onNavigationCancelledOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.CANCELLED.getStatus());
                    view.displayRecyclerView(ordersList);
                    view.hideProgressBar();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    @Override
    public void onMenuPendingClicked(final String order_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    service.updateOrderFromDB(order_id, STATUS.PENDING.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });
        thread.start();

    }

    @Override
    public void onMenuFinishClicked(final String order_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    service.updateOrderFromDB(order_id, STATUS.FINISHED.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });
        thread.start();
    }

    @Override
    public void onMenuCancelClicked(final String order_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    service.updateOrderFromDB(order_id, STATUS.CANCELLED.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });
        thread.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNotification(String title, String content) {
        try {
            service.addNotificationInDB(title, content);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeViews() {
        view.initViews();
    }

    public enum STATUS {
        PENDING("Processing"),
        //TODO: STATUS: READY FOR PICKUP
        CANCELLED("Cancelled"),

        FINISHED("Finished");

        private String status;

        STATUS(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
