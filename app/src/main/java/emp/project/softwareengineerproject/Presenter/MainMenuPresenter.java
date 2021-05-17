package emp.project.softwareengineerproject.Presenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.IMainMenu;

public class MainMenuPresenter implements IMainMenu.IMainPresenter {
    private IMainMenu.IMainMenuView view;
    private IMainMenu.IMainService service;

    public MainMenuPresenter(IMainMenu.IMainMenuView view, IMainMenu.IMainService service) {
        this.view = view;
        this.service = service;
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
    public void directProfileDisplay() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayUsername();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                try {
                    String numberOfNotifs = String.valueOf(service.getNumberOfNotifications(dtf.format(now)));
                    String numberOfInfo = String.valueOf(service.getNumberOfInformation());
                    view.displayNumberOfNotifs(numberOfNotifs);
                    view.displayNumberOfInformations(numberOfInfo);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    public void loadProfilePicture() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Blob profile = service.getProfilePicture();
                    view.displayProfileImage(profile);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void loadNumberOfNotfis() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime now = LocalDateTime.now();
                    String numberOfNotifs = String.valueOf(service.getNumberOfNotifications(dtf.format(now)));
                    view.displayNumberOfNotifs(numberOfNotifs);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });thread.start();
    }

    @Override
    public void loadNumberOfInfos() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String numberOfInfo = String.valueOf(service.getNumberOfInformation());
                    view.displayNumberOfInformations(numberOfInfo);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });thread.start();


    }

    @Override
    public void onNotificationButtonClicked() {
        view.gotoNotifications();
    }

    @Override
    public void onInformationButtonClicked() {
        view.goToInformation();
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
