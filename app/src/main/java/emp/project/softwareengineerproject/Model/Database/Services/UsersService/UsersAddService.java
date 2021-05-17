package emp.project.softwareengineerproject.Model.Database.Services.UsersService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Constants;
import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;

public class UsersAddService implements IUsersAdd.IUsersAddService {

    private static UsersAddService SINGLE_INSTANCE = null;

    private UsersAddService() {

    }

    public static UsersAddService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new UsersAddService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String inserNewUser = "INSERT INTO login_table(user_username,user_password,user_name,user_image)VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(inserNewUser);
        preparedStatement.setString(1, model.getUsername());
        preparedStatement.setString(2, model.getPassword());
        preparedStatement.setString(3, model.getFullName());
        preparedStatement.setBlob(4, model.getUserPicture());
        preparedStatement.execute();
        preparedStatement.close();

        //notification for new account
        NotificationModel newNotificationModel = NotificationService.getInstance().notificationFactory(model.getFullName(), Constants.NotificationStatus.ADDED_NEW_USER);
        NotificationService.getInstance().insertNewNotifications(newNotificationModel);

        connection.close();
    }
}
