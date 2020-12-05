package emp.project.softwareengineerproject.Interface.ISales;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;

public interface ISalesAdd {
    interface ISalesAddView {
        void initViews();

        void displayCart();
    }

    interface ISalesAddPresenter {
        void onCartButtonClicked();
    }

    interface ISalesAddService extends IServiceStrictMode {
        void insertOrderToDB();
    }
}
