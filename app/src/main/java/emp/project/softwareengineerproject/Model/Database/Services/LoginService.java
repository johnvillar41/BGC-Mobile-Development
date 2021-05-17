package emp.project.softwareengineerproject.Model.Database.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Presenter.LoginPresenter;

public class LoginService implements ILogin.ILoginService {

    private static LoginService SINGLE_INSTANCE = null;

    private LoginService() {

    }

    public static LoginService getInstance() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new LoginService();
        }
        return SINGLE_INSTANCE;
    }

    public void removeInstance() {
        SINGLE_INSTANCE = null;
    }

    @Override
    public boolean checkLoginCredentialsDB(String username, String password) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlSearch = "SELECT user_username,user_password,user_name FROM login_table WHERE user_username=? AND user_password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            LoginPresenter.USER_REAL_NAME = resultSet.getString("user_name");
            connection.close();
            preparedStatement.close();
            resultSet.close();
            return true;
        } else {
            connection.close();
            preparedStatement.close();
            resultSet.close();
            return false;
        }
    }
}
