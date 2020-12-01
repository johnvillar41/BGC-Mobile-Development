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

public class NotificationPresenter implements INotification.INotificationPresenter {

    INotification.INotificationView view;
    INotification.INotificationDBhelper dBhelper;
    NotificationModel model;

    public NotificationPresenter(INotification.INotificationView view) {
        this.view = view;
        this.dBhelper = new DBhelper();
        this.model = new NotificationModel();
    }


    @Override
    public void onDateButtonClicked() {
        view.showDatePicker();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getNotificationList() throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        view.displayNotificationRecyclerView(dBhelper.fetchNotifsFromDB(dtf.format(now)));
    }

    @Override
    public void onSearchNotificationYesClicked(String date) throws SQLException {
        view.displayNotificationRecyclerView(dBhelper.fetchNotifsFromDB(date));
    }

    private class DBhelper implements INotification.INotificationDBhelper {

        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

        @Override
        public void StrictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException {
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
