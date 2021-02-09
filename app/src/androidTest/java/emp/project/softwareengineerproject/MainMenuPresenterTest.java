package emp.project.softwareengineerproject;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Presenter.MainMenuPresenter;

public class MainMenuPresenterTest {
    IMainMenu.IMainMenuView view;
    IMainMenu.IMainService service;
    IMainMenu.IMainPresenter presenter;

    @Before
    public void setUp() {
        view = new MockMainMenuView();
        service = new MockMainMenuService();
        presenter = new MainMenuPresenter(view, service);
    }

    @Test
    public void testLogout() {
        presenter.onLogoutButtonClicked(null);
        Assert.assertTrue(MockMainMenuView.goToLoginScreen);
    }

    @Test
    public void testInventory() {
        presenter.onInventoryButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToInventory);
    }

    @Test
    public void testSales() {
        presenter.onSalesButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToSales);
    }

    @Test
    public void testReports() {
        presenter.onReportsButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToReports);
    }

    @Test
    public void testUsers() {
        presenter.onUsersButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToUsers);
    }

    @Test
    public void testSettings() {
        presenter.onSettingsButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToSettings);
    }

    @Test
    public void testUsernameDisplay() throws SQLException, ClassNotFoundException {
        presenter.directProfileDisplay();
        Assert.assertTrue(MockMainMenuView.isUsernameDisplayed);
    }

    @Test
    public void testNotifications() {
        presenter.onNotificationButtonClicked();
        Assert.assertTrue(MockMainMenuView.goToNotifications);
    }

    @Test
    public void testNumberOfNotifs() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.directProfileDisplay();
        Thread.sleep(1000);
        Assert.assertTrue(MockMainMenuView.isNumberOfNotifsDisplayed);
    }

    @Test
    public void testProfilePicture() throws InterruptedException {
        presenter.directPictureDisplay();
        Thread.sleep(1000);
        Assert.assertTrue(MockMainMenuView.isProfileDisplayed);
    }

    static class MockMainMenuView implements IMainMenu.IMainMenuView {
        static boolean goToLoginScreen;
        static boolean goToInventory;
        static boolean goToSales;
        static boolean goToReports;
        static boolean goToUsers;
        static boolean goToSettings;
        static boolean goToNotifications;
        static boolean isUsernameDisplayed;
        static boolean isNumberOfNotifsDisplayed;
        static boolean isProfileDisplayed;

        @Override
        public void initViews() {

        }

        @Override
        public void goToLoginScreen(View v) {
            goToLoginScreen = true;
        }

        @Override
        public void goToInventory() {
            goToInventory = true;
        }

        @Override
        public void goToSales() {
            goToSales = true;
        }

        @Override
        public void goToReports() {
            goToReports = true;
        }

        @Override
        public void goToUsers() {
            goToUsers = true;
        }

        @Override
        public void goToSettings() {
            goToSettings = true;
        }

        @Override
        public void displayUsername() {
            isUsernameDisplayed = true;
        }

        @Override
        public void gotoNotifications() {
            goToNotifications = true;
        }

        @Override
        public void goToInformation() {

        }

        @Override
        public void displayNumberOfNotifs(String numberOfNotifs) {
            if (numberOfNotifs.equals("3")) {
                isNumberOfNotifsDisplayed = true;
            } else {
                isNumberOfNotifsDisplayed = false;
            }
        }

        @Override
        public void displayNumberOfInformations(String numberOfInfo) {

        }

        @Override
        public void displayProfileImage(Blob profile) {
            if (profile == null) {
                isProfileDisplayed = true;
            } else {
                isProfileDisplayed = false;
            }
        }
    }

    static class MockMainMenuService implements IMainMenu.IMainService {


        @Override
        public int getNumberOfNotifications(String date) {
            List<NotificationModel> mockNotification = new ArrayList<>();
            NotificationModel model = new NotificationModel(null, null, null, "12/12/12", null);
            NotificationModel model1 = new NotificationModel(null, null, null, "12/12/12", null);
            NotificationModel model2 = new NotificationModel(null, null, null, "12/12/12", null);
            mockNotification.add(model);
            mockNotification.add(model1);
            mockNotification.add(model2);

            int size = 0;
            for (NotificationModel notifModel : mockNotification) {
                if (notifModel.getNotif_date().equals("12/12/12")) {
                    size++;
                }
            }
            return size;
        }

        @Override
        public Blob getProfilePicture() {
            return null;
        }

        @Override
        public Integer getNumberOfInformation() throws ClassNotFoundException, SQLException {
            return null;
        }
    }
}
