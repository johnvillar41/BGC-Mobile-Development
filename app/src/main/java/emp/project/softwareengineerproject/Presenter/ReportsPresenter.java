package emp.project.softwareengineerproject.Presenter;

import java.lang.ref.WeakReference;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Services.ReportsService;
import emp.project.softwareengineerproject.View.ReportsView.ReportsActivityView;

public class ReportsPresenter implements IReports.IReportsPresenter {

    private IReports.IReportsView view;
    private ReportsModel model;
    private IReports.IReportsService service;
    private WeakReference<ReportsActivityView> context;

    public ReportsPresenter(IReports.IReportsView view, ReportsActivityView context) {
        this.view = view;
        this.model = new ReportsModel();
        this.service = ReportsService.getInstance(this.model);
        this.context = new WeakReference<>(context);
    }




}
