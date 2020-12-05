package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.SalesModel;

public interface ISales {
    interface ISalesView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayTotalBalance(String totalBalance);

        void goToSaleActivity();

        void goToTransActionActivity();
    }
    interface ISalesPresenter{
        void onCreateSaleClicked();

        void onViewTransactionClicked();

        void onLoadPage() throws SQLException, ClassNotFoundException;
    }
    interface ISalesService extends IServiceStrictMode {
        List<SalesModel> getAllTransactionFromDB() throws SQLException;

        long getTotalTransactionsFromDB() throws ClassNotFoundException, SQLException;
    }
}
