package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.view.View;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Set;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    private IUsersAdd.IUsersAddView view;
    private IUsersAdd.IUsersAddService service;
    private UserModel model;



    public UsersAddPresenter(IUsersAdd.IUsersAddView view, IUsersAdd.IUsersAddService service) {
        this.view = view;
        this.service = service;

    }




    @Override
    public void initializeViews() {
        view.initViews();
    }
}
