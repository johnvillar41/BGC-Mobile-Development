package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.UserModel;

public class LoginPresenter implements ILogin.ILoginPresenter {
    public static String USER_REAL_NAME = "NAME";
    private ILogin.ILoginView view;
    private UserModel model;
    private ILogin.IDbHelper dBhelper;

    public LoginPresenter(ILogin.ILoginView view) {
        this.view = view;
        this.model = new UserModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void onLoginButtonClicked(String username, String password, View v) throws SQLException, ClassNotFoundException {
        model = new UserModel(username, password);
        if (model.validateCredentials(model) == null) {
            boolean success = dBhelper.checkLoginCredentialsDB(model);
            if (success) {
                view.onSuccess("Logging in!", v);
                view.goToMainPage();
            } else
                view.onError("User not found!", v);
        } else {
            view.onError(model.validateCredentials(model), v);
        }
    }

    private class DBhelper implements ILogin.IDbHelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/agt_db";
        private String USER = "admin";
        private String PASS = "admin";

        @Override
        public void StrictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public boolean checkLoginCredentialsDB(UserModel model) throws ClassNotFoundException, SQLException {//Checks Login and also returns the User Object for Users Page
            StrictMode();
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
