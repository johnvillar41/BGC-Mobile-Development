package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.os.StrictMode;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.UserModel;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    IUsersAdd.IUsersAddView view;
    IUsersAdd.IUsersAddDBhelper dBhelper;
    UserModel model;

    public UsersAddPresenter(IUsersAdd.IUsersAddView view) {
        this.view = view;
        this.dBhelper = new DBhelper();
        this.model = new UserModel();
    }


    @Override
    public void onAddButtonClicked(TextInputLayout username, TextInputLayout password1, TextInputLayout password2, TextInputLayout realName, InputStream profileImage, View v) throws SQLException, ClassNotFoundException {
        UserModel newModel = model.validateAddUsers(username, password1, password2, realName, profileImage);
        if (newModel != null) {
            dBhelper.insertNewUserToDB(newModel);
            view.onStatusDisplayMessage("Successfully Added new User!", v);
        } else {
            view.onStatusDisplayMessage("Error Adding User!", v);
        }
    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    private class DBhelper implements IUsersAdd.IUsersAddDBhelper {

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
        public void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException {
            strictMode();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String inserNewUser = "INSERT INTO login_table(user_username,user_password,user_name,user_image)VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(inserNewUser);
            preparedStatement.setString(1, model.getUser_username());
            preparedStatement.setString(2, model.getUser_password());
            preparedStatement.setString(3, model.getUser_full_name());
            preparedStatement.setBlob(4, model.getUploadUserImage());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
    }
}
