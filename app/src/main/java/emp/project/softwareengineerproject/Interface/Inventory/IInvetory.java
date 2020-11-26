package emp.project.softwareengineerproject.Interface.Inventory;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IInvetory {
    interface IinventoryView {
        void initViews() throws SQLException, ClassNotFoundException, InterruptedException;

        void displayRecyclerView(List<ProductModel>[] productList);
        void displaySearchedProduct(List<ProductModel>searchedProduct);

        void goToAddProductPage();

        void showProgressDialog();
        void hideProgressDialog();

        void goToSearchPage();
    }

    interface IinventoryPresenter {
        void getGreenHouseFromDB() throws InterruptedException, SQLException, ClassNotFoundException;

        void onAddProductButtonClicked();

        void searchButtonClicked();
    }

    interface DBhelper {
        void strictMode() throws ClassNotFoundException;

        List<ProductModel>[] getGreenHouseDB() throws ClassNotFoundException, SQLException, InterruptedException;

    }
}
