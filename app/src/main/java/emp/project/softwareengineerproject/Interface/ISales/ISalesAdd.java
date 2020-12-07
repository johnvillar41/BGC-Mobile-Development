package emp.project.softwareengineerproject.Interface.ISales;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;

public interface ISalesAdd {
    interface ISalesAddView {
        void initViews() throws SQLException, ClassNotFoundException;

        void displayCart(List<InventoryModel> cartList);

        void displayProductRecyclerView(List<InventoryModel> list);
    }

    interface ISalesAddPresenter {
        void onCartButtonClicked(List<InventoryModel> salesModels);

        void directProductList() throws SQLException, ClassNotFoundException;

        void onConfirmButtonClicked();
    }

    interface ISalesAddService extends IServiceStrictMode {
        void insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException;

        List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException;

    }
}
