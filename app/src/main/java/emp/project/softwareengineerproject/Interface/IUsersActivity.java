package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Model.UserModel;

public interface IUsersActivity {
    interface IUsersView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayProfile(UserModel model);
    }

    interface IUsersPresenter {
        void onPageDisplayProfile(String user_id) throws SQLException, ClassNotFoundException;
    }

    interface IUsersDBhelper {
        void strictMode() throws ClassNotFoundException;

        UserModel getUserProfileFromDB(String user_id) throws ClassNotFoundException, SQLException;
    }
}
