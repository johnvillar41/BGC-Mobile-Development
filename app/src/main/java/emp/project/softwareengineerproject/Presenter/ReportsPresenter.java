package emp.project.softwareengineerproject.Presenter;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Services.ReportsService;
import emp.project.softwareengineerproject.View.LoginActivityView;
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


    @Override
    public void loadTotals(String username) {
        if (username == null) {
            username = LoginActivityView.USERNAME_VALUE;
        }
        final String finalUsername = username;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final int total = service.computeAverages(finalUsername)[0];
                    final int average = service.computeAverages(finalUsername)[1];
                    final int totalAveMonthly = service.computeAverages(finalUsername)[2];
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayTotals(String.valueOf(total), String.valueOf(average), String.valueOf(totalAveMonthly));
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    public void loadChartValues() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final ReportsModel model = service.getMonthlySales(LoginActivityView.USERNAME_VALUE);
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayChart(model, LoginActivityView.USERNAME_VALUE);
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }); thread.start();

    }

    @Override
    public List<String> loadAdministratorValues() throws SQLException, ClassNotFoundException {
        return service.getListOfAdministrators();
    }

    @Override
    public void onMenuButtonClicked(final String username) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProgressIndicator();
                        }
                    });
                    final int total = service.computeAverages(username)[0];
                    final int average = service.computeAverages(username)[1];
                    final int totalAveMonthly = service.computeAverages(username)[2];
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                view.displayTotals(String.valueOf(total), String.valueOf(average), String.valueOf(totalAveMonthly));
                                view.displayChart(service.getMonthlySales(username), username);
                                view.hideProgressIndicator();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    @Override
    public void loadSortedAdministrators() {
        /**TODO: SORT THIS LIST OF ADMINISTRATORS */
        try {
            view.displayRecyclerView(service.getAdminsFromDB());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
