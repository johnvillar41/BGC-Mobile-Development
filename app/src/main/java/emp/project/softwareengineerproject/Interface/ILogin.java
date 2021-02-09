package emp.project.softwareengineerproject.Interface;

import android.view.View;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface ILogin {
    interface ILoginView extends IBaseView {
        void onSuccess(String message, View v);

        void onError(String message, View v);

        void goToMainPage();
    }

    interface ILoginPresenter extends IBasePresenter {
        void onLoginButtonClicked(String username, String password, View view);
    }

    interface ILoginService extends IServiceStrictMode {

        boolean checkLoginCredentialsDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
