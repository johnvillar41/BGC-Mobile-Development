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
    }

    interface IUsersAddPresenter extends IBasePresenter {
    }

    interface IUsersAddService extends IServiceStrictMode {
        void insertNewUserToDB(UserModel model) throws ClassNotFoundException, SQLException;
    }
}
