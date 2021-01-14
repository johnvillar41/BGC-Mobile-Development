package emp.project.softwareengineerproject.Interface.IUsers;

import android.view.View;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface IUsersAdd {
    interface IUsersAddView {
        /**
         * Initializes all the objects in the xml
         */
        void initViews();

        /**
         * This will display the users mobile gallery
         */
        void loadImageFromGallery();

        /**
         * Will display the current status of a transaction
         * @param message the message to be displayed in a snackbar
         */
        void displayStatusMessage(String message, View v);

        /**
         * Will display a linear progress loader while loading
         */
        void displayProgressIndicator();

        /**
         * hides the progress loader after loading
         */
        void hideProgressIndicator();

        /**
         * Displays the animation of a successfull transaction
         */
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

    interface IUsersAddPresenter {
        /**
         * Handles the click event of a user being added
         * @param username
         * @param password1
         * @param password2
         * @param realName
         * @param profileImage
         * @param v
* These params will be redirected to the service class after they are validated     */
        void onAddButtonClicked(String username, String password1, String password2, String realName, InputStream profileImage,View v) throws SQLException, ClassNotFoundException;

        /**
         * By Clicking on the image this will prompt the display of function loadImageFromGallery();
         */
        void onImageButtonClicked();
    }

    interface IUsersAddService extends IServiceStrictMode {
        /**
         * Inserts a new user to the database
         * @param model the object for the new user
         */
        void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
