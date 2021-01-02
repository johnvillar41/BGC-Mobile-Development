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
    private static UsersService SINGLE_INSTANCE = null;

    private UsersService(UserModel model) {
        this.model = model;
    }

    public static UsersService getInstance(UserModel model) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new UsersService(model);
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
    public UserModel getUserProfileFromDB(String user_username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetUserProfile = "SELECT * FROM login_table WHERE user_username=" + "'" + user_username + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetUserProfile);
        if (resultSet.next()) {
            model = new UserModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getBlob(5));
            connection.close();
            statement.close();
            resultSet.close();
            return model;
        } else {
            connection.close();
            statement.close();
            resultSet.close();
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
        connection.close();
        statement.close();
        resultSet.close();
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean updateNewUserCredentials(UserModel model) {
        boolean isSuccesfull;
        try {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlUpdate = "UPDATE login_table SET user_username=? ,user_password=? ,user_name=? ,user_image=? WHERE user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1,model.getUser_username());
            preparedStatement.setString(2,model.getUser_password());
            preparedStatement.setString(3,model.getUser_full_name());
            preparedStatement.setBlob(4,model.getUploadUserImage());
            preparedStatement.setString(5,model.getUser_id());
            preparedStatement.execute();
            isSuccesfull = true;

            /**
             * Create Notification here
             */
            String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            NotificationModel notificationModel = new NotificationModel("Updated User", "Updated User: " + model.getUser_full_name(), String.valueOf(dtf.format(now)),
                    MainMenuActivityView.GET_PREFERENCES_REALNAME);
            PreparedStatement preparedStatementUpdateNotification = connection.prepareStatement(sqlNotification);
            preparedStatementUpdateNotification.setString(1, notificationModel.getNotif_title());
            preparedStatementUpdateNotification.setString(2, notificationModel.getNotif_content());
            preparedStatementUpdateNotification.setString(3, notificationModel.getNotif_date());
            preparedStatementUpdateNotification.setString(4, notificationModel.getUser_name());
            preparedStatementUpdateNotification.execute();
            connection.close();
            preparedStatement.close();
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

            connection.close();
            statement.close();
            preparedStatementUpdateNotification.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
