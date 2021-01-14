package emp.project.softwareengineerproject.Presenter.UsersPresenter;

import android.view.View;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.Bean.UserModel;

public class UsersAddPresenter implements IUsersAdd.IUsersAddPresenter {
    private IUsersAdd.IUsersAddView view;
    private IUsersAdd.IUsersAddService service;
    private UserModel model;

    public static final String EMPTY_USERNAME_FIELD = "Empty Username Field!";
    public static final String EMPTY_PASSWORD_FIELD = "Empty Password Field!";
    public static final String EMPTY_PASSWORD_2_FIELD = "Empty Password 2 Field!";
    public static final String EMPTY_REAL_NAME_FIELD = "Empty Name Field!";
    public static final String EMPTY_PROFILE_PIC_FIELD = "Empty Picture!";
    public UsersAddPresenter(IUsersAdd.IUsersAddView view, IUsersAdd.IUsersAddService service) {
        this.view = view;
        this.model = new UserModel();
        this.service = service;

    }

    @Override
    public void onAddButtonClicked(final String username, final String password1, final String password2,
                                   final String realName, final InputStream profileImage, final View v) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressIndicator();
                String[] arrTexts = new String[4];
                arrTexts[0] = username;
                arrTexts[1] = password1;
                arrTexts[2] = password2;
                arrTexts[3] = realName;
                List<UserModel.VALIDITY> newModel = model.validateAddUsers(arrTexts, profileImage);
                for (int i = 0; i < newModel.size(); i++) {
                    switch (newModel.get(i)) {
                        /**
                         * This is for the Invalid Cases
                         */
                        case EMPTY_USERNAME:
                            view.displayStatusMessage(EMPTY_USERNAME_FIELD, v);
                            view.setErrorUserName(EMPTY_USERNAME_FIELD);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PASSWORD:
                            view.displayStatusMessage(EMPTY_PASSWORD_FIELD, v);
                            view.setErrorPassword(EMPTY_PASSWORD_FIELD);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PASSWORD_2:
                            view.displayStatusMessage(EMPTY_PASSWORD_2_FIELD, v);
                            view.setErrorPassword_2(EMPTY_PASSWORD_2_FIELD);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_REAL_NAME:
                            view.displayStatusMessage(EMPTY_REAL_NAME_FIELD, v);
                            view.setErrorRealName(EMPTY_REAL_NAME_FIELD);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PROFILE_PICTURE:
                            view.displayStatusMessage(EMPTY_PROFILE_PIC_FIELD, v);
                            view.hideProgressIndicator();
                            break;

                        /**
                         * This is for the Valid Cases
                         */
                        case VALID_USERNAME:
                            view.removeErrorUsername();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PASSWORD:
                            view.removeErrorPassword();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PASSWORD_2:
                            view.removeErrorPassword_2();
                            view.hideProgressIndicator();
                            break;
                        case VALID_REAL_NAME:
                            view.removeErrorRealName();
                            view.hideProgressIndicator();
                            break;
                        case EQUAL_PASSWORD:
                            view.removeErrorPassword();
                            view.removeErrorPassword_2();
                            break;

                        case VALID_REGISTER:
                            view.displayCheckAnimation();
                            UserModel model = new UserModel(username, password1, realName, profileImage);
                            try {
                                service.insertNewUserToDB(model);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            view.hideProgressIndicator();
                            break;
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }


}
