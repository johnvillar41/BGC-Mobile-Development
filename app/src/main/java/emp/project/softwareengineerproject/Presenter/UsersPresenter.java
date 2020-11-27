package emp.project.softwareengineerproject.Presenter;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import emp.project.softwareengineerproject.Interface.IUsersActivity;
import emp.project.softwareengineerproject.Model.UserModel;

public class UsersPresenter implements IUsersActivity.IUsersPresenter {
    UserModel model;
    IUsersActivity.IUsersDBhelper dBhelper;
    IUsersActivity.IUsersView view;

    public UsersPresenter(IUsersActivity.IUsersView view) {
        this.view = view;
        this.dBhelper = new Dbhelper();
        this.model = new UserModel();
    }

    @Override
    public void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException {
       view.displayProfile(dBhelper.getUserProfileFromDB(user_id));
    }

    private class Dbhelper implements IUsersActivity.IUsersDBhelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/agt_db";
        private String USER = "admin";
        private String PASS = "admin";

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
            String sqlGetUserProfile = "SELECT * FROM login_table WHERE user_username="+"'"+user_username+"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetUserProfile);
            if (resultSet.next()) {
                return new UserModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getBlob(5));
            }else{
                return null;
            }
        }
    }
}
