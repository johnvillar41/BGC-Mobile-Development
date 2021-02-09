package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;

public class SalesTransactionPresenter implements ISalesTransactions.ISalesTransactionPresenter {

    private ISalesTransactions.ISalesTransactionsView view;
    private ISalesTransactions.ISalesTransactionService service;

    public SalesTransactionPresenter(ISalesTransactions.ISalesTransactionsView view, ISalesTransactions.ISalesTransactionService service) {
        this.view = view;
        this.service = service;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLoadPageDisplay(String date_today) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(date_today);
                    view.displayTransactions(transactionList);
                    view.hideProgressBar();
                } catch (ClassNotFoundException e) {
                    view.hideProgressBar();
                    e.printStackTrace();
                } catch (SQLException e) {
                    view.hideProgressBar();
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
                view.displayProgressBar();
                try {
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(date);
                    view.displayTransactions(transactionList);
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
    public void onShowAllListClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final List<SalesModel> transactionList = service.getTransactionsFromDB();
                    view.displayTransactions(transactionList);
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
    public void onLongCardViewClicked(final String id) {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    service.deleteItem(id);
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
    public void initializeViews() {
        view.initViews();
    }
}
