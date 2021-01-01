package emp.project.softwareengineerproject.Services.SalesService;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesTransactionService implements ISalesTransactions.ISalesTransactionService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private SalesModel model;
    private static SalesTransactionService SINGLE_INSTANCE = null;

    private SalesTransactionService(SalesModel model) {
        this.model = model;
    }

    public static SalesTransactionService getInstance(SalesModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new SalesTransactionService(model);
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
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
                    resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            list.add(model);
        }

        connection.close();
        statement.close();
        resultSet.close();
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
                    resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
            list.add(model);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }

    @Override
    public void deleteItem(String id) throws ClassNotFoundException, SQLException {
        strictMode();
        String sqlDelete = "DELETE FROM sales_table WHERE sales_id=" + "'" + id + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        statement.execute(sqlDelete);

        connection.close();
        statement.close();
    }
}
