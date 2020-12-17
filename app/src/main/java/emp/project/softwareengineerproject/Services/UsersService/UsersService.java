package emp.project.softwareengineerproject.Services.UsersService;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.DATABASE_CREDENTIALS;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.UserModel;

public class UsersService implements IUsers.IUsersService {

    private String DB_NAME = DATABASE_CREDENTIALS.DB_NAME.getDatabaseCredentials();
    private String USER = DATABASE_CREDENTIALS.USER.getDatabaseCredentials();
    private String PASS = DATABASE_CREDENTIALS.PASS.getDatabaseCredentials();

    private UserModel model;

    public UsersService(UserModel model) {
        this.model = model;
    }

    @Override
    public void strictMode() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public UserModel getUserProfileFromDB(String user_username) throws ClassNotFoundException, SQLException {
        strictMode();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlGetUserProfile = "SELECT * FROM login_table WHERE user_username=" + "'" + user_username + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlGetUserProfile);
        if (resultSet.next()) {
            return new UserModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getBlob(5));
        } else {
            return null;
        }
    }

    @Override
    public List<UserModel> getUsersListFromDB() throws ClassNotFoundException, SQLException {
        strictMode();
        List<UserModel> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlSearch = "SELECT * FROM login_table";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSearch);
        while (resultSet.next()) {
            model = new UserModel(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getBlob(5));
            list.add(model);
        }
        return list;
    }

    @Override
    public boolean updateNewUserCredentials(UserModel model) {
        boolean isSuccesfull;
        try {
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlUpdate = "UPDATE login_table " +
                    "SET user_username='" + model.getUser_username() + "',user_password='" + model.getUser_password() + "',user_name='" + model.getUser_full_name() + "'" +
                    "WHERE user_id='" + model.getUser_id() + "'";
            Statement statement = connection.createStatement();
            statement.execute(sqlUpdate);
            isSuccesfull = true;
        } catch (Exception e) {
            isSuccesfull = false;
        }
        return isSuccesfull;

    }
}
