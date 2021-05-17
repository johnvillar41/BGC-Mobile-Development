package emp.project.softwareengineerproject.Interface.Inventory;

import android.view.View;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IBasePresenter;
import emp.project.softwareengineerproject.Interface.IBaseView;
import emp.project.softwareengineerproject.Interface.IServiceStrictMode;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public interface IUpdateInventory {
    interface IUupdateInventoryView extends IBaseView {
        void setHints(InventoryModel model);

        void goBack();

        void displayStatusMessage(String message, View v);

        void loadImageFromGallery();

        void showCheckAnimation();

        void setErrorProductName(String errorMessage);

        void setErrorProductDescription(String errorMessage);

        void setErrorProductPrice(String errorMessage);

        void setErrorProductStocks(String errorMessage);

        void setErrorProductCategory(String errorMessage);

        void removeErrorProductName();

        void removeErrorProductDescription();

        void removeErrorProductPrice();

        void removeErrorProductStocks();

        void removeErrorProductCategory();

        void displayCategoryList(List<String>categories);
    }

    interface IUpdatePresenter extends IBasePresenter {
        void onCancelButtonClicked();

        void onSaveProductButtonClicked();

        void onAddProductButtonClicked();

        void onPageLoadHints(InventoryModel model);

        void onImageButtonClicked();

        void loadCategories();
    }

    interface IUpdateInventoryService extends IServiceStrictMode {
        void updateProductToDB(InventoryModel model) throws SQLException, ClassNotFoundException;

        void addNewProduct(InventoryModel model) throws ClassNotFoundException, SQLException;

        HashSet<String> getCategories() throws ClassNotFoundException, SQLException;
    }
}
