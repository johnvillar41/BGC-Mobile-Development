package emp.project.softwareengineerproject.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.SQLException;
import java.util.Objects;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Presenter.LoginPresenter;
import emp.project.softwareengineerproject.R;

public class LoginActivityView extends AppCompatActivity implements ILogin.ILoginView {
    private MaterialButton btn_login;
    private TextInputLayout txt_username, txt_password;
    private ILogin.ILoginPresenter presenter;
    public static final String MyPREFERENCES_USERNAME = "MyPrefs";
    public static final String MyPREFERENCES_NAME = "NAME";
    private ProgressIndicator progressIndicator;

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
        presenter = new LoginPresenter(this,this);
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
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onError(String message, View v) {
        //hides keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void goToMainPage() {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES_USERNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MyPREFERENCES_USERNAME, txt_username.getEditText().getText().toString());
        editor.putString(MyPREFERENCES_NAME, LoginPresenter.USER_REAL_NAME);
        editor.apply();
        finish();
        Intent intent = new Intent(this, MainMenuActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayProgressBar() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressBar() {
        progressIndicator.hide();
    }
}