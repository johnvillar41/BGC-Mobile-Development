package emp.project.softwareengineerproject.Presenter;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.Bean.ReportsModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class ReportsPresenter implements IReports.IReportsPresenter {

    private IReports.IReportsView view;
    private IReports.IReportsService service;

    //TODO:Add more reports

    public ReportsPresenter(IReports.IReportsView view, IReports.IReportsService service) {
        this.view = view;
        this.service = service;
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
