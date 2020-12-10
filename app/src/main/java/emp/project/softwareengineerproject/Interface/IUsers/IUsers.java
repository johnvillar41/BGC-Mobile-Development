package emp.project.softwareengineerproject.Interface.IUsers;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.UserModel;

public interface IUsers {
    interface IUsersView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProfile(UserModel model);

        void displayPopupUsers(List<UserModel> userList) throws InterruptedException;

        void goToAddPage();

        void displayProgressBar();

        void hideProgressBar();

        boolean makeTextViewsEdittable();

        void displayStatusMessage(String message);
    }

    interface IUsersPresenter {
        void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException;

        void onViewButtonClicked();

        void onAddButtonClicked();

        void onEditAccountButtonClicked(String id,String username,String password,String fullname);
    }

    interface IUsersService extends IServiceStrictMode {

        UserModel getUserProfileFromDB(String user_id) throws ClassNotFoundException, SQLException;

        List<UserModel>getUsersListFromDB() throws ClassNotFoundException, SQLException;

        boolean updateNewUserCredentials(UserModel model) throws SQLException;
    }
}
