package emp.project.softwareengineerproject.View.NotificationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.INotification;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.Presenter.NotificationPresenter;
import emp.project.softwareengineerproject.R;

public class NotificationsActivityView extends AppCompatActivity implements INotification.INotificationView {

    INotification.INotificationPresenter presenter;
    RecyclerView recyclerView;
    CircleImageView circleImageView_empty;

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
        circleImageView_empty = findViewById(R.id.empty_image);

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
                        if (adapter.getItemCount() == 0) {
                            Glide.with(NotificationsActivityView.this).load(R.drawable.no_notifications_logo).into(circleImageView_empty);
                            circleImageView_empty.setVisibility(View.VISIBLE);
                        } else {
                            circleImageView_empty.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        thread.start();
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);
        datePicker.show();
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