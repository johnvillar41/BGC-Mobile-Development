package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;

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
    public void getNotificationList(List<NotificationModel> list_notifs) {

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
        public List<NotificationModel> fetchNotifsFromDB() {
            return null;
        }
    }

}
