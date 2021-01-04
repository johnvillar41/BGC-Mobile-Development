package emp.project.softwareengineerproject.Interface.IUsers;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface IUsers {
    interface IUsersView {
        /**
         * Inititalizes all the items from the xml
         */
        void initViews() throws SQLException, ClassNotFoundException;

        /**
         * Displays the profile of the logged in used
         *
         * @param model Logged in user's model
         */
        void displayProfile(UserModel model);

        /**
         * Displays the list of all the users
         *
         * @param userList list of all the users
         */
        void displayPopupUsers(List<UserModel> userList) throws InterruptedException;

        /**
         * Displays the UsersAddActivityView
         */
        void goToAddPage();

        /**
         * Displays the linear progress loader
         */
        void displayProgressBar();

        /**
         * Hides the linear progress loader
         */
        void hideProgressBar();

        /**
         * Will make the edditTexts to be edditable so that the user can edit the values
         * on the editText
         *
         * @return returns a boolean to check whether the editTexts are enabled or not
         */
        boolean makeTextViewsEdittable();

        /**
         * Displays all the error messages and other status message the user or the program has
         * encountered
         *
         * @param message the message to be displayed in a snackbar
         */
        void displayStatusMessage(String message);

        void loadImageFromGallery();

    }

    interface IUsersPresenter {
        /**
         * Redirects the display of the profile when the activity is loaded
         *
         * @param user_id gets the user id of the user to be passed on the service class
         */
        void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException;

        /**
         * Handles the onViewButton Click event where it directs the popup display and puts the list
         * of all the users
         */
        void onViewButtonClicked();

        /**
         * Handles the add menu item click to add a new user
         */
        void onAddButtonClicked();

        /**
         * This handles the edit item menu click on the toolbar to edit the users credentials
         *
         * @param id
         * @param username
         * @param password
         * @param fullname This will get all the parameters and redirects it to the service class for the dataase transaction
         */
        void onEditAccountButtonClicked(String id, String username, String password, String fullname, InputStream image_upload);

        /**
         * This handles the cardView onclick listener of the user accounts displayed on the alertDialog to be either deleted or not
         *
         * @param usernameToBeDeleted id of user to be deleted
         * @param username            id of user logged in
         */
        void onCardViewLongClicked(String usernameToBeDeleted, String username);

        void onImageClicked();

    }

    interface IUsersService extends IServiceStrictMode {
        /**
         * Fetches the userProfile from the database
         *
         * @param user_id this will search the user_id on the database
         * @return and to be returned the logged in user profile
         */
        UserModel getUserProfileFromDB(String user_id) throws ClassNotFoundException, SQLException;

        /**
         * Fetches all the users list from the database
         */
        List<UserModel> getUsersListFromDB() throws ClassNotFoundException, SQLException;

        /**
         * Updates the new users profile credentials
         *
         * @return returns a boolean to check whether the update is successfull or not
         */
        boolean updateNewUserCredentials(UserModel model) throws SQLException;

        /**
         * Deletes user from the database
         *
         * @param usernameToBeDeleted id of user to be deleted
         */
        void deleteSpecificUserFromDB(String usernameToBeDeleted);
    }
}
