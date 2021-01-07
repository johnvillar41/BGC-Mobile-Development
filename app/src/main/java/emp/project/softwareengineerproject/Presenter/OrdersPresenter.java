package emp.project.softwareengineerproject.Presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Model.Database.Services.OrdersService;

public class OrdersPresenter implements IOrders.IOrdersPresenter {

    private IOrders.IOrdersView view;
    private OrdersModel model;
    private WeakReference<Context> context;
    private OrdersService service;

    public OrdersPresenter(IOrders.IOrdersView view, Context context) {
        this.view = view;
        this.model = new OrdersModel();
        this.context = new WeakReference<>(context);
        this.service = OrdersService.getInstance(this.model);
    }

    @Override
    public void onNavigationPendingOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });

                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.PENDING.getStatus());
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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
        });
        thread.start();
    }

    @Override
    public void onNavigationFinishedOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });

                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.FINISHED.getStatus());
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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
        });
        thread.start();
    }

    @Override
    public void onNavigationCancelledOrders() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });

                try {
                    final List<OrdersModel> ordersList = service.getOrdersFromDB(STATUS.CANCELLED.getStatus());
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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
        });
        thread.start();
    }

    @Override
    public void onMenuPendingClicked(final String order_id) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    service.updateOrderFromDB(order_id,STATUS.PENDING.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();

                    }
                });
            }
        });thread.start();

    }

    @Override
    public void onMenuFinishClicked(final String order_id) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    service.updateOrderFromDB(order_id,STATUS.FINISHED.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();
                    }
                });
            }
        });thread.start();
    }

    @Override
    public void onMenuCancelClicked(final String order_id) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    service.updateOrderFromDB(order_id,STATUS.CANCELLED.getStatus());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();
                    }
                });
            }
        });thread.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addNotification(String title,String content) {
        try {
            service.addNotificationInDB(title,content);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private enum STATUS {
        PENDING("Processing"),
        CANCELLED("Cancelled"),
        FINISHED("Finished");

        private String status;

        STATUS(String status) {
            this.status = status;
        }

        private String getStatus() {
            return status;
        }
    }
}
