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
import emp.project.softwareengineerproject.View.UsersView.UsersAddActivityView;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    IUsersAdd.IUsersAddView view;
    IUsersAdd.IUsersAddService service;
    UserModel model;
    UsersAddActivityView context;

    public UsersAddPresenter(IUsersAdd.IUsersAddView view, UsersAddActivityView context) {
        this.view = view;
        this.service = new UsersAddService();
        this.model = new UserModel();
        this.context = context;
    }

    @Override
    public void onAddButtonClicked(final TextInputLayout username, final TextInputLayout password1, final TextInputLayout password2,
                                   final TextInputLayout realName, final InputStream profileImage, final View v) {

        Thread thread = new Thread(new Runnable() {
            Boolean ifSuccess = true;

            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        final UserModel newModel = model.validateAddUsers(username, password1, password2, realName, profileImage);
                        if (newModel != null) {

                            final Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        service.insertNewUserToDB(newModel);
                                    } catch (final Exception e) {
                                        ifSuccess = false;
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.onStatusDisplayMessage(e.getMessage(), v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    }
                                }
                            });
                            thread1.start();
                            try {
                                thread1.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ifSuccess = false;
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onStatusDisplayMessage("Error Adding User!", v);
                                    view.hideProgressIndicator();
                                }
                            });
                        }
                        if (ifSuccess) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onStatusDisplayMessage("Successfully Added new User!", v);
                                    view.hideProgressIndicator();
                                    view.displayCheckAnimation();
                                }
                            });
                        }
                    }
                });
            }
        });
        thread.start();

    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private class UsersAddService implements IUsersAdd.IUsersAddService {

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
