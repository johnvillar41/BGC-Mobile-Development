package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

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
        this.model = new UserModel();
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
                    try {
                        view.displayPopupUsers(userList);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    public void onEditAccountButtonClicked(String id, String username, String password, String fullname, InputStream image_upload) {
        if (!view.makeTextViewsEdittable()) {
            model = new UserModel(id, username, password, fullname, image_upload);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (service.updateNewUserCredentials(model)) {
                            view.displayStatusMessage(SAVING);
                        } else {
                            view.displayStatusMessage(ERROR);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
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


}
