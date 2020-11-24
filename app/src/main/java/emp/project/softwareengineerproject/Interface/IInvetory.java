package emp.project.softwareengineerproject.Interface;

import android.view.View;

import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IInvetory {
    interface IinventoryView{
        void initViews() throws SQLException, ClassNotFoundException;
        void displayRecyclerViewGreenHouse(List<ProductModel>productList);
        void displayErrorMessage(String message, View v);
    }
    interface IinventoryPresenter{
        void getGreenHouseFromDB() throws SQLException, ClassNotFoundException;

        void onSaveButtonClickedRecycler(String product_name, String product_description,
                                         long product_price, Blob product_picture, int product_stocks,View v) throws ClassNotFoundException, SQLException;
    }
    interface DBhelper{
        void strictMode() throws ClassNotFoundException;

        List<ProductModel> getGreenHouseDB() throws ClassNotFoundException, SQLException;

        void updateProductDB(ProductModel model) throws ClassNotFoundException, SQLException;
    }
}
