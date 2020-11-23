package emp.project.softwareengineerproject.Interface;

import android.view.View;

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

    }
    interface IMainPresenter{
        void onLogoutButtonClicked(View v);

        void onInventoryButtonClicked();

        void onSalesButtonClicked();

        void onReportsButtonClicked();

        void onUsersButtonClicked();

        void onSettingsButtonClicked();

        void directUsernameDisplay();
    }
}
