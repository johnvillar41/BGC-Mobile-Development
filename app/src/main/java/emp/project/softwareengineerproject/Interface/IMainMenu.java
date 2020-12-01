package emp.project.softwareengineerproject.Interface;

import android.view.View;

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

        void displayNumberOfNotifs(String numberOfNotifs);
    }

    interface IMainPresenter {
        void onLogoutButtonClicked(View v);

        void onInventoryButtonClicked();

        void onSalesButtonClicked();

        void onReportsButtonClicked();

        void onUsersButtonClicked();

        void onSettingsButtonClicked();

        void directUsernameDisplay() throws SQLException;

        void onNotificationButtonClicked();
    }

    interface IMainService extends IServiceStrictMode {

        int getNumberOfNotifications(String date) throws SQLException;
    }
}
