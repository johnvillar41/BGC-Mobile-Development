package emp.project.softwareengineerproject.Interface.IUsers;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public interface IUsers {
    interface IUsersView extends IBaseView {
        void displayProfile(UserModel model);

        void displayPopupUsers(List<UserModel> userList);

        void goToAddPage();

        boolean makeTextViewsEdittable();

        void displayStatusMessage(String message);

        void loadImageFromGallery();

        void removeUserCredentialsOnSharedPreferences();

        void finishActivity();

        void setErrorOnUsername(String errorMessage);

        void setErrorOnPassword(String errorMessage);

        void setErrorOnRealName(String errorMessage);

        void removeErrorOnUsername();

        void removeErrorOnPassword();

        void removeErrorOnRealName();
    }

    interface IUsersPresenter extends IBasePresenter {
        void onPageDisplayProfile(String user_id);

        void onViewButtonClicked();

        void onAddButtonClicked();

        void onEditAccountButtonClicked(String id, String username, String password, String fullname, InputStream image_upload);

        void onCardViewLongClicked(String usernameToBeDeleted, String username);

        void onImageClicked();

    }

    interface IUsersService extends IServiceStrictMode {
        UserModel getUserProfileFromDB(String user_id) throws ClassNotFoundException, SQLException;

        List<UserModel> getUsersListFromDB() throws ClassNotFoundException, SQLException;

        boolean updateNewUserCredentials(UserModel model) throws SQLException;

        void deleteSpecificUserFromDB(String usernameToBeDeleted);
    }
}
