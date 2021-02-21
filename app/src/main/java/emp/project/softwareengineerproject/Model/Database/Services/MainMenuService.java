package emp.project.softwareengineerproject.Model.Database.Services;

import android.app.Activity;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class MainMenuService extends Activity implements IMainMenu.IMainService {

    private static MainMenuService SINGLE_INSTANCE = null;

    private MainMenuService() {

    }

    public static MainMenuService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new MainMenuService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
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

    @Override
    public Integer getNumberOfInformation() throws ClassNotFoundException, SQLException {
        strictMode();
        int totalVal = 0;
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetTotalNumberInfo = "SELECT COUNT(*) FROM information_table WHERE product_information = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlGetTotalNumberInfo);
        preparedStatement.setString(1, "No Information yet");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            totalVal = resultSet.getInt(1);
        }
        preparedStatement.close();
        connection.close();
        resultSet.close();
        return totalVal;
    }
}
