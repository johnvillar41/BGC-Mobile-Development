package emp.project.softwareengineerproject.Interface;

import java.util.ArrayList;

import emp.project.softwareengineerproject.Model.ReportsModel;

public interface IReports {
    interface IReportsView{
        void initViews();

        void displayCharts(ArrayList<ReportsModel> chartList);
    }
    interface IReportsPresenter{
        void onPageLoad();
    }
    interface IReportsService extends IServiceStrictMode{
    }
}
