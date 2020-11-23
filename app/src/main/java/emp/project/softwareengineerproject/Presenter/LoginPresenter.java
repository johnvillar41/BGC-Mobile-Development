package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;
import android.view.View;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.LoginModel;

public class LoginPresenter implements ILogin.ILoginPresenter {
    private ILogin.ILoginView view;
    private LoginModel model;
    private DBhelper dBhelper;

    public LoginPresenter(ILogin.ILoginView view) {
        this.view = view;
        this.model = new LoginModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void onLoginButtonClicked(String username, String password, View v) throws SQLException, ClassNotFoundException {
        model = new LoginModel(username, password);
        if (model.validateCredentials(model) == null) {
            boolean success = dBhelper.checkLoginCredentialsDB(model);
            if (success) {
                view.onSuccess("Logging in!",v);
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
        public Boolean checkLoginCredentialsDB(LoginModel model) throws ClassNotFoundException, SQLException {
            StrictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT user_username,user_password FROM login_table WHERE user_username=" + "'" + model.getUser_username() + "'AND user_password= " + "'" + model.getUser_password() + "'";
            Statement statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            if (resultSet.next()) {
                resultSet.close();
                statement.close();
                connection.close();
                return true;
            } else {
                resultSet.close();
                statement.close();
                connection.close();
                return false;
            }
        }
    }

}
