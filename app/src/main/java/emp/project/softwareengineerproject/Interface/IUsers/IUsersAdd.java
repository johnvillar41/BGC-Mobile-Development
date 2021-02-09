package emp.project.softwareengineerproject.Interface.IUsers;

import android.view.View;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface IUsersAdd {
    interface IUsersAddView extends IBaseView {
        void loadImageFromGallery();

        void displayStatusMessage(String message, View v);

        void displayCheckAnimation();
        
        void setErrorUserName(String errorMessage);

        void setErrorPassword(String errorMessage);

        void setErrorPassword_2(String s);

        void setErrorRealName(String empty_name);

        void removeErrorUsername();

        void removeErrorPassword();

        void removeErrorPassword_2();

        void removeErrorRealName();
    }

    interface IUsersAddPresenter extends IBasePresenter {
        void onAddButtonClicked(String username, String password1, String password2, String realName, InputStream profileImage,View v);

        void onImageButtonClicked();
    }

    interface IUsersAddService extends IServiceStrictMode {
        void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
