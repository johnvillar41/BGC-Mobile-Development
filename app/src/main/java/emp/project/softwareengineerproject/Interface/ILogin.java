package emp.project.softwareengineerproject.Interface;

import android.view.View;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.UserModel;

public interface ILogin {
    interface ILoginView {
        void initViews();

        void onSuccess(String message, View v);

        void onError(String message, View v);

        void goToMainPage();
    }

    interface ILoginPresenter {
        void onLoginButtonClicked(String username, String password, View view) throws SQLException, ClassNotFoundException;
    }

    interface ILoginService extends IServiceStrictMode{


        boolean checkLoginCredentialsDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
