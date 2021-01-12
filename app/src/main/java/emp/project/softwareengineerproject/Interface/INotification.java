package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.NotificationModel;

public interface INotification {
    interface INotificationView {
        /**
         * Initializes the xml items
         */
        void initViews() throws SQLException, ClassNotFoundException;

        /**
         * Displayes the list of notifications
         * @param list_notifs list of all the notication to be displayed
         */
        void displayNotificationRecyclerView(List<NotificationModel> list_notifs);

        /**
         * Displays the calendar or date so that the user can pick the date into which will the list of
         * notifications will be displayed
         */
        void showDatePicker();

        /**
         * Displays the progress linear loader
         */
        void displayProgressIndicator();

        /**
         * Hides the progress linear loader
         */
        void hideProgressIndicator();
    }

    interface INotificationPresenter {
        /**
         * Handles the menu button click event then redirects to the display of the calendar
         */
        void onDateButtonClicked();

        /**
         * Gets the list of the notifcations on the date today
         * */
        void loadNotificationList() throws SQLException, ClassNotFoundException;

        /**
         * Handles the calenday event click then redirects the proper lists to be searched in the service class
         * @param date date clicked on the calendar
         */
        void onSearchNotificationYesClicked(String date) throws SQLException, ClassNotFoundException;
    }

    interface INotificationService extends IServiceStrictMode{
        /**
         * Fetches the list of all the notifs on the database
         * @param date_today provides the date to be searched in the database
         * @return returns the List of notifications fetched from the database
         */
        List<NotificationModel> fetchNotifsFromDB(String date_today) throws SQLException, ClassNotFoundException;

    }
}
