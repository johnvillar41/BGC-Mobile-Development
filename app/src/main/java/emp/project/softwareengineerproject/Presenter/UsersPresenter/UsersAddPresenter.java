package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Services.UsersService.UsersAddService;
import emp.project.softwareengineerproject.View.UsersView.UsersAddActivityView;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    IUsersAdd.IUsersAddView view;
    IUsersAdd.IUsersAddService service;
    UserModel model;
    UsersAddActivityView context;

    public UsersAddPresenter(IUsersAdd.IUsersAddView view, UsersAddActivityView context) {
        this.view = view;
        this.model = new UserModel();
        this.service = new UsersAddService();
        this.context = context;
    }

    @Override
    public void onAddButtonClicked(final TextInputLayout username, final TextInputLayout password1, final TextInputLayout password2,
                                   final TextInputLayout realName, final InputStream profileImage, final View v) {

        Thread thread = new Thread(new Runnable() {
            Boolean ifSuccess = true;
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        final UserModel newModel = model.validateAddUsers(username, password1, password2, realName, profileImage);
                        if (newModel != null) {

                            final Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        service.insertNewUserToDB(newModel);
                                    } catch (final Exception e) {
                                        ifSuccess = false;
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.displayStatusMessage(e.getMessage(), v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    }
                                }
                            });
                            thread1.start();
                            try {
                                thread1.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ifSuccess = false;
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Error Adding User!", v);
                                    view.hideProgressIndicator();
                                }
                            });
                        }
                        if (ifSuccess) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Successfully Added new User!", v);
                                    view.hideProgressIndicator();
                                    view.displayCheckAnimation();
                                }
                            });
                        }
                    }
                });
            }
        });
        thread.start();

    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }


}
