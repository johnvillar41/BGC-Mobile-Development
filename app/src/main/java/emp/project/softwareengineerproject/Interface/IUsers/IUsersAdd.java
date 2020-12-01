package emp.project.softwareengineerproject.Interface.IUsers;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.UserModel;

public interface IUsersAdd {
    interface IUsersAddView {
        void initViews();

        void loadImageFromGallery();

        void onStatusDisplayMessage(String message, View v);
    }

    interface IUsersAddPresenter {
        void onAddButtonClicked(TextInputLayout username, TextInputLayout password1, TextInputLayout password2, TextInputLayout realName, InputStream profileImage,View v) throws SQLException, ClassNotFoundException;

        void onImageButtonClicked();
    }

    interface IUsersAddService extends IServiceStrictMode {

        void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
