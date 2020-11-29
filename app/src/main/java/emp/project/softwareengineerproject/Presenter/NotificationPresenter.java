package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void onSwipeDeleteNotification(String notif_id) {

    }

    @Override
    public void getNotificationList() throws SQLException {
        view.displayNotificationRecyclerView(dBhelper.fetchNotifsFromDB());
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
        public void deleteNotifOnDB(String notif_id) {

        }

        @Override
        public List<NotificationModel> fetchNotifsFromDB() throws SQLException {
            List<NotificationModel> list = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM notifications_table";
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
