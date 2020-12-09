package emp.project.softwareengineerproject.Interface.ISales;

import android.view.View;

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

        void displaySuccessfullPrompt();

        void displayOnErrorMessage(String message, View v);

        void displayProgressIndicator();

        void hideProgressIndicator();

        void displayProgressIndicatorCart();

        void hideProgressIndicatorCart();
    }

    interface ISalesAddPresenter {
        void onCartButtonClicked(List<InventoryModel> salesModels);

        void directProductList() throws SQLException, ClassNotFoundException;

        void onConfirmButtonClicked(View v);
    }

    interface ISalesAddService extends IServiceStrictMode {
        boolean insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException;

        List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException;

        boolean checkIfProductIsEnough(String product_id,String total_orders) throws ClassNotFoundException, SQLException;

    }
}
