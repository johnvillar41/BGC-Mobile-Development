package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;

public interface ISales {
    interface ISalesView extends IBaseView {
        void displayTotalBalance(String totalBalance);

        void goToSaleActivity();

        void goToTransActionActivity();
    }

    interface ISalesPresenter extends IBasePresenter {
        void onCreateSaleClicked();

        void onViewTransactionClicked();

        void onLoadPage();
    }

    interface ISalesService extends IServiceStrictMode {
        long getTotalTransactionsFromDB() throws ClassNotFoundException, SQLException;
    }
}
