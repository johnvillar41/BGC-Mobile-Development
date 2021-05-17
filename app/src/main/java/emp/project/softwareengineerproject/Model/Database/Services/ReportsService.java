package emp.project.softwareengineerproject.Model.Database.Services;

import emp.project.softwareengineerproject.Interface.IReports;

public class ReportsService implements IReports.IReportsService {

    private static ReportsService SINGLE_INSTANCE = null;

    private ReportsService() {

    }

    public static ReportsService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ReportsService();
        }
        return SINGLE_INSTANCE;
    }

}
