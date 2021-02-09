package emp.project.softwareengineerproject;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersAddPresenter;

public class UsersAddPresenterTest {
    private static final String MOCK_USERNAME = "sample";
    private static final String MOCK_PASSWORD_1 = "sample";
    private static final String MOCK_PASSWORD_2 = "sample";
    private static final String MOCK_REAL_NAME = "sample";
    IUsersAdd.IUsersAddView view;
    IUsersAdd.IUsersAddService service;
    IUsersAdd.IUsersAddPresenter presenter;

    @Before
    public void setUp() {
        view = new MockAddUsersView();
        service = new MockAddUsersService();
        presenter = new UsersAddPresenter(view, service);
    }

    @Test
    public void testPassAddNewUserSuccess() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isNewSuccessfullPromptDisplayed);
    }

    @Test
    public void testAddUserToDB() throws InterruptedException, SQLException, ClassNotFoundException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersService) service).isUserAddedToDB);
    }

    @Test
    public void testDisplayErrorMessageEmptyUsername() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                "",
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isEmptyUserFieldMessageDisplayed);
    }

    @Test
    public void testDisplayErrorMessageEmptyPassword_1() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                "",
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isEmptyPassword_1FieldMessageDisplayed);
    }

    @Test
    public void testDisplayErrorMessageEmptyPassword_2() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                "",
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isEmptyPassword_2FieldMessageDisplayed);
    }

    @Test
    public void testDisplayErrorMessageEmptyName() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                "",
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                }, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isEmptyNameFieldMessageDisplayed);
    }

    @Test
    public void testDisplayErrorMessageEmptyPicture() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                null, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isEmptyProfilePictureMessageDisplayed);
    }

    @Test
    public void testRemoveErrors() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isErrorRemoved);
    }

    @Test
    public void testProgressIndicatoryShowing() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isProgressIndicatoryShowing);
    }

    @Test
    public void testProgressIndicatoryHidden() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onAddButtonClicked(
                MOCK_USERNAME,
                MOCK_PASSWORD_1,
                MOCK_PASSWORD_2,
                MOCK_REAL_NAME,
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockAddUsersView) view).isProgressIndicatoryNotShowing);
    }

    @Test
    public void testShowGallery() {
        presenter.onImageButtonClicked();
        Assert.assertTrue(((MockAddUsersView)view).isGalleryShowing);
    }

    static class MockAddUsersView implements IUsersAdd.IUsersAddView {
        boolean isNewSuccessfullPromptDisplayed;
        boolean isEmptyUserFieldMessageDisplayed;
        boolean isEmptyPassword_1FieldMessageDisplayed;
        boolean isEmptyPassword_2FieldMessageDisplayed;
        boolean isEmptyNameFieldMessageDisplayed;
        boolean isEmptyProfilePictureMessageDisplayed;
        boolean isErrorRemoved;
        boolean isProgressIndicatoryShowing;
        boolean isProgressIndicatoryNotShowing;
        boolean isGalleryShowing;

        @Override
        public void initViews() {

        }

        @Override
        public void loadImageFromGallery() {
            isGalleryShowing = true;
        }

        @Override
        public void displayStatusMessage(String message, View v) {
            switch (message) {
                case UsersAddPresenter.EMPTY_USERNAME_FIELD:
                    isEmptyUserFieldMessageDisplayed = true;
                    break;
                case UsersAddPresenter.EMPTY_PASSWORD_FIELD:
                    isEmptyPassword_1FieldMessageDisplayed = true;
                    break;
                case UsersAddPresenter.EMPTY_PASSWORD_2_FIELD:
                    isEmptyPassword_2FieldMessageDisplayed = true;
                    break;
                case UsersAddPresenter.EMPTY_REAL_NAME_FIELD:
                    isEmptyNameFieldMessageDisplayed = true;
                    break;
                case UsersAddPresenter.EMPTY_PROFILE_PIC_FIELD:
                    isEmptyProfilePictureMessageDisplayed = true;
                    break;
            }
        }

        @Override
        public void displayProgressBar() {
            isProgressIndicatoryShowing = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressIndicatoryNotShowing = true;
        }

        @Override
        public void displayCheckAnimation() {
            isNewSuccessfullPromptDisplayed = true;
        }

        @Override
        public void setErrorUserName(String errorMessage) {
            if (errorMessage.equals(UsersAddPresenter.EMPTY_USERNAME_FIELD)) {
                isEmptyUserFieldMessageDisplayed = true;
            }
        }

        @Override
        public void setErrorPassword(String errorMessage) {
            if (errorMessage.equals(UsersAddPresenter.EMPTY_PASSWORD_FIELD)) {
                isEmptyPassword_1FieldMessageDisplayed = true;
            }
        }

        @Override
        public void setErrorPassword_2(String s) {
            if (s.equals(UsersAddPresenter.EMPTY_PASSWORD_2_FIELD)) {
                isEmptyPassword_2FieldMessageDisplayed = true;
            }
        }

        @Override
        public void setErrorRealName(String empty_name) {
            if (empty_name.equals(UsersAddPresenter.EMPTY_REAL_NAME_FIELD)) {
                isEmptyProfilePictureMessageDisplayed = true;
            }
        }

        @Override
        public void removeErrorUsername() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorPassword() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorPassword_2() {
            isErrorRemoved = true;
        }

        @Override
        public void removeErrorRealName() {
            isErrorRemoved = true;
        }
    }

    static class MockAddUsersService implements IUsersAdd.IUsersAddService {
        boolean isUserAddedToDB;

        @Override
        public void insertNewUserToDB(UserModel model) {
            if (model.getUser_username().equals(MOCK_USERNAME) &&
                    model.getUser_password().equals(MOCK_PASSWORD_2) &&
                    model.getUser_full_name().equals(MOCK_REAL_NAME) &&
                    model.getUploadUserImage() != null
            ) {
                isUserAddedToDB = true;
            } else {
                isUserAddedToDB = false;
            }
        }
    }
}
