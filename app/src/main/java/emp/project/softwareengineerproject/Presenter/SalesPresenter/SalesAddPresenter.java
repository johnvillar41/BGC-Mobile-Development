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
    private SalesModel model;

    public static final String PRODUCT_NOT_ENOUGH = "Products not enough!";

    @Override
    public void onCartButtonClicked(List<InventoryModel> cartList) {
        view.displayCart(cartList);
    }
    public boolean isValid = false;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view, ISalesAdd.ISalesAddService service) {
        this.view = view;
        this.model = new SalesModel();
        this.service = service;
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.displayProgressIndicatorCart();
                for (int i = 0; i < CartListModel.getInstance().cartList.size(); i++) {
                    try {
                        if (!service.checkIfProductIsEnough(
                                CartListModel.getInstance().cartList.get(i).getProduct_id(),
                                CartListModel.getInstance().cartList.get(i).getTotal_number_of_products()) ||
                                CartListModel.getInstance().cartList.get(i).getTotal_number_of_products().equals(String.valueOf(0))) {
                            isValid = false;
                            view.displayOnErrorMessage(PRODUCT_NOT_ENOUGH, v);
                            view.hideProgressIndicatorCart();
                            break;
                        } else {
                            isValid = true;
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("MM");
                            LocalDateTime now_month = LocalDateTime.now();
                            model = new SalesModel(
                                    CartListModel.getInstance().cartList.get(i).getProduct_picture(),
                                    CartListModel.getInstance().cartList.get(i).getProduct_name(),
                                    CartListModel.getInstance().cartList.get(i).getNewPrice(),
                                    CartListModel.getInstance().cartList.get(i).getProduct_id(),
                                    CartListModel.getInstance().cartList.get(i).getTotal_number_of_products(),
                                    String.valueOf(dtf.format(now)), String.valueOf(dtf_month.format(now_month)),
                                    LoginActivityView.USERNAME_VALUE);
                            service.insertOrderToDB(model);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (isValid) {
                    view.displaySuccessfullPrompt();
                    view.hideProgressIndicatorCart();
                }
            }
        });
        view.hideProgressIndicatorCart();
        thread.start();
    }


    @Override
    public void initializeViews() {
        view.initViews();
    }
}
