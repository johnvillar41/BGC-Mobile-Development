package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Model.ReportsModel;

public interface IReports {
    interface IReportsView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayBarChart(ArrayList<ReportsModel> chartList);

        void displayProgressIndicator();

        void hideProgressIndicator();
    }
    interface IReportsPresenter{
        void onPageLoad() throws SQLException, ClassNotFoundException;
    }
    interface IReportsService extends IServiceStrictMode{
        List<ReportsModel> getMonthValuesFromDB() throws SQLException, ClassNotFoundException;


    }
}
