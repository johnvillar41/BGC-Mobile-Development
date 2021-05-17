package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.Bean.CartListModel;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    private ISalesAdd.ISalesAddView view;
    private ISalesAdd.ISalesAddService service;



    public SalesAddPresenter(ISalesAdd.ISalesAddView view, ISalesAdd.ISalesAddService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onCartButtonClicked(List<InventoryModel> cartList) {
        view.displayCart(cartList);
    }

    @Override
    public void loadProductList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressBar();
                try {
                    final List<InventoryModel> productList = service.getProductListFromDB();
                    view.displayProducts(productList);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.hideProgressBar();

            }
        });
        thread.start();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onConfirmButtonClicked(final View v) {

    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
