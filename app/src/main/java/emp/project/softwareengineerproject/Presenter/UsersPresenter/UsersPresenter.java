package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public class UsersPresenter implements IUsers.IUsersPresenter {
    private UserModel model;
    private IUsers.IUsersService service;
    private IUsers.IUsersView view;

    public static final String DELETE_ERROR = "You cannot delete your own account!";
    public static final String DELETE_SUCCESS = "User Deleted!";
    private static final String SAVING = "Saving... Please Wait";

    @Override
    public void onAddButtonClicked() {
        view.goToAddPage();
    }

    private static final String ERROR = "Error!";

    public UsersPresenter(IUsers.IUsersView view, IUsers.IUsersService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onPageDisplayProfile(final String user_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final UserModel model = service.getUserProfileFromDB(user_id);
                    view.displayProfile(model);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();
            }
        });
        thread.start();
    }

    @Override
    public void onViewButtonClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<UserModel> userList = service.getUsersListFromDB();
                    view.displayPopupUsers(userList);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    public static final String EMPTY_USERNAME_FIELD = "Empty Username Field!";
    public static final String EMPTY_PASSWORD_FIELD = "Empty Password Field!";
    public static final String EMPTY_NAME_FIELD = "Empty Name Field!";

    @Override
    public void onEditAccountButtonClicked(String id, String username, String password, String fullname, InputStream image_upload) {


    }

    @Override
    public void onCardViewLongClicked(String usernameToBeDeleted, String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                if (username.equals(usernameToBeDeleted)) {
                    view.displayStatusMessage(DELETE_ERROR);
                    view.hideProgressBar();
                } else {
                    service.deleteSpecificUserFromDB(usernameToBeDeleted);
                    view.displayStatusMessage(DELETE_SUCCESS);
                    view.hideProgressBar();
                }
            }
        });
        thread.start();
    }

    @Override
    public void onImageClicked() {
        view.loadImageFromGallery();
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
