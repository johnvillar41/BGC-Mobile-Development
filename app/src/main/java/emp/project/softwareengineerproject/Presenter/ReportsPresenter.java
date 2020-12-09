package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;

public class ReportsPresenter implements IReports.IReportsPresenter {

    IReports.IReportsView view;
    ReportsModel model;
    IReports.IReportsService service;

    public ReportsPresenter(IReports.IReportsView view) {
        this.view = view;
        this.model = new ReportsModel();
        this.service = new ReportsService();
    }

    @Override
    public void onPageLoad() {

    }

    private class ReportsService implements IReports.IReportsService {
        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

    }

}
