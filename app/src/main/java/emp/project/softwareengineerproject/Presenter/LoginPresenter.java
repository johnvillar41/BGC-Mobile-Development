package emp.project.softwareengineerproject.Presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.LoginService;
import emp.project.softwareengineerproject.NetworkChecker;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.ILoginService service;
    private WeakReference<Context> context;

    public LoginPresenter(ILogin.ILoginView view, Context context) {
        this.view = view;
        this.model = new UserModel();
        this.service = LoginService.getInstance();
        this.context = new WeakReference<>(context);
    }

    private static final String EMPTY_USERNAME = "Empty Username!";
    private static final String EMPTY_PASSWORD = "Empty Password!";
    private static final String EMPTY_BOTH = "Empty Both fields!";
    private static final String SUCCESS_MESSAGE = "Login Successfull!";
    private static final String USER_NOT_FOUND = "User not found!";
    private static final String NO_INTERNET = "No Internet Connection!";

    @Override
    public void onLoginButtonClicked(final String username, final String password, final View v) {
        final NetworkChecker networkChecker = NetworkChecker.getSingleInstance((Context) view);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                if (networkChecker.isNetworkAvailable()) {
                    model = new UserModel(username, password);
                    switch (model.validateCredentials(model)) {
                        case EMPTY_USERNAME:
                            ((Activity) context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(EMPTY_USERNAME, v);
                                    view.hideProgressBar();
                                }
                            });
                            break;
                        case EMPTY_PASSWORD:
                            ((Activity) context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(EMPTY_PASSWORD, v);
                                    view.hideProgressBar();
                                }
                            });
                            break;
                        case EMPTY_BOTH:
                            ((Activity) context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.onError(EMPTY_BOTH, v);
                                    view.hideProgressBar();
                                }
                            });
                            break;
                        case VALID_LOGIN:
                            try {
                                if (service.checkLoginCredentialsDB(model)) {
                                    ((Activity) context.get()).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            view.onSuccess(SUCCESS_MESSAGE, v);
                                            view.goToMainPage();
                                            view.hideProgressBar();
                                        }
                                    });
                                } else {
                                    ((Activity) context.get()).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            view.onError(USER_NOT_FOUND, v);
                                            view.hideProgressBar();
                                        }
                                    });
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            break;
                    }
                } else {
                    ((Activity) context.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.onError(NO_INTERNET, v);
                            view.hideProgressBar();
                        }
                    });
                }
            }
        });
        thread.start();
    }

}
