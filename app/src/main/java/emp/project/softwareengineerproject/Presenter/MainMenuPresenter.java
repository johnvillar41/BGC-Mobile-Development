package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IMainMenu;

public class MainMenuPresenter implements IMainMenu.IMainPresenter {
    private IMainMenu.IMainMenuView view;
    private IMainMenu.IMainDbhelper dbhelper;

    public MainMenuPresenter(IMainMenu.IMainMenuView view) {
        this.view = view;
        this.dbhelper = new Dbhelper();
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
    public void directUsernameDisplay() throws SQLException {
        view.displayUsername();
        view.displayNumberOfNotifs(String.valueOf(dbhelper.getNumberOfNotifications()));
    }

    @Override
    public void onNotificationButtonClicked() {
        view.gotoNotifications();
    }

    private class Dbhelper implements IMainMenu.IMainDbhelper {
        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();


        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public int getNumberOfNotifications() throws SQLException {
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            String sqlGetNumberOfNotifs = "SELECT COUNT(*) FROM notifications_table";
            ResultSet resultSet = statement.executeQuery(sqlGetNumberOfNotifs);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        }
    }
}
