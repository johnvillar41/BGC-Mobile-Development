package emp.project.softwareengineerproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.CustomAdapters.NotificationRecyclerView;
import emp.project.softwareengineerproject.CustomAdapters.ProductSearchedRecyclerView;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Presenter.NotificationPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.InventoryView.InventorySearchItemView;

public class NotificationsActivityView extends AppCompatActivity implements INotification.INotificationView {

    INotification.INotificationPresenter presenter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notifications_view);
        try {
            initViews();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() throws SQLException {
        presenter = new NotificationPresenter(this);
        recyclerView = findViewById(R.id.recyclerView_notification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter.getNotificationList();
    }

    @Override
    public void displayNotificationRecyclerView(final List<NotificationModel> list_notifs) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManager
                        = new LinearLayoutManager(NotificationsActivityView.this, LinearLayoutManager.VERTICAL, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NotificationRecyclerView adapter = new NotificationRecyclerView(
                                NotificationsActivityView.this, list_notifs);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}