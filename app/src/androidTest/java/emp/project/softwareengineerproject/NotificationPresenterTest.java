package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Presenter.NotificationPresenter;

public class NotificationPresenterTest {
    private static final String MOCK_DATE = "12/1/2021";
    INotification.INotificationView view;
    INotification.INotificationService service;
    INotification.INotificationPresenter presenter;

    @Before
    public void setUp() {
        view = new MockNotificationView();
        service = new MockNotificationService();
        presenter = new NotificationPresenter(view, service);
    }

    @Test
    public void testDisplayNotifications() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadNotificationList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockNotificationView) view).isNotificationShowing);
    }

    @Test
    public void testDisplayDatePicker() {
        presenter.onDateButtonClicked();
        Assert.assertTrue(((MockNotificationView) view).isDatePickerShowing);
    }

    @Test
    public void testProgressIndicatorShowing() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadNotificationList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockNotificationView)view).isProgressIndicatorShowing);
    }

    @Test
    public void testProgressIndicatorNotShowing() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadNotificationList();
        Thread.sleep(1000);
        Assert.assertTrue(((MockNotificationView)view).isProgressIndicatorNotShowing);
    }

    static class MockNotificationView implements INotification.INotificationView {
        boolean isNotificationShowing;
        boolean isDatePickerShowing;
        boolean isProgressIndicatorShowing;
        boolean isProgressIndicatorNotShowing;

        @Override
        public void initViews() {

        }

        @Override
        public void displayNotificationRecyclerView(List<NotificationModel> list_notifs) {
            if (list_notifs.size() == 3) {
                isNotificationShowing = true;
            } else {
                isNotificationShowing = false;
            }
        }

        @Override
        public void showDatePicker() {
            isDatePickerShowing = true;
        }

        @Override
        public void displayProgressBar() {
            isProgressIndicatorShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressIndicatorNotShowing = true;
        }
    }

    static class MockNotificationService implements INotification.INotificationService {

        @Override
        public List<NotificationModel> fetchNotifsFromDB(String date_today) {
            date_today = MOCK_DATE;
            List<NotificationModel> mockDatabase = new ArrayList<>();
            List<NotificationModel> mockNotifs = new ArrayList<>();
            mockDatabase.add(new NotificationModel(null, null, null, MOCK_DATE, null));
            mockDatabase.add(new NotificationModel(null, null, null, MOCK_DATE, null));
            mockDatabase.add(new NotificationModel(null, null, null, MOCK_DATE, null));
            mockDatabase.add(new NotificationModel(null, null, null, "12/12/12", null));
            for (NotificationModel model : mockDatabase) {
                if (model.getNotif_date().equals(MOCK_DATE)) {
                    mockNotifs.add(model);
                }
            }
            return mockNotifs;
        }

        @Override
        public void insertNewNotifications(NotificationModel notificationModel) throws SQLException, ClassNotFoundException {

        }
    }
}
