package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;

public interface IReports {
    /**
     * This will be refractored soon still on working progress
     */
    interface IReportsView{
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProgressIndicator();

        void hideProgressIndicator();

    }
    interface IReportsPresenter{

    }
    interface IReportsService extends IServiceStrictMode{

    }
}
