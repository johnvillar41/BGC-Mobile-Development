package emp.project.softwareengineerproject.Presenter;

import android.view.View;

import emp.project.softwareengineerproject.Interface.IMainMenu;

public class MainMenuPresenter implements IMainMenu.IMainPresenter {
    private IMainMenu.IMainMenuView view;

    public MainMenuPresenter(IMainMenu.IMainMenuView view) {
        this.view = view;
    }

    @Override
    public void onLogoutButtonClicked(View v) {
        view.goToLoginScreen(v);
    }

    @Override
    public void onInventoryButtonClicked() {
        view.goToInventory();
    }

    @Override
    public void onSalesButtonClicked() {
        view.goToSales();
    }

    @Override
    public void onReportsButtonClicked() {
        view.goToReports();
    }

    @Override
    public void onUsersButtonClicked() {
        view.goToUsers();
    }

    @Override
    public void onSettingsButtonClicked() {
        view.goToSettings();
    }

    @Override
    public void directUsernameDisplay() {
        view.displayUsername();
    }
}
