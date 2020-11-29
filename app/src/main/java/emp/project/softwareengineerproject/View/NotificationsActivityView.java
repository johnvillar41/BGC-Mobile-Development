package emp.project.softwareengineerproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Presenter.NotificationPresenter;
import emp.project.softwareengineerproject.R;

public class NotificationsActivityView extends AppCompatActivity implements INotification.INotificationView {

    INotification.INotificationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_view);
        initViews();
    }

    @Override
    public void initViews() {
        presenter = new NotificationPresenter(this);
    }

    @Override
    public void displayNotificationRecyclerView(List<NotificationModel> list_notifs) {

    }
}