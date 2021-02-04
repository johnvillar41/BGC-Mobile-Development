package emp.project.softwareengineerproject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.Bean.ReportsModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Presenter.ReportsPresenter;

public class ReportsPresenterTest {
    private static final String MOCK_USERNAME = "SAMPLE";
    private static final String MOCK_USERNAME_2 = "SAMPLE_2";
    private static final String MOCK_USERNAME_3 = "SAMPLE_3";
    IReports.IReportsView view;
    IReports.IReportsService service;
    IReports.IReportsPresenter presenter;

    @Before
    public void setUp() {
        view = new MockReportsView();
        service = new MockReportsService();
        presenter = new ReportsPresenter(view, service);
    }

    @Test
    public void testShowProgressIndicator() throws InterruptedException {
        presenter.loadAdministratorValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressIndicatorShowing);
    }

    @Test
    public void testHideProgressIndicator() throws InterruptedException {
        presenter.loadAdministratorValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressIndicatorNotShowing);
    }

    @Test
    public void testShowProgressCircle() throws InterruptedException {
        presenter.loadChartValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressCircleShowing);
    }

    @Test
    public void testHideProgressCircle() throws InterruptedException {
        presenter.loadChartValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressCircleNotShowing);
    }

    @Test
    public void testDisplayProgressCircleUsers() throws InterruptedException {
        presenter.loadSortedAdministrators();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressCircleUsersShowing);
    }

    @Test
    public void testHideProgressCircleUsers() throws InterruptedException {
        presenter.loadSortedAdministrators();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isProgressCircleUsersNotShowing);
    }

    @Test
    public void testDisplayTotals() throws SQLException, ClassNotFoundException, InterruptedException {
        presenter.loadTotals(MOCK_USERNAME);
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isDisplayTotalsPass);
    }

    @Test
    public void testDisplayChart() throws InterruptedException {
        presenter.loadChartValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isChartDisplayed);
    }

    @Test
    public void testAdministratorValues() throws InterruptedException {
        presenter.loadAdministratorValues();
        Thread.sleep(1000);
        Assert.assertTrue(((MockReportsView) view).isAdministratorsDisplayed);
    }

    static class MockReportsView implements IReports.IReportsView {
        boolean isProgressIndicatorShowing;
        boolean isProgressIndicatorNotShowing;
        boolean isProgressCircleShowing;
        boolean isProgressCircleNotShowing;
        boolean isProgressCircleUsersShowing;
        boolean isProgressCircleUsersNotShowing;
        boolean isDisplayTotalsPass;
        boolean isChartDisplayed;
        boolean isRecyclerViewDisplayed;
        boolean isAdministratorsDisplayed;

        @Override
        public void initViews() {

        }

        @Override
        public void displayProgressIndicator() {
            isProgressIndicatorShowing = true;
        }

        @Override
        public void hideProgressIndicator() {
            isProgressIndicatorNotShowing = true;
        }

        @Override
        public void displayProgressCircle() {
            isProgressCircleShowing = true;
        }

        @Override
        public void hideProgressCircle() {
            isProgressCircleNotShowing = true;
        }

        @Override
        public void displayProgressCircle_Users() {
            isProgressCircleUsersShowing = true;
        }

        @Override
        public void hideProgressCircle_Users() {
            isProgressCircleUsersNotShowing = true;
        }

        @Override
        public void displayTotals(String total, String average, String ave_Monthly) {
            if (total.equals("1") && average.equals("2") && ave_Monthly.equals("3")) {
                isDisplayTotalsPass = true;
            } else {
                isDisplayTotalsPass = false;
            }
        }

        @Override
        public void displayChart(ReportsModel monthValues, String username) {
            if (monthValues.getUser_username().equals(MOCK_USERNAME)) {
                isChartDisplayed = true;
            }
        }

        @Override
        public void displayRecyclerView(List<UserModel> sortedUserList) {
            if (sortedUserList.size() == 3) {
                isRecyclerViewDisplayed = true;
            }
        }

        @Override
        public void displayAdministratorList(List<String> adminList) {
            if (adminList.size() == 3) {
                isAdministratorsDisplayed = true;
            } else {
                isAdministratorsDisplayed = false;
            }
        }
    }

    static class MockReportsService implements IReports.IReportsService {

        @Override
        public int[] computeAverages(String username) {
            int[] mockValues = new int[3];
            int mock_1 = 1;
            int mock_2 = 2;
            int mock_3 = 3;
            mockValues[0] = mock_1;
            mockValues[1] = mock_2;
            mockValues[2] = mock_3;
            return mockValues;
        }

        @Override
        public ReportsModel getMonthlySales(String username) {
            List<ReportsModel> mockDatabase = Arrays.asList(
                    new ReportsModel("null", MOCK_USERNAME, "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"),
                    new ReportsModel("null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"),
                    new ReportsModel("null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null")
            );
            ReportsModel model = new ReportsModel();
            for (int i = 0; i < mockDatabase.size(); i++) {
                if (mockDatabase.get(i).getUser_username().equals(MOCK_USERNAME)) {
                    model = mockDatabase.get(i);
                }
            }
            return model;
        }

        @Override
        public List<String> getListOfAdministrators() {
            return Arrays.asList(MOCK_USERNAME, MOCK_USERNAME_2, MOCK_USERNAME_3);
        }

        @Override
        public List<UserModel> getAdminsFromDB() {
            return Arrays.asList(
                    new UserModel(null, null, null, null),
                    new UserModel(null, null, null, null),
                    new UserModel(null, null, null, null));
        }
    }

}
