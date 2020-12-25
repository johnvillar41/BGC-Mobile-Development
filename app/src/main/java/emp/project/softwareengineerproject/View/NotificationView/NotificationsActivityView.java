package emp.project.softwareengineerproject.View.NotificationView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Presenter.NotificationPresenter;
import emp.project.softwareengineerproject.R;

public class NotificationsActivityView extends AppCompatActivity implements INotification.INotificationView {

    private INotification.INotificationPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView circleImageView_empty;
    private ProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notifications_view);
        try {
            initViews();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() throws SQLException, ClassNotFoundException {
        presenter = new NotificationPresenter(this, this);
        recyclerView = findViewById(R.id.recyclerView_notification);
        circleImageView_empty = findViewById(R.id.empty_image);
        progressIndicator = findViewById(R.id.progressBar_Notifications);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter.getNotificationList();
    }

    @Override
    public void displayNotificationRecyclerView(final List<NotificationModel> list_notifs) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(NotificationsActivityView.this, LinearLayoutManager.VERTICAL, false);
        NotificationRecyclerView adapter = new NotificationRecyclerView(
                NotificationsActivityView.this, list_notifs);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
        if (adapter.getItemCount() == 0) {
            Glide.with(NotificationsActivityView.this)
                    .load(R.drawable.no_result_imag2)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(circleImageView_empty);
            circleImageView_empty.setVisibility(View.VISIBLE);
        } else {
            circleImageView_empty.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
            cacheManager.clearGlideMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        super.onDestroy();
    }

    @Override
    public void showDatePicker() {
        //set actions
        DatePickerDialog datePicker;
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                calendar.set(year, month, dayOfMonth);
                String dateString = sdf.format(calendar.getTime());
                Toast.makeText(NotificationsActivityView.this, dateString, Toast.LENGTH_LONG).show();

                // Set search on notifications
                try {
                    presenter.onSearchNotificationYesClicked(dateString);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);
        datePicker.show();
    }

    @Override
    public void displayProgressIndicator() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.action_date) {
            presenter.onDateButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }
}