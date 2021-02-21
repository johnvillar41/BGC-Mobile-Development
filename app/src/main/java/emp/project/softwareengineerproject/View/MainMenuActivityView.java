package emp.project.softwareengineerproject.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventorySearchItemService;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryService;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryUpdateService;
import emp.project.softwareengineerproject.Model.Database.Services.LoginService;
import emp.project.softwareengineerproject.Model.Database.Services.MainMenuService;
import emp.project.softwareengineerproject.Model.Database.Services.NotificationService;
import emp.project.softwareengineerproject.Model.Database.Services.OrdersService;
import emp.project.softwareengineerproject.Model.Database.Services.ReportsService;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesAddService;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesService;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesTransactionService;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersAddService;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersService;
import emp.project.softwareengineerproject.Presenter.MainMenuPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.InformationView.InformationActivityView;
import emp.project.softwareengineerproject.View.InventoryView.InventoryActivityView;
import emp.project.softwareengineerproject.View.NotificationView.NotificationsActivityView;
import emp.project.softwareengineerproject.View.OrdersView.OrdersActivityView;
import emp.project.softwareengineerproject.View.ReportsView.ReportsActivityView;
import emp.project.softwareengineerproject.View.SalesView.SalesActivityView;
import emp.project.softwareengineerproject.View.UsersView.UsersActivityView;

public class MainMenuActivityView extends AppCompatActivity implements IMainMenu.IMainMenuView, View.OnClickListener {
    private IMainMenu.IMainPresenter presenter;
    private TextView txt_name, txt_number_notifs, txt_number_info;
    private SharedPreferences sharedPreferences;
    public static String GET_PREFERENCES_REALNAME = null;
    private CircleImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        presenter = new MainMenuPresenter(this, MainMenuService.getInstance());
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, MODE_PRIVATE);
        Animation atg = AnimationUtils.loadAnimation(this, R.anim.atg);

        try {
            presenter.directProfileDisplay();
            presenter.loadProfilePicture();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        txt_name = findViewById(R.id.txt_name);
        txt_number_notifs = findViewById(R.id.txt_number_notification);
        txt_number_info = findViewById(R.id.txt_number_information);
        image_profile = findViewById(R.id.image_profile);
        CircleImageView image_inventory = findViewById(R.id.image_stocks);
        CircleImageView image_sales = findViewById(R.id.image_sales);
        CircleImageView image_reports = findViewById(R.id.image_report);
        CircleImageView image_users = findViewById(R.id.image_users);
        CircleImageView image_settings = findViewById(R.id.image_settings);
        CircleImageView image_signout = findViewById(R.id.image_signOut);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_notifications);
        FloatingActionButton floatingActionButton_information = findViewById(R.id.fab_information);

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
        //imageView_illustration.setAnimation(atg);
        txt_name.setAnimation(atg);

        cardView_inventory.setOnClickListener(this);
        cardView_sales.setOnClickListener(this);
        cardView_reports.setOnClickListener(this);
        cardView_users.setOnClickListener(this);
        cardView_settings.setOnClickListener(this);
        cardView_logout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton_information.setOnClickListener(this);

        /**
         * Programmatically loading images through glide library due to
         * crash on loading large amounts of images
         */
        Glide.with(this).asBitmap().load(R.drawable.stocks_logo).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_inventory);
        Glide.with(this).asBitmap().load(R.drawable.sales_logo).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_sales);
        Glide.with(this).asBitmap().load(R.drawable.reports_logo).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_reports);
        Glide.with(this).asBitmap().load(R.drawable.users_logo).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_users);
        Glide.with(this).asBitmap().load(R.drawable.orders_online).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_settings);
        Glide.with(this).asBitmap().load(R.drawable.ic_baseline_exit_to_app_24).fitCenter().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_signout);


        if (sharedPreferences.getString(LoginActivityView.USERNAME_PREFS, null) == null) {
            this.finish();
        }
    }

    @Override
    public void goToLoginScreen(View v) {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainMenuActivityView.this, LoginActivityView.class);
        startActivity(intent);
        MainMenuActivityView.this.finish();
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
        Intent intent = new Intent(this, OrdersActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayUsername() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_name.setText(sharedPreferences.getString(LoginActivityView.MyPREFERENCES_NAME, null));
                GET_PREFERENCES_REALNAME = txt_name.getText().toString();
            }
        });
    }

    @Override
    public void gotoNotifications() {
        Intent intent = new Intent(this, NotificationsActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToInformation() {
        Intent intent = new Intent(this, InformationActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayNumberOfNotifs(String numberOfNotifs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_number_notifs.setText(numberOfNotifs);
            }
        });
    }

    @Override
    public void displayNumberOfInformations(String numberOfInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_number_info.setText(numberOfInfo);
            }
        });
    }

    @Override
    public void displayProfileImage(Blob profile) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Blob b = profile;
                final int[] blobLength = new int[1];
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    Glide.with(MainMenuActivityView.this)
                            .asBitmap()
                            .load(blobAsBytes)
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into(image_profile);
                } catch (SQLException ignored) {

                } catch (IllegalArgumentException ignored){

                }
            }
        });

    }

    /**
     * calling on resume method lifecycle of android to
     * refresh the number of notifications set on main view
     */
    @Override
    protected void onResume() {
        presenter.loadNumberOfInfos();
        presenter.loadNumberOfNotfis();
        presenter.loadProfilePicture();
        if (sharedPreferences.getString(LoginActivityView.USERNAME_PREFS, null) == null) {
            this.finish();
        }
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        /**
         * Removes SingleTon Instances
         */
        InventorySearchItemService.getInstance(null).removeInstance();
        InventoryService.getInstance(null).removeInstance();
        InventoryUpdateService.getInstance().removeInstance();
        SalesAddService.getInstance().removeInstance();
        SalesService.getInstance().removeInstance();
        SalesTransactionService.getInstance(null).removeInstance();
        UsersAddService.getInstance().removeInstance();
        UsersService.getInstance(null).removeInstance();
        LoginService.getInstance().removeInstance();
        MainMenuService.getInstance().removeInstance();
        NotificationService.getInstance().removeInstance();
        OrdersService.getInstance(null).removeInstance();
        ReportsService.getInstance(null).removeInstance();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardView_inventory) {
            presenter.onInventoryButtonClicked();
        } else if (v.getId() == R.id.cardView_sales) {
            presenter.onSalesButtonClicked();
        } else if (v.getId() == R.id.cardView_reports) {
            presenter.onReportsButtonClicked();
        } else if (v.getId() == R.id.cardView_users) {
            presenter.onUsersButtonClicked();
        } else if (v.getId() == R.id.cardView_settings) {
            presenter.onSettingsButtonClicked();
        } else if (v.getId() == R.id.cardView_logout) {
            presenter.onLogoutButtonClicked(v);
        } else if (v.getId() == R.id.fab_notifications) {
            presenter.onNotificationButtonClicked();
        } else if (v.getId() == R.id.fab_information) {
            presenter.onInformationButtonClicked();
        }
    }
}