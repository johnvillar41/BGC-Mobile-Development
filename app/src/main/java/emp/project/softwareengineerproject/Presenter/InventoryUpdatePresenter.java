package emp.project.softwareengineerproject.Presenter;

import android.view.View;

import java.sql.Blob;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.IUpdateInventory;
import emp.project.softwareengineerproject.Model.ProductModel;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    IUpdateInventory.IUupdateInventoryView view;
    DBhelper dBhelper;
    ProductModel model;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view) {
        this.view = view;
        this.model = new ProductModel();
        this.dBhelper = new DBhelper();
    }

    @Override
    public void onCancelButtonClicked() {
        view.goBack();
    }

    @Override
    public void onSaveButtonClicked(String product_title, String product_description,
                                    long product_price, int product_stocks, Blob picture, View v) {
        model = new ProductModel(product_title, product_description, product_price,
                (com.mysql.jdbc.Blob) picture, product_stocks);
        String isValid = model.validateProduct(model);
        if (isValid == null) {
            dBhelper.updateProductToDB(model);
        }else{
            view.displayErrorMessage(model.validateProduct(model),v);
        }
    }

    @Override
    public void displayHints(ProductModel model) throws SQLException {
        view.setHints(model);
    }

    @Override
    public void goToImageLibrary() {
        view.loadImageFromGallery();
    }

    private class DBhelper implements IUpdateInventory.IDbHelper {

        @Override
        public void updateProductToDB(ProductModel model) {

        }
    }
}
