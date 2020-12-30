package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

public interface IReports {
    /**
     * This will be refractored soon still on working progress
     */
    interface IReportsView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayTotals(String total,String average, String ave_Monthly);

        void displayChart(List<String> monthValues);

    }
    interface IReportsPresenter{
        void loadTotals() throws SQLException, ClassNotFoundException;
    }
    interface IReportsService extends IServiceStrictMode{
        int[] computeAverages() throws ClassNotFoundException, SQLException;
    }
}
