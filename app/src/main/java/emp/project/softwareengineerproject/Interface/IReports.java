package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.ReportsModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface IReports {
    interface IReportsView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayProgressCircle();

        void hideProgressCircle();

        void displayProgressCircle_Users();

        void hideProgressCircle_Users();

        void displayTotals(String total,String average, String ave_Monthly);

        void displayChart(ReportsModel monthValues,String username);

        void displayRecyclerView(List<UserModel>sortedUserList);

        void displayAdministratorList(List<String>adminList);
    }
    interface IReportsPresenter{
        void loadTotals(String username) throws SQLException, ClassNotFoundException;

        void loadChartValues();

        void loadAdministratorValues();

        void onSpinnerItemClicked(String adminName);

        void loadSortedAdministrators();
    }
    interface IReportsService extends IServiceStrictMode{
        int[] computeAverages(String username) throws ClassNotFoundException, SQLException;

        ReportsModel getMonthlySales(String username) throws ClassNotFoundException, SQLException;

        List<String>getListOfAdministrators() throws ClassNotFoundException, SQLException;

        List<UserModel>getAdminsFromDB() throws ClassNotFoundException, SQLException;
    }
}
