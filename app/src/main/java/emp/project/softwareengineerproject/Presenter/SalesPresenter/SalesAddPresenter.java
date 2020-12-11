package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Services.SalesService.SalesAddService;
import emp.project.softwareengineerproject.View.SalesView.SalesAddActivityView;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    ISalesAdd.ISalesAddView view;
    ISalesAdd.ISalesAddService service;
    SalesModel model;
    SalesAddActivityView context;

    public SalesAddPresenter(ISalesAdd.ISalesAddView view, SalesAddActivityView context) {
        this.view = view;
        this.model = new SalesModel();
        this.service = new SalesAddService();
        this.context = context;
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
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProgressIndicator();
                            view.displayProductRecyclerView(productList);
                            view.hideProgressIndicator();
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
                List<Boolean> isSuccessful = new ArrayList<>();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicatorCart();
                    }
                });
                for (int i = 0; i < SalesModel.cartList.size(); i++) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();

                    DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("MM");
                    LocalDateTime now_month=LocalDateTime.now();

                    model = new SalesModel(SalesModel.cartList.get(i).getProduct_picture(), SalesModel.cartList.get(i).getProduct_name(),
                            SalesModel.cartList.get(i).getNewPrice(), SalesModel.cartList.get(i).getProduct_id(), SalesModel.cartList.get(i).getTotal_number_of_products(),
                            String.valueOf(dtf.format(now)),String.valueOf(dtf_month.format(now_month)));
                    try {
                        isSuccessful.add(service.insertOrderToDB(model));
                    } catch (SQLException e) {
                        view.displayOnErrorMessage(e.getMessage(), v);
                        isSuccessful.add(false);
                    } catch (ClassNotFoundException e) {
                        view.displayOnErrorMessage(e.getMessage(), v);
                        isSuccessful.add(false);
                    }


                }
                boolean finalSuccess = true;
                if (SalesModel.cartList.size() == 0) {
                    finalSuccess = false;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressIndicatorCart();
                            view.displayOnErrorMessage("Cart is Empty!",v);
                        }
                    });
                }

                for (int i = 0; i < isSuccessful.size(); i++) {
                    if (!isSuccessful.get(i)) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.displayOnErrorMessage("Number of products not enough", v);
                                view.hideProgressIndicatorCart();
                            }
                        });
                        finalSuccess = false;
                        break;
                    }
                }
                if (finalSuccess) {
                    context.runOnUiThread(new Runnable() {
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
