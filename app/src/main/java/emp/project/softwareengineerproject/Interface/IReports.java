package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Model.UserModel;

public interface IReports {
    interface IReportsView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayTotals(String total,String average, String ave_Monthly);

        void displayChart(ReportsModel monthValues,String username);

        void displayRecyclerView(List<UserModel>sortedUserList);
    }
    interface IReportsPresenter{
        void loadTotals(String username) throws SQLException, ClassNotFoundException;

        void loadChartValues();

        List<String> loadAdministratorValues() throws SQLException, ClassNotFoundException;

        void onMenuButtonClicked(String adminName);

        void loadSortedAdministrators();
    }
    interface IReportsService extends IServiceStrictMode{
        int[] computeAverages(String username) throws ClassNotFoundException, SQLException;

        ReportsModel getMonthlySales(String username) throws ClassNotFoundException, SQLException;

        List<String>getListOfAdministrators() throws ClassNotFoundException, SQLException;

        List<UserModel>getAdminsFromDB() throws ClassNotFoundException, SQLException;
    }
}
