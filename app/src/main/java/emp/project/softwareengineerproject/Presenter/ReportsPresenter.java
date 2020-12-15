package emp.project.softwareengineerproject.Presenter;

import java.sql.SQLException;
import java.util.ArrayList;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Services.ReportsService;
import emp.project.softwareengineerproject.View.ReportsView.ReportsActivityChartView;

public class ReportsPresenter implements IReports.IReportsPresenter {

    IReports.IReportsView view;
    ReportsModel model;
    IReports.IReportsService service;
    ReportsActivityChartView context;

    public ReportsPresenter(IReports.IReportsView view, ReportsActivityChartView context) {
        this.view = view;
        this.model = new ReportsModel();
        this.service = new ReportsService(this.model);
        this.context = context;
    }

    @Override
    public void onPageLoad() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final ArrayList<ReportsModel> valuesList= (ArrayList<ReportsModel>) service.getMonthValuesFromDB();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayBarChart(valuesList);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();
                    }
                });
            }
        });thread.start();

    }



}
