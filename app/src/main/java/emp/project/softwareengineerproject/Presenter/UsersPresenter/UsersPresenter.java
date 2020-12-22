package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Services.UsersService.UsersService;
import emp.project.softwareengineerproject.View.LoginActivityView;
import emp.project.softwareengineerproject.View.UsersView.UsersActivityView;

public class UsersPresenter implements IUsers.IUsersPresenter {
    private UserModel model;
    private IUsers.IUsersService service;
    private IUsers.IUsersView view;
    private UsersActivityView context;

    public UsersPresenter(IUsers.IUsersView view, Context context) {
        this.view = view;
        this.model = new UserModel();
        this.service = new UsersService(this.model);
        this.context = (UsersActivityView) context;
    }

    @Override
    public void onPageDisplayProfile(final String user_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                try {
                    final UserModel model = service.getUserProfileFromDB(user_id);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProfile(model);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressBar();
                    }
                });
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
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                view.displayPopupUsers(userList);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
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
    public void onAddButtonClicked() {
        view.goToAddPage();
    }

    @Override
    public void onEditAccountButtonClicked(String id, String username, String password, String fullname) {
        if (!view.makeTextViewsEdittable()) {
            try {
                model = new UserModel(id, username, password, fullname);
                if (service.updateNewUserCredentials(model)) {
                    view.displayStatusMessage("Saving... Please Wait");
                    SharedPreferences sharedPreferences = context.getSharedPreferences(LoginActivityView.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    context.finish();
                    Intent intent = new Intent(context, LoginActivityView.class);
                    context.startActivity(intent);
                } else {
                    view.displayStatusMessage("Error!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCardViewLongClicked(final String usernameToBeDeleted, final String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                if (username.equals(usernameToBeDeleted)) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage("You cannot delete your own account!");
                            view.hideProgressBar();
                        }
                    });
                } else {
                    service.deleteSpecificUserFromDB(usernameToBeDeleted);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage("User Deleted!");
                            view.hideProgressBar();
                        }
                    });

                }
            }
        });
        thread.start();

    }


}
