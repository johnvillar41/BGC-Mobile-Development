package emp.project.softwareengineerproject.Presenter;

import android.view.View;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.ILoginService service;

    public static final String EMPTY_USERNAME = "Empty Username!";
    public static final String EMPTY_PASSWORD = "Empty Password!";
    public static final String EMPTY_BOTH = "Empty Both fields!";
    public static final String SUCCESS_MESSAGE = "Login Successfull!";
    private static final String NO_INTERNET = "No Internet Connection!";//TODO: Add internet
    private static final String USER_NOT_FOUND = "User not found!";

    public LoginPresenter(ILogin.ILoginView view, ILogin.ILoginService service) {
        this.view = view;
        this.model = new UserModel();
        this.service = service;
    }

    @Override
    public void onLoginButtonClicked(String username, String password, View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                model = new UserModel(username, password);
                switch (model.validateLoginCredentials(model)) {
                    case EMPTY_USERNAME:
                        view.onError(EMPTY_USERNAME, v);
                        view.hideProgressBar();
                        break;
                    case EMPTY_PASSWORD:
                        view.onError(EMPTY_PASSWORD, v);
                        view.hideProgressBar();
                        break;
                    case EMPTY_BOTH:
                        view.onError(EMPTY_BOTH, v);
                        view.hideProgressBar();
                        break;
                    case VALID_LOGIN:
                        try {
                            if (service.checkLoginCredentialsDB(model)) {
                                view.onSuccess(SUCCESS_MESSAGE, v);
                                view.goToMainPage();
                                view.hideProgressBar();
                            } else {
                                view.onError(USER_NOT_FOUND, v);
                                view.hideProgressBar();
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                }
            }
        });
        thread.start();
    }

    @Override
    public void initializeViews() {
        view.initViews();
    }
}
