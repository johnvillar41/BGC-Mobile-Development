package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IMainMenu;

public class MainMenuService implements IMainMenu.IMainService {
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
    public int getNumberOfNotifications(String date) throws SQLException, ClassNotFoundException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        String sqlGetNumberOfNotifs = "SELECT COUNT(*) FROM notifications_table WHERE notif_date LIKE " + "'" + date + "%'";
        ResultSet resultSet = statement.executeQuery(sqlGetNumberOfNotifs);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }
    }
}
