package emp.project.softwareengineerproject.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.NetworkChecker;
import emp.project.softwareengineerproject.Presenter.LoginPresenter;
import emp.project.softwareengineerproject.R;

public class LoginActivityView extends AppCompatActivity implements ILogin.ILoginView {

    private MaterialButton btn_login;
    private TextInputLayout txt_username, txt_password;
    private ILogin.ILoginPresenter presenter;
    private ProgressIndicator progressIndicator;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String MyPREFERENCES_NAME = "NAME";
    public static final String USERNAME_PREFS = "Username_Prefs";

    public static String USERNAME_VALUE = null;

    /**
     * TODO
     * 1)Add an alertDialog on the search inventory activity for CRUD
     * 2)Change the icons for the orders in notificationActivity
     * 3)Change the design in the reports activity
     * 4)Add a delete button in the orders activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initViews();
    }

    @Override
    public void initViews() {
        try{displayNoNetworkPrompt();}catch(NullPointerException ignored){}

        presenter = new LoginPresenter(this, this);
        btn_login = findViewById(R.id.btn_login);
        txt_username = findViewById(R.id.textField_username);
        txt_password = findViewById(R.id.textField_password);
        progressIndicator = findViewById(R.id.progress_bar_login);
        progressIndicator.hide();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    presenter.onLoginButtonClicked(Objects.requireNonNull(txt_username.getEditText()).getText().toString(),
                            Objects.requireNonNull(txt_password.getEditText()).getText().toString(), v);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onSuccess(String message, View v) {
        //hides keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Snackbar snack = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(LoginActivityView.this, android.R.color.holo_orange_dark));
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        tv.setGravity(Gravity.CENTER);
        snack.show();

        USERNAME_VALUE = txt_username.getEditText().getText().toString();
    }

    @Override
    public void onError(String message, View v) {
        //hides keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Snackbar snack = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(LoginActivityView.this, android.R.color.holo_orange_dark));
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
        tv.setGravity(Gravity.CENTER);
        snack.show();
    }

    @Override
    public void goToMainPage() {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME_PREFS, txt_username.getEditText().getText().toString());
        editor.putString(MyPREFERENCES_NAME, LoginPresenter.USER_REAL_NAME);
        editor.apply();
        Intent intent = new Intent(this, MainMenuActivityView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void displayProgressBar() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressBar() {
        progressIndicator.hide();
    }

    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
            //cacheManager.clearGlideMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        super.onDestroy();
    }

    private void displayNoNetworkPrompt() {
        NetworkChecker networkChecker = NetworkChecker.getSingleInstance(this);
        networkChecker.displayNoNetworkConnection();
    }
}