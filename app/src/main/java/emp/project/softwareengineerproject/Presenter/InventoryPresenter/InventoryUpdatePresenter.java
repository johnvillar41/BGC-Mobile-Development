package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    private IUpdateInventory.IUupdateInventoryView view;
    private IUpdateInventory.IUpdateInventoryService service;
    public static boolean isAddProductClicked = false;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view, IUpdateInventory.IUpdateInventoryService service) {
        this.view = view;

        this.service = service;
    }

    @Override
    public void onCancelButtonClicked() {
        view.goBack();
    }

    public static final String EMPTY_PRODUCT_NAME = "Empty product name!";
    public static final String EMPTY_PRODUCT_DESCRIPTION = "Empty product description!";
    public static final String EMPTY_PRODUCT_PRICE = "Empty product price";
    public static final String EMPTY_PRODUCT_STOCKS = "Empty product stocks";
    public static final String EMPTY_PRODUCT_CATEGORY = "Empty product category";
    public static final String EMPTY_PICTURE = "Empty product picture";
    public static final String SUCCESSFULL_MESSAGE = "Product Added Successfully!";
    public static final String ZERO_VALUE_PRICE = "Price value must not be zero!";
    private static final String SUCCESSFULL_UPDATE_PRODUCT = "Successfully Updated Product!";

    @Override
    public void onSaveProductButtonClicked(String product_id,
                                           TextInputLayout editText_productTitle,
                                           TextInputLayout txt_product_description,
                                           TextInputLayout txt_product_Price,
                                           TextInputLayout txt_product_Stocks,
                                           InputStream upload_picture,
                                           AutoCompleteTextView txt_product_category, final View v) {

    }

    @Override
    public void onAddProductButtonClicked() {

    }

    @Override
    public void onPageLoadHints(InventoryModel model) {
        view.setHints(model);
    }

    @Override
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    @Override
    public void loadCategories() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                HashSet<String> categories = null;
                try {
                    categories = service.getCategories();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                List<String> categoryList = new ArrayList<>(categories);
                view.displayCategoryList(categoryList);
                view.hideProgressBar();
            }
        });
        thread.start();
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
