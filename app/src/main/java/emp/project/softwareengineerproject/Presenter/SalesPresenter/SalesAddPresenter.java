package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.StrictMode;

import emp.project.softwareengineerproject.Interface.EDatabaseCredentials;
import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.SalesModel;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    ISalesAdd.ISalesAddView view;
    ISalesAdd.ISalesAddService service;
    SalesModel model;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesAddService();
    }

    @Override
    public void onCartButtonClicked() {
        view.displayCart();
    }

    private class SalesAddService implements ISalesAdd.ISalesAddService {

        private String DB_NAME = EDatabaseCredentials.DB_NAME.getDatabaseCredentials();
        private String USER = EDatabaseCredentials.USER.getDatabaseCredentials();
        private String PASS = EDatabaseCredentials.PASS.getDatabaseCredentials();

        @Override
        public void strictMode() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public void insertOrderToDB() {

        }


    }
}
