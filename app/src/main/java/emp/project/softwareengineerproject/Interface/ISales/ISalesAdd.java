package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.InventoryModel;

public interface ISalesAdd {
    interface ISalesAddView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayCart();

        void displayProductRecyclerView(List<InventoryModel> list);
    }

    interface ISalesAddPresenter {
        void onCartButtonClicked();

        void directProductList() throws SQLException, ClassNotFoundException;
    }

    interface ISalesAddService extends IServiceStrictMode {
        void insertOrderToDB();

        List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException;
    }
}
