package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.InventoryModel;

public interface IInvetory {
    interface IinventoryView {
        void initViews() throws SQLException, ClassNotFoundException, InterruptedException;

        void displayRecyclerView(List<InventoryModel>[] productList);

        void goToAddProductPage();

        void showProgressDialog();

        void hideProgressDialog();

        void goToSearchPage();

        void refreshPage();

    }

    interface IinventoryPresenter {
        void getGreenHouseFromDB() throws InterruptedException, SQLException, ClassNotFoundException;

        void onAddProductButtonClicked();

        void searchButtonClicked();

        void onSwipeRefresh();

        void onCardViewLongClicked(String product_id,String product_name) throws SQLException, ClassNotFoundException;
    }

    interface DBhelper {
        void strictMode() throws ClassNotFoundException;

        List<InventoryModel>[] getProductFromDB() throws ClassNotFoundException, SQLException, InterruptedException;

        void deleteItem(String product_id,InventoryModel model) throws ClassNotFoundException, SQLException;
    }
}
