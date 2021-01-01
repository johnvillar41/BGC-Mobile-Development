package emp.project.softwareengineerproject.Presenter;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Model.UserModel;
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
    public void loadChartValues() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProgressCircle();
                        }
                    });
                    final ReportsModel model = service.getMonthlySales(LoginActivityView.USERNAME_VALUE);
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayChart(model, LoginActivityView.USERNAME_VALUE);
                            view.hideProgressCircle();
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
    public void loadAdministratorValues() {
        final List<String>[] adminList = new List[1];
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
                    adminList[0] = service.getListOfAdministrators();
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayAdministratorList(adminList[0]);
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
    public void loadTotals(final String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final int total = service.computeAverages(username)[0];
                    final int average = service.computeAverages(username)[1];
                    final int totalAveMonthly = service.computeAverages(username)[2];
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
    public void onSpinnerItemClicked(final String username) {
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressCircle_Users();
                    }
                });
                try {
                    final List<UserModel> adminList = service.getAdminsFromDB();
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(adminList);
                            view.hideProgressCircle_Users();
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
}
