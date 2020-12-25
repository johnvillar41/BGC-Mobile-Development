package emp.project.softwareengineerproject.Services;

import android.app.Activity;
import android.os.StrictMode;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class MainMenuService extends Activity implements IMainMenu.IMainService {
    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();


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
            int numberOfNotifs = resultSet.getInt(1);
            connection.close();
            statement.close();
            resultSet.close();
            return numberOfNotifs;
        } else {
            connection.close();
            statement.close();
            resultSet.close();
            return 0;
        }
    }

    @Override
    public Blob getProfilePicture() throws ClassNotFoundException, SQLException {
        strictMode();
        Blob profileImage = null;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetProfilePic = "SELECT user_image from login_table WHERE user_username=" + "'" + LoginActivityView.USERNAME_VALUE + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetProfilePic);
        while (resultSet.next()) {
            profileImage = resultSet.getBlob(1);
        }
        connection.close();
        statement.close();
        resultSet.close();
        return profileImage;
    }
}
