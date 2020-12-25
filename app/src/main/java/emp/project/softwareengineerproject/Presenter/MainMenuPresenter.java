package emp.project.softwareengineerproject.Presenter;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Services.MainMenuService;

public class MainMenuPresenter extends Activity implements IMainMenu.IMainPresenter {
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
    public void directProfileDisplay() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayUsername();
                    }
                });
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                try {
                    final String numberOfNotifs = String.valueOf(service.getNumberOfNotifications(dtf.format(now)));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayNumberOfNotifs(numberOfNotifs);
                        }
                    });
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
    public void directPictureDisplay() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Blob profile = service.getProfilePicture();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProfileImage(profile);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });thread.start();
    }

    @Override
    public void onNotificationButtonClicked() {
        view.gotoNotifications();
    }


}
