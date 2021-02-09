package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.NotificationModel;

public interface INotification {
    interface INotificationView extends IBaseView {
        void displayNotificationRecyclerView(List<NotificationModel> list_notifs);

        void showDatePicker();
    }

    interface INotificationPresenter extends IBasePresenter {
        void onDateButtonClicked();

        void loadNotificationList();

        void onSearchNotificationYesClicked(String date);
    }

    interface INotificationService extends IServiceStrictMode {
        List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException, ClassNotFoundException;

        void insertNewNotifications(NotificationModel notificationModel) throws SQLException, ClassNotFoundException;
    }
}
