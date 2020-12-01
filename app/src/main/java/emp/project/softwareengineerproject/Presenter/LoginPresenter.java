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

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.ILoginService service;

    public LoginPresenter(ILogin.ILoginView view) {
        this.view = view;
        this.model = new UserModel();
        this.service = new LoginService();
    }

    @Override
    public void onLoginButtonClicked(String username, String password, View v) throws SQLException, ClassNotFoundException {
        model = new UserModel(username, password);
        if (model.validateCredentials(model) == null) {
            boolean success = service.checkLoginCredentialsDB(model);
            if (success) {
                view.onSuccess("Logging in!", v);
                view.goToMainPage();
            } else
                view.onError("User not found!", v);
        } else {
            view.onError(model.validateCredentials(model), v);
        }
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
            String sqlSearch = "SELECT * FROM login_table WHERE user_username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch);
            preparedStatement.setString(1, model.getUser_username());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LoginPresenter.USER_REAL_NAME = resultSet.getString(4);
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return true;
            } else {
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return false;
            }
        }


    }

}
