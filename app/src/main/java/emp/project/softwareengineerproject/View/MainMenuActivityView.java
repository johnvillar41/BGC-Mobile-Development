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
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import emp.project.softwareengineerproject.Interface.IMainMenu;
import emp.project.softwareengineerproject.Presenter.MainMenuPresenter;
import emp.project.softwareengineerproject.R;

public class MainMenuActivityView extends AppCompatActivity implements IMainMenu.IMainMenuView, View.OnClickListener {
    private MainMenuPresenter presenter;
    private TextView txt_name;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        initViews();
        presenter.directUsernameDisplay();
    }

    @Override
    public void initViews() {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, MODE_PRIVATE);

        presenter = new MainMenuPresenter(this);
        txt_name = findViewById(R.id.txt_name);
        CardView cardView_inventory = findViewById(R.id.cardView_inventory);
        CardView cardView_sales = findViewById(R.id.cardView_sales);
        CardView cardView_reports = findViewById(R.id.cardView_reports);
        CardView cardView_users = findViewById(R.id.cardView_users);
        CardView cardView_settings = findViewById(R.id.cardView_settings);
        CardView cardView_logout = findViewById(R.id.cardView_logout);

        cardView_inventory.setOnClickListener(this);
        cardView_sales.setOnClickListener(this);
        cardView_reports.setOnClickListener(this);
        cardView_users.setOnClickListener(this);
        cardView_settings.setOnClickListener(this);
        cardView_logout.setOnClickListener(this);

        if (sharedPreferences.getString(LoginActivityView.MyPREFERENCES, null) == null){
            this.finish();
        }
    }

    @Override
    public void goToLoginScreen(View v) {
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        this.finish();
        Intent intent = new Intent(this, LoginActivityView.class);
        startActivity(intent);
        Snackbar.make(v,"Logging out of session!", Snackbar.LENGTH_SHORT).show();
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
        txt_name.setText(sharedPreferences.getString(LoginActivityView.MyPREFERENCES, null));
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
        }
    }
}