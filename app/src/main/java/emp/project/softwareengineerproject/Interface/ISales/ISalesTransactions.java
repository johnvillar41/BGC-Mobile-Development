package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;

public interface ISalesTransactions {
    interface ISalesTransactionsView extends IBaseView {
        void displayTransactions(List<SalesModel> transactionList);
    }

    interface ISalesTransactionPresenter extends IBasePresenter {
        void onLoadPageDisplay(String date_today);

        void onSearchNotificationYesClicked(String date);

        void onShowAllListClicked();

        void onLongCardViewClicked(String id);
    }

    interface ISalesTransactionService extends IServiceStrictMode {
        List<SalesModel> getTransactionsFromDB() throws ClassNotFoundException, SQLException;

        List<SalesModel> getSearchedTransactionListFromDB(String date) throws ClassNotFoundException, SQLException;

        void deleteItem(String id) throws ClassNotFoundException, SQLException;
    }
}
