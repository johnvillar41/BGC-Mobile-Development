package emp.project.softwareengineerproject.Presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.Bean.NotificationModel;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;
import emp.project.softwareengineerproject.View.NotificationView.NotificationsActivityView;

public class NotificationPresenter implements INotification.INotificationPresenter {

    private INotification.INotificationView view;
    private INotification.INotificationService service;
    private NotificationModel model;
    private WeakReference<NotificationsActivityView> context;

    public NotificationPresenter(INotification.INotificationView view, NotificationsActivityView context) {
        this.view = view;
        this.model = new NotificationModel();
        this.service = NotificationService.getInstance(this.model);
        this.context = new WeakReference<>(context);
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
                context.get().runOnUiThread(new Runnable() {
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
                context.get().runOnUiThread(new Runnable() {
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
