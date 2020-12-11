package emp.project.softwareengineerproject.Presenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Services.MainMenuService;

public class MainMenuPresenter implements IMainMenu.IMainPresenter {
    private IMainMenu.IMainMenuView view;
    private IMainMenu.IMainService service;

    public MainMenuPresenter(IMainMenu.IMainMenuView view) {
        this.view = view;
        this.service = new MainMenuService();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void directUsernameDisplay() throws SQLException, ClassNotFoundException {
        view.displayUsername();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        view.displayNumberOfNotifs(String.valueOf(service.getNumberOfNotifications(dtf.format(now))));
    }

    @Override
    public void onNotificationButtonClicked() {
        view.gotoNotifications();
    }


}
