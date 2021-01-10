package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesAddService;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    private ISalesAdd.ISalesAddView view;
    private ISalesAdd.ISalesAddService service;
    private SalesModel model;
    private WeakReference<Context> context;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view, Context context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = SalesAddService.getInstance();
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCartButtonClicked(List<InventoryModel> cartList) {
        view.displayCart(cartList);
    }

    @Override
    public void directProductList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<InventoryModel> productList = service.getProductListFromDB();
                    ((Activity) context.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProductRecyclerView(productList);
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ((Activity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressIndicator();
                    }
                });
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
                ((Activity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicatorCart();
                    }
                });
                boolean isValid = false;
                for (int i = 0; i < SalesModel.cartList.size(); i++) {
                    try {
                        if (!service.checkIfProductIsEnough(SalesModel.cartList.get(i).getProduct_id(),
                                SalesModel.cartList.get(i).getTotal_number_of_products()) ||
                                SalesModel.cartList.get(i).getTotal_number_of_products().equals(String.valueOf(0))) {
                            isValid = false;
                            ((Activity) context.get()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayOnErrorMessage("Products not enough!", v);
                                    view.hideProgressIndicatorCart();
                                }
                            });
                            break;
                        } else {
                            isValid = true;
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("MM");
                            LocalDateTime now_month = LocalDateTime.now();

                            model = new SalesModel(SalesModel.cartList.get(i).getProduct_picture(), SalesModel.cartList.get(i).getProduct_name(),
                                    SalesModel.cartList.get(i).getNewPrice(), SalesModel.cartList.get(i).getProduct_id(), SalesModel.cartList.get(i).getTotal_number_of_products(),
                                    String.valueOf(dtf.format(now)), String.valueOf(dtf_month.format(now_month)), LoginActivityView.USERNAME_VALUE);

                            service.insertOrderToDB(model);

                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if (isValid) {
                    ((Activity) context.get()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displaySuccessfullPrompt();
                            view.hideProgressIndicatorCart();
                        }
                    });
                }
            }
        });
        thread.start();
    }


}
