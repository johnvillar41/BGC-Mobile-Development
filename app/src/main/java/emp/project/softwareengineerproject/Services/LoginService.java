package emp.project.softwareengineerproject.Services;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Presenter.LoginPresenter;

public class LoginService implements ILogin.ILoginService {

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
        preparedStatement.setString(2, model.getUser_password());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            LoginPresenter.USER_REAL_NAME = resultSet.getString(4);
            return true;
        } else {

            return false;
        }
    }
}