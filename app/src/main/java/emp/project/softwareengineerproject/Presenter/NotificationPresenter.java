package emp.project.softwareengineerproject.Presenter;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.View.NotificationView.NotificationsActivityView;

public class NotificationPresenter implements INotification.INotificationPresenter {

    INotification.INotificationView view;
    INotification.INotificationService service;
    NotificationModel model;
    NotificationsActivityView context;

    public NotificationPresenter(INotification.INotificationView view, NotificationsActivityView context) {
        this.view = view;
        this.service = new NotificationService();
        this.model = new NotificationModel();
        this.context = context;
    }


    @Override
    public void onDateButtonClicked() {
        view.showDatePicker();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getNotificationList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                List<NotificationModel> notifsList = null;
                try {
                    notifsList = service.fetchNotifsFromDB(dtf.format(now));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                final List<NotificationModel> finalNotifsList = notifsList;
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        view.displayNotificationRecyclerView(finalNotifsList);
                        view.hideProgressIndicator();
                    }
                });
            }
        });
        thread.start();

    }

    @Override
    public void onSearchNotificationYesClicked(final String date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        try {
                            view.displayNotificationRecyclerView(service.fetchNotifsFromDB(date));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        view.hideProgressIndicator();
                    }
                });
            }
        });
        thread.start();

    }

    private class NotificationService implements INotification.INotificationService {

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
        public List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException, ClassNotFoundException {
            strictMode();
            List<NotificationModel> list = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM notifications_table WHERE notif_date LIKE " + "'" + date_today + "%'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                model = new NotificationModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getString(4),
                        resultSet.getString(5));
                list.add(model);
            }
            return list;
        }
    }

}
