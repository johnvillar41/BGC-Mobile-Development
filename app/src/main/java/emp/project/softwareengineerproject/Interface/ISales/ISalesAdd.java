package emp.project.softwareengineerproject.Interface.ISales;

import android.view.View;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;

public interface ISalesAdd {
    interface ISalesAddView extends IBaseView {
        void displayCart(List<InventoryModel> cartList);

        void displayProducts(List<InventoryModel> list);

        void displaySuccessfullPrompt();

        void displayOnErrorMessage(String message, View v);

        void displayProgressIndicatorCart();

        void hideProgressIndicatorCart();
    }

    interface ISalesAddPresenter extends IBasePresenter {
        void onCartButtonClicked(List<InventoryModel> salesModels);

        void loadProductList();

        void onConfirmButtonClicked(View v);
    }

    interface ISalesAddService extends IServiceStrictMode {
        boolean insertOrderToDB(SalesModel model) throws SQLException, ClassNotFoundException;

        List<InventoryModel> getProductListFromDB() throws ClassNotFoundException, SQLException;

        boolean checkIfProductIsEnough(String product_id,String total_orders) throws ClassNotFoundException, SQLException;

    }
}
