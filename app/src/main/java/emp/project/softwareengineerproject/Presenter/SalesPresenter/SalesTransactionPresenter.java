package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Services.SalesService.SalesTransactionService;
import emp.project.softwareengineerproject.View.SalesView.SalesTransactionView;

public class SalesTransactionPresenter implements ISalesTransactions.ISalesTransactionPresenter {

    private ISalesTransactions.ISalesTransactionsView view;
    private SalesModel model;
    private ISalesTransactions.ISalesTransactionService service;
    private WeakReference<SalesTransactionView> context;

    public SalesTransactionPresenter(ISalesTransactions.ISalesTransactionsView view, SalesTransactionView context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = SalesTransactionService.getInstance(this.model);
        this.context = new WeakReference<>(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLoadPageDisplay() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(dtf.format(now));
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicator();
                        }
                    });
                    e.printStackTrace();
                } catch (SQLException e) {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicator();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onSearchNotificationYesClicked(final String date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(date);
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
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
    public void onShowAllListClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getTransactionsFromDB();
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
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
    public void onLongCardViewClicked(final String id) {
        Thread thread=new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    service.deleteItem(id);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();
                    }
                });
            }
        });thread.start();

    }


}
