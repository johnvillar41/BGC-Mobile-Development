package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.NotificationModel;

public interface INotification {
    interface INotificationView {
        void initViews() throws SQLException;

        void displayNotificationRecyclerView(List<NotificationModel> list_notifs);

        void showDatePicker();
    }

    interface INotificationPresenter {
        void onDateButtonClicked();

        void onSwipeDeleteNotification(String notif_id);

        void getNotificationList() throws SQLException;

        void onSearchNotificationYesClicked(String date) throws SQLException;
    }

    interface INotificationDBhelper {
        void StrictMode() throws ClassNotFoundException;

        void deleteNotifOnDB(String notif_id);

        List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException;

    }
}
