package emp.project.softwareengineerproject.Services.SalesService;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesTransactionService implements ISalesTransactions.ISalesTransactionService {

    private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
    private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
    private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

    private SalesModel model;

    public SalesTransactionService(SalesModel model) {
        this.model = model;
    }

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
