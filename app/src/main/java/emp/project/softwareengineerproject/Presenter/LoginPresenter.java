package emp.project.softwareengineerproject.Presenter;

import android.view.View;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

import static emp.project.softwareengineerproject.Constants.LoginConstants.EMPTY_BOTH;
import static emp.project.softwareengineerproject.Constants.LoginConstants.EMPTY_PASSWORD;
import static emp.project.softwareengineerproject.Constants.LoginConstants.EMPTY_USERNAME;
import static emp.project.softwareengineerproject.Constants.LoginConstants.SUCCESS_MESSAGE;
import static emp.project.softwareengineerproject.Constants.LoginConstants.USER_NOT_FOUND;
import static emp.project.softwareengineerproject.Constants.LoginConstants.VALID_LOGIN;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private ILogin.ILoginService service;

    public LoginPresenter(ILogin.ILoginView view, ILogin.ILoginService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onLoginButtonClicked(String username, String password, View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                String errorList = view.FindErrors();
                switch (errorList) {
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
                            if (service.checkLoginCredentialsDB(username, password)) {
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
