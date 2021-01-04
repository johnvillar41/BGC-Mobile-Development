package emp.project.softwareengineerproject.Interface;

import android.view.View;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface ILogin {
    interface ILoginView {
        /**
         * Initializes all the xml items
         */
        void initViews();

        /**
         * Displays a snackbar if the login successess
         * @param message message to be displayed in snackbar
         */
        void onSuccess(String message, View v);

        /**
         * Displays an error message if the user fails to login
         * @param message message to be displayed in the snackbar
         */
        void onError(String message, View v);

        /**
         * Displays the main page if the user successfully logs in
         */
        void goToMainPage();

        /**
         * Displays a progress loader in the log in screen
         */
        void displayProgressBar();

        /**
         * hides the progress loader in the log in screen
         */
        void hideProgressBar();
    }

    interface ILoginPresenter {
        /**
         * Handles the loginButton click event
         * @param username
         * @param password
         * @param view
         * Validates the user login editText
         */
        void onLoginButtonClicked(String username, String password, View view) throws SQLException, ClassNotFoundException;
    }

    interface ILoginService extends IServiceStrictMode{

        /**
         * Checks if the user is on the database
         * @param model userModel of the user logged in
         * @return will return true if the user logged in is in the database
         */
        boolean checkLoginCredentialsDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
