package emp.project.softwareengineerproject.Interface;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.ProductModel;

public interface IInvetory {
    interface IinventoryView{
        void initViews() throws SQLException, ClassNotFoundException;
        void displayRecyclerViewGreenHouse(List<ProductModel>productList);
    }
    interface IinventoryPresenter{
        void getGreenHouseFromDB() throws SQLException, ClassNotFoundException;
    }
    interface DBhelper{
        void strictMode() throws ClassNotFoundException;

        List<ProductModel> getGreenHouseDB() throws ClassNotFoundException, SQLException;
    }
}
