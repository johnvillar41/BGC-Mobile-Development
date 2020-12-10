package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public void onPageLoad() throws SQLException, ClassNotFoundException {
        view.displayCharts((ArrayList<ReportsModel>) service.getMonthValuesFromDB());
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

        @Override
        public List<ReportsModel> getMonthValuesFromDB() throws SQLException, ClassNotFoundException {
            strictMode();
            float total_value_per_month = 0;
            int months = 0;
            List<ReportsModel> reportsList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {

                Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
                Statement statement = connection.createStatement();
                String sql = "SELECT * from sales_table WHERE date_month=" + "'" + i + "'";
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    total_value_per_month += resultSet.getInt(4);
                }
                model = new ReportsModel(months, total_value_per_month);

                months++;

                reportsList.add(model);
                if (true) {
                    total_value_per_month = 0;
                }
            }

            return reportsList;

        }


    }

}
