package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ISales.ISales;

public class SalesPresenter implements ISales.ISalesPresenter {

    private ISales.ISalesView view;
    private ISales.ISalesService service;

    public SalesPresenter(ISales.ISalesView view, ISales.ISalesService service) {
        this.view = view;
        this.service = service;
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
                    view.displayProgressIndicator();
                    view.displayTotalBalance(totalTransactions);
                    view.hideProgressIndicator();
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
