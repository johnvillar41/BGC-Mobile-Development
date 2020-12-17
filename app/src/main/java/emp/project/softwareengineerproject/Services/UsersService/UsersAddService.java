package emp.project.softwareengineerproject.Services.UsersService;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class UsersAddService implements IUsersAdd.IUsersAddService {

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String inserNewUser = "INSERT INTO login_table(user_username,user_password,user_name,user_image)VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(inserNewUser);
        preparedStatement.setString(1, model.getUser_username());
        preparedStatement.setString(2, model.getUser_password());
        preparedStatement.setString(3, model.getUser_full_name());
        preparedStatement.setBlob(4, model.getUploadUserImage());
        preparedStatement.execute();
        preparedStatement.close();

        //notification for new account
        String sqlNotification = "INSERT INTO notifications_table(notif_title,notif_content,notif_date,user_name)VALUES(?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        NotificationModel notificationModel;
        notificationModel = new NotificationModel("Added new User", "Added user " + model.getUser_full_name(), String.valueOf(dtf.format(now)),
                MainMenuActivityView.GET_PREFERENCES_REALNAME);
        com.mysql.jdbc.PreparedStatement preparedStatementUpdateNotification = (com.mysql.jdbc.PreparedStatement) connection.prepareStatement(sqlNotification);
        preparedStatementUpdateNotification.setString(1, notificationModel.getNotif_title());
        preparedStatementUpdateNotification.setString(2, notificationModel.getNotif_content());
        preparedStatementUpdateNotification.setString(3, notificationModel.getNotif_date());
        preparedStatementUpdateNotification.setString(4, notificationModel.getUser_name());
        preparedStatementUpdateNotification.execute();
        preparedStatementUpdateNotification.close();
        preparedStatementUpdateNotification.close();
        connection.close();
    }
}
