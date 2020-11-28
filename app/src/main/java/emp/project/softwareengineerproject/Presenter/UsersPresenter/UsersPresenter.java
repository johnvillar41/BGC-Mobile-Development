package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.UserModel;

public class UsersPresenter implements IUsers.IUsersPresenter {
    UserModel model;
    IUsers.IUsersDBhelper dBhelper;
    IUsers.IUsersView view;

    public UsersPresenter(IUsers.IUsersView view) {
        this.view = view;
        this.dBhelper = new Dbhelper();
        this.model = new UserModel();
    }

    @Override
    public void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException {
        view.displayProfile(dBhelper.getUserProfileFromDB(user_id));
    }

    @Override
    public void onViewButtonClicked() {
        try {
            view.displayPopupUsers(dBhelper.getUsersListFromDB());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddButtonClicked() {
        view.goToAddPage();
    }

    private class Dbhelper implements IUsers.IUsersDBhelper {

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
    }
}
