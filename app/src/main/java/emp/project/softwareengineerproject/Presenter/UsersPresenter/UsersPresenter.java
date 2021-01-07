package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersService;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class UsersPresenter implements IUsers.IUsersPresenter {
    private UserModel model;
    private IUsers.IUsersService service;
    private IUsers.IUsersView view;
    private WeakReference<Context> context;

    public UsersPresenter(IUsers.IUsersView view, Context context) {
        this.view = view;
        this.model = new UserModel();
        this.service = UsersService.getInstance(this.model);
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onPageDisplayProfile(final String user_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                try {
                    final UserModel model = service.getUserProfileFromDB(user_id);
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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
                ((Activity)context.get()).runOnUiThread(new Runnable() {
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
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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
    public void onEditAccountButtonClicked(String id, String username, String password, String fullname, InputStream image_upload) {
        if (!view.makeTextViewsEdittable()) {
            model = new UserModel(id, username, password, fullname, image_upload);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (service.updateNewUserCredentials(model)) {
                            ((Activity)context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Saving... Please Wait");
                                }
                            });
                            SharedPreferences sharedPreferences = context.get().getSharedPreferences(LoginActivityView.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            ((Activity)context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((Activity)context.get()).finish();
                                    Intent intent = new Intent(context.get(), LoginActivityView.class);
                                    context.get().startActivity(intent);
                                }
                            });
                        } else {
                            ((Activity)context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Error!");
                                }
                            });
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
    public void onCardViewLongClicked(final String usernameToBeDeleted, final String username) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity)context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                if (username.equals(usernameToBeDeleted)) {
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage("You cannot delete your own account!");
                            view.hideProgressBar();
                        }
                    });
                } else {
                    service.deleteSpecificUserFromDB(usernameToBeDeleted);
                    ((Activity)context.get()).runOnUiThread(new Runnable() {
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

    @Override
    public void onImageClicked() {
        view.loadImageFromGallery();
    }


}
