package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Services.SalesService.SalesService;
import emp.project.softwareengineerproject.View.SalesView.SalesActivityView;

public class SalesPresenter implements ISales.ISalesPresenter {

    private ISales.ISalesView view;
    private ISales.ISalesService service;
    private SalesModel model;
    private WeakReference<SalesActivityView> context;

    public SalesPresenter(ISales.ISalesView view, SalesActivityView context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = SalesService.getInstance(this.model);
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
                    context.get().runOnUiThread(new Runnable() {
                        @Override
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
