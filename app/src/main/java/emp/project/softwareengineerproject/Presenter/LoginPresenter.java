package emp.project.softwareengineerproject.Presenter;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Services.LoginService;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.ILoginService service;
    private LoginActivityView context;

    public LoginPresenter(ILogin.ILoginView view, LoginActivityView context) {
        this.view = view;
        this.model = new UserModel();
        this.service = new LoginService();
        this.context = context;
    }

    @Override
    public void onLoginButtonClicked(final String username, final String password, final View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                model = new UserModel(username, password);
                if (model.validateCredentials(model) == null) {
                    boolean success = false;
                    try {
                        success = service.checkLoginCredentialsDB(model);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (success) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onSuccess("Logging in!", v);
                                view.goToMainPage();
                            }
                        });
                    } else {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError("Error!", v);
                                view.hideProgressBar();
                            }
                        });
                    }
                } else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onError(model.validateCredentials(model), v);
                            view.hideProgressBar();
                        }
                    });
                }
            }
        });
        thread.start();
    }

}
