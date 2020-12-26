package emp.project.softwareengineerproject.Services.SalesService;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesService implements ISales.ISalesService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private SalesModel model;
    private static SalesService SINGLE_INSTANCE = null;

    private SalesService(SalesModel model) {
        this.model = model;
    }

    public static SalesService getInstance(SalesModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new SalesService(model);
        }
        return SINGLE_INSTANCE;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
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

        statement.close();
        connection.close();
        resultSet.close();
        return totalTransaction;
    }


}
