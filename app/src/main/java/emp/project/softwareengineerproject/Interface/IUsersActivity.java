package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.UserModel;

public interface IUsersActivity {
    interface IUsersView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProfile(UserModel model);

        void displayPopupUsers(List<UserModel> userList) throws InterruptedException;
    }

    interface IUsersPresenter {
        void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException;

        void onViewButtonClicked();
    }

    interface IUsersDBhelper {
        void strictMode() throws ClassNotFoundException;

        UserModel getUserProfileFromDB(String user_id) throws ClassNotFoundException, SQLException;

        List<UserModel>getUsersListFromDB() throws ClassNotFoundException, SQLException;
    }
}
