package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.UserModel;
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

    private static class LoginService implements ILogin.ILoginService {

        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public boolean checkLoginCredentialsDB(UserModel model) throws ClassNotFoundException, SQLException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlSearch = "SELECT * FROM login_table WHERE user_username=? AND user_password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch);
            preparedStatement.setString(1, model.getUser_username());
            preparedStatement.setString(2,model.getUser_password());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LoginPresenter.USER_REAL_NAME = resultSet.getString(4);
                return true;
            } else {

                return false;
            }
        }


    }

}
