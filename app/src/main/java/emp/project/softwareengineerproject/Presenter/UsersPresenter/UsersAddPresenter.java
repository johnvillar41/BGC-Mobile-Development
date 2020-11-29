package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.os.Build;
import android.os.StrictMode;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.View.MainMenuActivityView;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    IUsersAdd.IUsersAddView view;
    IUsersAdd.IUsersAddDBhelper dBhelper;
    UserModel model;

    public UsersAddPresenter(IUsersAdd.IUsersAddView view) {
        this.view = view;
        this.dBhelper = new DBhelper();
        this.model = new UserModel();
    }

    @Override
    public void onAddButtonClicked(TextInputLayout username, TextInputLayout password1, TextInputLayout password2, TextInputLayout realName, InputStream profileImage, View v) throws SQLException, ClassNotFoundException {
        UserModel newModel = model.validateAddUsers(username, password1, password2, realName, profileImage);
        if (newModel != null) {
            dBhelper.insertNewUserToDB(newModel);
            view.onStatusDisplayMessage("Successfully Added new User!", v);
        } else {
            view.onStatusDisplayMessage("Error Adding User!", v);
        }
    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private class DBhelper implements IUsersAdd.IUsersAddDBhelper {

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
            connection.close();

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
}
