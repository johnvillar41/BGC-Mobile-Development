package emp.project.softwareengineerproject;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.ILogin;
import emp.project.softwareengineerproject.Presenter.LoginPresenter;

public class LoginPresenterTest {
    private static final String MOCK_USER = "admin";
    private static final String MOCK_PASS = "admin";
    ILogin.ILoginView view;
    ILogin.ILoginService service;
    ILogin.ILoginPresenter presenter;

    @Before
    public void setUp() {
        view = new MockLoginView();
        service = new MockLoginService();
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void testLoginSuccess() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isSuccessfull);
    }

    @Test
    public void testLoginErrorOnEmptyUsername() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked("", MOCK_PASS, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isErrorDisplayedOnEmptyUsername);
    }

    @Test
    public void testLoginErrorOnEmptyPassword() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, "", null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isErrorDisplayedOnEmptyPassword);
    }

    @Test
    public void testLoginErrorOnEmptyBoth() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked("", "", null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isErrorDisplayedOnEmptyBoth);
    }

    @Test
    public void testDisplayProgressBar() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isProgressBarDisplaying);
    }

    @Test
    public void testHideProgressBar() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER, MOCK_PASS, null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isProgressBarHidden);
    }

    @Test
    public void testMainMenu() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.onLoginButtonClicked(MOCK_USER,MOCK_PASS,null);
        Thread.sleep(1000);
        Assert.assertTrue(((MockLoginView)view).isMainMenuShowing);
    }

    static class MockLoginView implements ILogin.ILoginView {
        boolean isSuccessfull;
        boolean isErrorDisplayedOnEmptyUsername;
        boolean isErrorDisplayedOnEmptyPassword;
        boolean isErrorDisplayedOnEmptyBoth;
        boolean isProgressBarDisplaying;
        boolean isProgressBarHidden;
        boolean isMainMenuShowing;

        @Override
        public void initViews() {

        }

        @Override
        public void onSuccess(String message, View v) {
            if (message.equals(LoginPresenter.SUCCESS_MESSAGE)) {
                isSuccessfull = true;
            }
        }

        @Override
        public void onError(String message, View v) {
            switch (message) {
                case LoginPresenter.EMPTY_USERNAME: {
                    isErrorDisplayedOnEmptyUsername = true;
                    break;
                }
                case LoginPresenter.EMPTY_PASSWORD: {
                    isErrorDisplayedOnEmptyPassword = true;
                    break;
                }
                case LoginPresenter.EMPTY_BOTH: {
                    isErrorDisplayedOnEmptyBoth = true;
                    break;
                }
            }

        }

        @Override
        public void goToMainPage() {
            isMainMenuShowing = true;
        }

        @Override
        public void displayProgressBar() {
            isProgressBarDisplaying = true;
        }

        @Override
        public void hideProgressBar() {
            isProgressBarHidden = true;
        }
    }

    static class MockLoginService implements ILogin.ILoginService {

        @Override
        public boolean checkLoginCredentialsDB(String username, String password) {
            if (model.getUser_username().equals(MOCK_USER) && model.getUser_password().equals(MOCK_PASS)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
