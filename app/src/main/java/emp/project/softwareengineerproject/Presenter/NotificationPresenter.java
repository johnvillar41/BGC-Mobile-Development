package emp.project.softwareengineerproject.Presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Services.NotificationService;
import emp.project.softwareengineerproject.View.NotificationView.NotificationsActivityView;

public class NotificationPresenter implements INotification.INotificationPresenter {

    INotification.INotificationView view;
    INotification.INotificationService service;
    NotificationModel model;
    NotificationsActivityView context;

    public NotificationPresenter(INotification.INotificationView view, NotificationsActivityView context) {
        this.view = view;
        this.model = new NotificationModel();
        this.service = new NotificationService(this.model);
        this.context = context;
    }


    @Override
    public void onDateButtonClicked() {
        view.showDatePicker();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getNotificationList() {
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
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        view.displayNotificationRecyclerView(finalNotifsList);
                        view.hideProgressIndicator();
                    }
                });
            }
        });
        thread.start();

    }

    @Override
    public void onSearchNotificationYesClicked(final String date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        try {
                            view.displayNotificationRecyclerView(service.fetchNotifsFromDB(date));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        view.hideProgressIndicator();
                    }
                });
            }
        });
        thread.start();

    }



}
