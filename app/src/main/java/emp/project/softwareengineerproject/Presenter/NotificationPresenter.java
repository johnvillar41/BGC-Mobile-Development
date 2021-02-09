package emp.project.softwareengineerproject.Presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;

public class NotificationPresenter implements INotification.INotificationPresenter {

    private INotification.INotificationView view;
    private INotification.INotificationService service;
    private NotificationModel model;

    public NotificationPresenter(INotification.INotificationView view, INotification.INotificationService service) {
        this.view = view;
        this.model = new NotificationModel();
        this.service = service;
    }


    @Override
    public void onDateButtonClicked() {
        view.showDatePicker();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void loadNotificationList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                List<NotificationModel> notifsList = null;
                try {
                    notifsList = service.fetchNotifsFromDB(dtf.format(now));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                final List<NotificationModel> finalNotifsList = notifsList;
                view.displayProgressBar();
                view.displayNotificationRecyclerView(finalNotifsList);
                view.hideProgressBar();
            }

        });
        thread.start();

    }

    @Override
    public void onSearchNotificationYesClicked(final String date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    view.displayNotificationRecyclerView(service.fetchNotifsFromDB(date));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });
        thread.start();

    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
