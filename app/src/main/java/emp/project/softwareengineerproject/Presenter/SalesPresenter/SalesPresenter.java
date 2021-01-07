package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesService;

public class SalesPresenter implements ISales.ISalesPresenter {

    private ISales.ISalesView view;
    private ISales.ISalesService service;
    private WeakReference<Context> context;

    public SalesPresenter(ISales.ISalesView view, Context context) {
        this.view = view;
        this.service = SalesService.getInstance();
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCreateSaleClicked() {
        view.goToSaleActivity();
    }

    @Override
    public void onViewTransactionClicked() {
        view.goToTransActionActivity();
    }

    @Override
    public void onLoadPage() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String totalTransactions = String.valueOf(service.getTotalTransactionsFromDB());
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
                        public void run() {
                            view.displayProgressIndicator();
                            view.displayTotalBalance(totalTransactions);
                            view.hideProgressIndicator();
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }); thread.start();

    }


}
