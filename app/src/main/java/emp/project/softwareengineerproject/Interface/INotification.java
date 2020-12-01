package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.NotificationModel;

public interface INotification {
    interface INotificationView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayNotificationRecyclerView(List<NotificationModel> list_notifs);

        void showDatePicker();
    }

    interface INotificationPresenter {
        void onDateButtonClicked();

        void getNotificationList() throws SQLException, ClassNotFoundException;

        void onSearchNotificationYesClicked(String date) throws SQLException, ClassNotFoundException;
    }

    interface INotificationService extends IServiceStrictMode{

        List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException, ClassNotFoundException;

    }
}
