package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;

public class ReportsService implements IReports.IReportsService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private ReportsModel model;
    private static ReportsService SINGLE_INSTANCE = null;

    private ReportsService(ReportsModel model) {
        this.model = model;
    }

    public static ReportsService getInstance(ReportsModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ReportsService(model);
        }
        return SINGLE_INSTANCE;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }




}
