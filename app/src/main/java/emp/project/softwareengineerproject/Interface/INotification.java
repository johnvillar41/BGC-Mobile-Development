package emp.project.softwareengineerproject.Interface;

import java.util.List;

import emp.project.softwareengineerproject.Model.NotificationModel;

public interface INotification {
    interface INotificationView {
        void initViews();

        void displayNotificationRecyclerView(List<NotificationModel> list_notifs);
    }

    interface INotificationPresenter {
        void onSwipeDeleteNotification(String notif_id);

        void getNotificationList(List<NotificationModel>list_notifs);
    }

    interface INotificationDBhelper {
        void StrictMode() throws ClassNotFoundException;

        void deleteNotifOnDB(String notif_id);

        List<NotificationModel> fetchNotifsFromDB();
    }
}
