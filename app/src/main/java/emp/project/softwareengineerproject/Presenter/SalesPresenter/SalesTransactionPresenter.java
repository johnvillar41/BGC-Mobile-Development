package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.View.SalesView.SalesTransactionView;

public class SalesTransactionPresenter implements ISalesTransactions.ISalesTransactionPresenter {

    ISalesTransactions.ISalesTransactionsView view;
    SalesModel model;
    ISalesTransactions.ISalesTransactionService service;
    SalesTransactionView context;

    public SalesTransactionPresenter(ISalesTransactions.ISalesTransactionsView view, SalesTransactionView context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesService();
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLoadPageDisplay() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(dtf.format(now));
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
                            view.hideProgressIndicator();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicator();
                        }
                    });
                    e.printStackTrace();
                } catch (SQLException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicator();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onSearchNotificationYesClicked(final String date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getSearchedTransactionListFromDB(date);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
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
    public void onShowAllListClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                    }
                });
                try {
                    final List<SalesModel> transactionList = service.getTransactionsFromDB();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayRecyclerView(transactionList);
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

    private class SalesService implements ISalesTransactions.ISalesTransactionService {

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
        public List<SalesModel> getTransactionsFromDB() throws ClassNotFoundException, SQLException {
            strictMode();
            List<SalesModel> list = new ArrayList<>();
            String sqlGetSalesList = "SELECT * FROM sales_table";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetSalesList);
            while (resultSet.next()) {
                model = new SalesModel(resultSet.getString(1), resultSet.getString(2), resultSet.getBlob(3), resultSet.getLong(4),
                        resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                list.add(model);
            }
            return list;
        }

        @Override
        public List<SalesModel> getSearchedTransactionListFromDB(String date) throws ClassNotFoundException, SQLException {
            strictMode();
            List<SalesModel> list = new ArrayList<>();
            String sqlGetSalesList = "SELECT * FROM sales_table WHERE sales_date=" + "'" + date + "'";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetSalesList);
            while (resultSet.next()) {
                model = new SalesModel(resultSet.getString(1), resultSet.getString(2), resultSet.getBlob(3), resultSet.getLong(4),
                        resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                list.add(model);
            }
            return list;
        }

    }
}
