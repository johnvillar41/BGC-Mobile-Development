package emp.project.softwareengineerproject.Presenter;

import android.view.View;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.NetworkChecker;
import emp.project.softwareengineerproject.Services.LoginService;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.ILoginService service;
    private WeakReference<LoginActivityView> context;

    public LoginPresenter(ILogin.ILoginView view, LoginActivityView context) {
        this.view = view;
        this.model = new UserModel();
        this.service = LoginService.getInstance();
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onLoginButtonClicked(final String username, final String password, final View v) {
        final NetworkChecker networkChecker = NetworkChecker.getSingleInstance(context);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                model = new UserModel(username, password);
                if (model.validateCredentials(model) == null) {
                    boolean success;
                    try {
                        success = service.checkLoginCredentialsDB(model);
                    } catch (ClassNotFoundException e) {
                        view.onError(e.getMessage(), v);
                        success = false;
                    } catch (SQLException e) {
                        view.onError(e.getMessage(), v);
                        success = false;
                    }
                    if (success) {
                        context.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onSuccess("Logging in!", v);
                                view.goToMainPage();
                            }
                        });
                    } else if (!networkChecker.isNetworkAvailable()) {
                        context.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError("No network Connected", v);
                                view.hideProgressBar();
                            }
                        });
                    } else {
                        context.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.onError("User not found!", v);
                                view.hideProgressBar();
                            }
                        });
                    }
                } else {
                    context.get().runOnUiThread(new Runnable() {
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
