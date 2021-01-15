package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter;

import static emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter.EMPTY_NAME_FIELD;
import static emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter.EMPTY_PASSWORD_FIELD;
import static emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter.EMPTY_USERNAME_FIELD;

public class UsersPresenterTest {
    IUsers.IUsersView view;
    IUsers.IUsersService service;
    IUsers.IUsersPresenter presenter;

    @Before
    public void setUp() {
        view = new MockUsersView();
        service = new MockUsersService();
        presenter = new UsersPresenter(view, service);
    }

    private static final InputStream MOCK_IMAGE = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };

    @Test
    public void testDisplayUserPopup() throws InterruptedException {
        presenter.onViewButtonClicked();
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isDisplayPopupDisplayed);
    }

    @Test
    public void testDisplayUserProfile() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onPageDisplayProfile(MockUser.MOCK_ID.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isProfileDisplayed);
    }

    @Test
    public void testDeleteUserErrorMessage_DELETE_ERROR() throws InterruptedException {
        presenter.onCardViewLongClicked(MockUser.MOCK_USERNAME.getVal(), MockUser.MOCK_USERNAME.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isDisplayErrorDisplayed);
    }

    @Test
    public void testProgressBarShowing() throws InterruptedException {
        presenter.onCardViewLongClicked(MockUser.MOCK_USERNAME.getVal(), MockUser.MOCK_USERNAME.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isProgressBarShowing);
    }

    @Test
    public void testProgressBarHidden() throws InterruptedException {
        presenter.onCardViewLongClicked(MockUser.MOCK_USERNAME.getVal(), MockUser.MOCK_USERNAME.getVal());
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isProgressBarHidden);
    }

    @Test
    public void testDisplayGallery() {
        presenter.onImageClicked();
        Assert.assertTrue(((MockUsersView) view).isGalleryDisplayed);
    }

    @Test
    public void testUserUpdate() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                MockUser.MOCK_USERNAME.getVal(),
                MockUser.MOCK_PASSWORD.getVal(),
                MockUser.MOCK_FULLNAME.getVal(),
                null);
        Thread.sleep(1000);
        Assert.assertTrue(MockUsersService.isUpdated);
    }

    @Test
    public void testGoToAddPage() {
        presenter.onAddButtonClicked();
        Assert.assertTrue(((MockUsersView) view).isAddPageDisplayed);
    }

    @Test
    public void testEmptyUsernameErrorMessageDisplay() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                "",
                MockUser.MOCK_PASSWORD.getVal(),
                MockUser.MOCK_FULLNAME.getVal(),
                MOCK_IMAGE
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isErrorMessageDisplayed);
    }

    @Test
    public void testEmptyPasswordErrorMessageDisplay() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                MockUser.MOCK_USERNAME.getVal(),
                "",
                MockUser.MOCK_FULLNAME.getVal(),
                MOCK_IMAGE
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isErrorMessageDisplayed);
    }

    @Test
    public void testEmptyNameErrorMessageDisplay() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                MockUser.MOCK_USERNAME.getVal(),
                MockUser.MOCK_PASSWORD.getVal(),
                "",
                MOCK_IMAGE
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isErrorMessageDisplayed);
    }

    @Test
    public void testRemoveErrorMessageDisplay() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                MockUser.MOCK_USERNAME.getVal(),
                MockUser.MOCK_PASSWORD.getVal(),
                MockUser.MOCK_FULLNAME.getVal(),
                MOCK_IMAGE
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isErrorMessageRemoved);
    }

    @Test
    public void shouldPassIfEditIsSuccessfull() throws InterruptedException {
        presenter.onEditAccountButtonClicked(
                MockUser.MOCK_ID.getVal(),
                MockUser.MOCK_USERNAME.getVal(),
                MockUser.MOCK_PASSWORD.getVal(),
                MockUser.MOCK_FULLNAME.getVal(),
                MOCK_IMAGE
        );
        Thread.sleep(1000);
        Assert.assertTrue(((MockUsersView) view).isCredentialsRemoved);
        Assert.assertTrue(((MockUsersView) view).isActivityDestroyed);
    }

    enum MockUser {
        MOCK_ID("123"),
        MOCK_USERNAME("Mock_username"),
        MOCK_PASSWORD("Mock_password"),
        MOCK_FULLNAME("FullName");

        private String val;

        MockUser(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    static class MockUsersView implements IUsers.IUsersView {
        boolean isProfileDisplayed;
        boolean isDisplayPopupDisplayed;
        boolean isDisplayErrorDisplayed;
        boolean isDisplayStatusMessageSuccessDisplayed;
        boolean isProgressBarShowing;
        boolean isProgressBarHidden;
        boolean isGalleryDisplayed;
        boolean isAddPageDisplayed;

        boolean isErrorMessageDisplayed;
        boolean isErrorMessageRemoved;
        boolean isCredentialsRemoved;
        boolean isActivityDestroyed;

        @Override
        public void initViews() {

        }

        @Override
        public void displayProfile(UserModel model) {
            if (model.getUser_id().equals(MockUser.MOCK_ID.getVal())) {
                isProfileDisplayed = true;
            }
        }

        @Override
        public void displayPopupUsers(List<UserModel> userList) {
            if (userList.size() == 3) {
                isDisplayPopupDisplayed = true;
            }
        }

        @Override
        public void goToAddPage() {
            isAddPageDisplayed = true;
        }

        @Override
        public void displayProgressBar() {
            isProgressBarShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressBarHidden = true;
        }

        @Override
        public boolean makeTextViewsEdittable() {
            return false;
        }

        @Override
        public void displayStatusMessage(String message) {
            if (message.equals(UsersPresenter.DELETE_ERROR)) {
                isDisplayErrorDisplayed = true;
            }
            if (message.equals(UsersPresenter.DELETE_SUCCESS)) {
                isDisplayStatusMessageSuccessDisplayed = true;
            }
        }

        @Override
        public void loadImageFromGallery() {
            isGalleryDisplayed = true;
        }

        @Override
        public void removeUserCredentialsOnSharedPreferences() {
            isCredentialsRemoved = true;
        }

        @Override
        public void finishActivity() {
            isActivityDestroyed = true;
        }

        @Override
        public void setErrorOnUsername(String errorMessage) {
            if (errorMessage.equals(EMPTY_USERNAME_FIELD)) {
                isErrorMessageDisplayed = true;
            }
        }

        @Override
        public void setErrorOnPassword(String errorMessage) {
            if (errorMessage.equals(EMPTY_PASSWORD_FIELD)) {
                isErrorMessageDisplayed = true;
            }
        }

        @Override
        public void setErrorOnRealName(String errorMessage) {
            if (errorMessage.equals(EMPTY_NAME_FIELD)) {
                isErrorMessageDisplayed = true;
            }
        }

        @Override
        public void removeErrorOnUsername() {
            isErrorMessageRemoved = true;
        }

        @Override
        public void removeErrorOnPassword() {
            isErrorMessageRemoved = true;
        }

        @Override
        public void removeErrorOnRealName() {
            isErrorMessageRemoved = true;
        }
    }

    static class MockUsersService implements IUsers.IUsersService {
        static boolean isUpdated;
        static boolean isDeleted;

        @Override
        public UserModel getUserProfileFromDB(String user_id) {
            List<UserModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new UserModel(MockUser.MOCK_ID.getVal(), null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            for (UserModel model : mockDatabase) {
                if (model.getUser_id().equals(user_id)) {
                    return model;
                }
            }
            return null;
        }

        @Override
        public List<UserModel> getUsersListFromDB() {
            List<UserModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new UserModel(MockUser.MOCK_ID.getVal(), null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            return mockDatabase;
        }

        @Override
        public boolean updateNewUserCredentials(UserModel model) {
            List<UserModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new UserModel(MockUser.MOCK_ID.getVal(), null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            for (UserModel mock_model : mockDatabase) {
                if (mock_model.getUser_id().equals(model.getUser_id())) {
                    mock_model = new UserModel(null, null, null, null);
                    isUpdated = true;
                    return true;
                } else {
                    isUpdated = false;
                    return false;
                }
            }
            return false;
        }

        @Override
        public void deleteSpecificUserFromDB(String usernameToBeDeleted) {
            List<UserModel> mockDatabase = new ArrayList<>();
            mockDatabase.add(new UserModel(MockUser.MOCK_ID.getVal(), MockUser.MOCK_USERNAME.getVal(), null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            mockDatabase.add(new UserModel(null, null, null, null, (InputStream) null));
            for (int i = 0; i < mockDatabase.size(); i++) {
                if (mockDatabase.get(i).getUser_username().equals(usernameToBeDeleted)) {
                    isDeleted = true;
                    mockDatabase.remove(i);
                }
            }
        }
    }
}
