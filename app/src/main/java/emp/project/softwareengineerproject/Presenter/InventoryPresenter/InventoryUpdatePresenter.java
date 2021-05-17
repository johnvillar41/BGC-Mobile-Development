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

    @Override
    public void onSaveProductButtonClicked() {

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
