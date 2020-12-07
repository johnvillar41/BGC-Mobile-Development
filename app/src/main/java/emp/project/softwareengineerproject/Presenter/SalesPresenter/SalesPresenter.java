package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesPresenter implements ISales.ISalesPresenter {

    private ISales.ISalesView view;
    private ISales.ISalesService service;
    private SalesModel model;

    public SalesPresenter(ISales.ISalesView view) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesService();
    }

    @Override
    public void onCreateSaleClicked() {
        view.goToSaleActivity();
    }

    @Override
    public void onViewTransactionClicked() {
        view.goToTransActionActivity();
    }

    @Override
    public void onLoadPage() throws SQLException, ClassNotFoundException {
        view.displayTotalBalance(String.valueOf(service.getTotalTransactionsFromDB()));
    }

    private class SalesService implements ISales.ISalesService {

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
        public List<SalesModel> getAllTransactionFromDB() throws SQLException {
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            List<SalesModel> list = new ArrayList<>();
            String sql = "SELECT * FROM sales_table";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                model = new SalesModel(resultSet.getString(1), resultSet.getBlob(2), resultSet.getString(3),
                        resultSet.getLong(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7));
                list.add(model);
            }
            return list;
        }

        @Override
        public long getTotalTransactionsFromDB() throws ClassNotFoundException, SQLException {
            strictMode();
            long totalTransaction = 0;
            String sqlGetTotalTransaction = "SELECT * FROM sales_table";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetTotalTransaction);
            while (resultSet.next()) {
                totalTransaction += resultSet.getLong(4);
            }
            return totalTransaction;
        }


    }
}
