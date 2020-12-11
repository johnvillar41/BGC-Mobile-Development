package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Services.SalesService.SalesTransactionService;
import emp.project.softwareengineerproject.View.SalesView.SalesTransactionView;

public class SalesTransactionPresenter implements ISalesTransactions.ISalesTransactionPresenter {

    ISalesTransactions.ISalesTransactionsView view;
    SalesModel model;
    ISalesTransactions.ISalesTransactionService service;
    SalesTransactionView context;

    public SalesTransactionPresenter(ISalesTransactions.ISalesTransactionsView view, SalesTransactionView context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesTransactionService(this.model);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLoadPageDisplay() {
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
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(dtf.format(now));
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicator();
                        }
                    });
                    e.printStackTrace();
                } catch (SQLException e) {
                    context.runOnUiThread(new Runnable() {
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
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(date);
                    context.runOnUiThread(new Runnable() {
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
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getTransactionsFromDB();
                    context.runOnUiThread(new Runnable() {
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


}
