package emp.project.softwareengineerproject.Model.Database.Services.SalesService;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.ISales.ISales;

public class SalesService implements ISales.ISalesService {

    private static SalesService SINGLE_INSTANCE = null;

    private SalesService() {
    }

    public static SalesService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new SalesService();
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
