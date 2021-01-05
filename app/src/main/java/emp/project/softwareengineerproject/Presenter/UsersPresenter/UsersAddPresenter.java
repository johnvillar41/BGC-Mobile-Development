package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersAddService;
import emp.project.softwareengineerproject.View.UsersView.UsersAddActivityView;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    private IUsersAdd.IUsersAddView view;
    private IUsersAdd.IUsersAddService service;
    private UserModel model;
    private WeakReference<UsersAddActivityView> context;

    public UsersAddPresenter(IUsersAdd.IUsersAddView view, UsersAddActivityView context) {
        this.view = view;
        this.model = new UserModel();
        this.service = UsersAddService.getInstance();
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onAddButtonClicked(final TextInputLayout username, final TextInputLayout password1, final TextInputLayout password2,
                                   final TextInputLayout realName, final InputStream profileImage, final View v) {

        Thread thread = new Thread(new Runnable() {
            Boolean ifSuccess = true;

            @Override
            public void run() {
                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicator();
                        TextInputLayout[] arrTexts = new TextInputLayout[4];
                        arrTexts[0] = username;
                        arrTexts[1] = password1;
                        arrTexts[2] = password2;
                        arrTexts[3] = realName;
                        final UserModel newModel = model.validateAddUsers(arrTexts, profileImage);
                        if (newModel != null) {
                            final Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        service.insertNewUserToDB(newModel);
                                    } catch (final Exception e) {
                                        ifSuccess = false;
                                        context.get().runOnUiThread(new Runnable() {
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
                                thread1.interrupt();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ifSuccess = false;
                            context.get().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Error Adding User!", v);
                                    view.hideProgressIndicator();
                                }
                            });
                        }
                        if (ifSuccess) {
                            context.get().runOnUiThread(new Runnable() {
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
        thread.interrupt();

    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }


}
