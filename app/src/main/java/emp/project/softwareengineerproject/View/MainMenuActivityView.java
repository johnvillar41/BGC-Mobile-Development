package emp.project.softwareengineerproject.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Presenter.MainMenuPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.InventoryView.InventoryActivityView;
import emp.project.softwareengineerproject.View.NotificationView.NotificationsActivityView;
import emp.project.softwareengineerproject.View.UsersView.UsersActivityView;

public class MainMenuActivityView extends AppCompatActivity implements IMainMenu.IMainMenuView, View.OnClickListener {
    private IMainMenu.IMainPresenter presenter;
    private TextView txt_name, txt_number_notifs;
    private SharedPreferences sharedPreferences;
    public static String GET_PREFERENCES_REALNAME = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        initViews();
        try {
            presenter.directUsernameDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES_USERNAME, MODE_PRIVATE);

        presenter = new MainMenuPresenter(this);
        Animation atg = AnimationUtils.loadAnimation(this, R.anim.atg);

        txt_name = findViewById(R.id.txt_name);
        txt_number_notifs = findViewById(R.id.txt_number_notification);
        ImageView imageView_illustration = findViewById(R.id.image_illustration);
        CircleImageView image_inventory = findViewById(R.id.image_stocks);
        CircleImageView image_sales = findViewById(R.id.image_sales);
        CircleImageView image_reports = findViewById(R.id.image_report);
        CircleImageView image_users = findViewById(R.id.image_users);
        CircleImageView image_settings = findViewById(R.id.image_settings);
        CircleImageView image_signout = findViewById(R.id.image_signOut);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_notifications);

        CardView cardView_inventory = findViewById(R.id.cardView_inventory);
        CardView cardView_sales = findViewById(R.id.cardView_sales);
        CardView cardView_reports = findViewById(R.id.cardView_reports);
        CardView cardView_users = findViewById(R.id.cardView_users);
        CardView cardView_settings = findViewById(R.id.cardView_settings);
        CardView cardView_logout = findViewById(R.id.cardView_logout);

        cardView_sales.setAnimation(atg);
        cardView_inventory.setAnimation(atg);
        cardView_reports.setAnimation(atg);
        cardView_users.setAnimation(atg);
        cardView_settings.setAnimation(atg);
        cardView_logout.setAnimation(atg);
        imageView_illustration.setAnimation(atg);
        txt_name.setAnimation(atg);

        cardView_inventory.setOnClickListener(this);
        cardView_sales.setOnClickListener(this);
        cardView_reports.setOnClickListener(this);
        cardView_users.setOnClickListener(this);
        cardView_settings.setOnClickListener(this);
        cardView_logout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        //Programmatically loading images through glide library due to crash on loading large amounts of images
        Glide.with(this).load(R.drawable.logo_main).into(imageView_illustration);
        Glide.with(this).load(R.drawable.stocks_logo).into(image_inventory);
        Glide.with(this).load(R.drawable.sales_logo).into(image_sales);
        Glide.with(this).load(R.drawable.reports_logo).into(image_reports);
        Glide.with(this).load(R.drawable.users_logo).into(image_users);
        Glide.with(this).load(R.drawable.settings_logo).into(image_settings);
        Glide.with(this).load(R.drawable.logout_logo).into(image_signout);


        if (sharedPreferences.getString(LoginActivityView.MyPREFERENCES_USERNAME, null) == null) {
            this.finish();
        }
    }

    @Override
    public void goToLoginScreen(View v) {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES_USERNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        this.finish();
        Intent intent = new Intent(this, LoginActivityView.class);
        startActivity(intent);
        Snackbar.make(v, "Logging out of session!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void goToInventory() {
        Intent intent = new Intent(this, InventoryActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToSales() {
        Intent intent = new Intent(this, SalesActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToReports() {
        Intent intent = new Intent(this, ReportsActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToUsers() {
        Intent intent = new Intent(this, UsersActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToSettings() {
        Intent intent = new Intent(this, SettingsActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayUsername() {
        txt_name.setText(sharedPreferences.getString(LoginActivityView.MyPREFERENCES_NAME, null));
        GET_PREFERENCES_REALNAME = txt_name.getText().toString();
    }

    @Override
    public void gotoNotifications() {
        Intent intent = new Intent(this, NotificationsActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayNumberOfNotifs(String numberOfNotifs) {
        txt_number_notifs.setText(numberOfNotifs);
    }

    //calling on resume method lifecycle of android to refresh the number of notifications set on main view
    @Override
    protected void onResume() {
        try {
            presenter.directUsernameDisplay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardView_inventory: {
                presenter.onInventoryButtonClicked();
                break;
            }
            case R.id.cardView_sales: {
                presenter.onSalesButtonClicked();
                break;
            }
            case R.id.cardView_reports: {
                presenter.onReportsButtonClicked();
                break;
            }
            case R.id.cardView_users: {
                presenter.onUsersButtonClicked();
                break;
            }
            case R.id.cardView_settings: {
                presenter.onSettingsButtonClicked();
                break;
            }
            case R.id.cardView_logout: {
                presenter.onLogoutButtonClicked(v);
                break;
            }
            case R.id.fab_notifications: {
                presenter.onNotificationButtonClicked();
                break;
            }
        }
    }
}