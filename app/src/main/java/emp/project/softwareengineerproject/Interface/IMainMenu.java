package emp.project.softwareengineerproject.Interface;

import android.view.View;

import java.sql.Blob;
import java.sql.SQLException;

public interface IMainMenu {
    interface IMainMenuView {
        void initViews();

        void goToLoginScreen(View v);

        void goToInventory();

        void goToSales();

        void goToReports();

        void goToUsers();

        void goToSettings();

        void displayUsername();

        void gotoNotifications();

        void goToInformation();

        void displayNumberOfNotifs(String numberOfNotifs);

        void displayProfileImage(Blob profile);
    }

    interface IMainPresenter {
        /**
         * Handles all the click events on the main screen and redirects them to their specified activities
         * */
        void onLogoutButtonClicked(View v);

        void onInventoryButtonClicked();

        void onSalesButtonClicked();

        void onReportsButtonClicked();

        void onUsersButtonClicked();

        void onSettingsButtonClicked();

        void onNotificationButtonClicked();

        void onInformationButtonClicked();

        /**
         * Directs the username and profile picture to be displayed on the main screen
         */
        void directProfileDisplay() throws SQLException, ClassNotFoundException;

        void directPictureDisplay();


    }

    interface IMainService extends IServiceStrictMode {
        /**
         * Gets the total number of notifications per day on the database
         * @return will return the total number of notifications per day
         */
        int getNumberOfNotifications(String date) throws SQLException, ClassNotFoundException;

        Blob getProfilePicture() throws ClassNotFoundException, SQLException;
    }
}
