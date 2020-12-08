package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.SalesModel;

public interface ISalesTransactions {
    interface ISalesTransactionsView{
        void initViews();

        void displayRecyclerView(List<SalesModel>transactionList);
    }
    interface ISalesTransactionPresenter{
        void onLoadPageDisplay();
    }
    interface ISalesTransactionService extends IServiceStrictMode {
        List<SalesModel> getTransactionsFromDB() throws ClassNotFoundException, SQLException;
    }
}
