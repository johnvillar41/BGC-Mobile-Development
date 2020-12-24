package emp.project.softwareengineerproject.Services.UsersService;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class UsersService implements IUsers.IUsersService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private UserModel model;

    public UsersService(UserModel model) {
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
    public UserModel getUserProfileFromDB(String user_username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetUserProfile = "SELECT * FROM login_table WHERE user_username=" + "'" + user_username + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetUserProfile);
        if (resultSet.next()) {
            return new UserModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getBlob(5));
        } else {
            return null;
        }
    }

    @Override
    public List<UserModel> getUsersListFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<UserModel> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlSearch = "SELECT * FROM login_table";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSearch);
        while (resultSet.next()) {
            model = new UserModel(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getBlob(5));
            list.add(model);
        }
        return list;
    }

    @Override
    public boolean updateNewUserCredentials(UserModel model) {
        boolean isSuccesfull;
        try {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlUpdate = "UPDATE login_table " +
                    "SET user_username='" + model.getUser_username() + "',user_password='" + model.getUser_password() + "',user_name='" + model.getUser_full_name() + "'" +
                    "WHERE user_id='" + model.getUser_id() + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlUpdate);
            isSuccesfull = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSuccesfull = false;
        }
        return isSuccesfull;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void deleteSpecificUserFromDB(String username) {
        try {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlDelete = "DELETE FROM login_table WHERE user_username=" + "'" + username + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlDelete);

            /**
             * Create Notification here
             */
            String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            NotificationModel notificationModel = new NotificationModel("Deleted User", "Deleted User: " + username, String.valueOf(dtf.format(now)),
                    MainMenuActivityView.GET_PREFERENCES_REALNAME);
            PreparedStatement preparedStatementUpdateNotification = connection.prepareStatement(sqlNotification);
            preparedStatementUpdateNotification.setString(1, notificationModel.getNotif_title());
            preparedStatementUpdateNotification.setString(2, notificationModel.getNotif_content());
            preparedStatementUpdateNotification.setString(3, notificationModel.getNotif_date());
            preparedStatementUpdateNotification.setString(4, notificationModel.getUser_name());
            preparedStatementUpdateNotification.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
