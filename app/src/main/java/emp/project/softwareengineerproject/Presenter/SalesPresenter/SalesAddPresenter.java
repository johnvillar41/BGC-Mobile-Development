package emp.project.softwareengineerproject.Presenter.SalesPresenter;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Services.SalesService.SalesAddService;
import emp.project.softwareengineerproject.View.SalesView.SalesAddActivityView;

public class SalesAddPresenter implements ISalesAdd.ISalesAddPresenter {

    private ISalesAdd.ISalesAddView view;
    private ISalesAdd.ISalesAddService service;
    private SalesModel model;
    private SalesAddActivityView context;

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
                            view.displayProductRecyclerView(productList);
                        }
                    });

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                context.runOnUiThread(new Runnable() {
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
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressIndicatorCart();
                    }
                });
                boolean isValid = false;
                for (int i = 0; i < SalesModel.cartList.size(); i++) {
                    try {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDateTime now = LocalDateTime.now();

                        DateTimeFormatter dtf_month = DateTimeFormatter.ofPattern("MM");
                        LocalDateTime now_month = LocalDateTime.now();

                        model = new SalesModel(SalesModel.cartList.get(i).getProduct_picture(), SalesModel.cartList.get(i).getProduct_name(),
                                SalesModel.cartList.get(i).getNewPrice(), SalesModel.cartList.get(i).getProduct_id(), SalesModel.cartList.get(i).getTotal_number_of_products(),
                                String.valueOf(dtf.format(now)), String.valueOf(dtf_month.format(now_month)));

                        isValid = service.checkIfProductIsEnough(SalesModel.cartList.get(i).getProduct_id(), SalesModel.cartList.get(i).getTotal_number_of_products());


                    } catch (Exception e) {
                        e.printStackTrace();
                        isValid = false;
                    }

                    if (SalesModel.cartList.get(i).getTotal_number_of_products().equals(String.valueOf(0)) || SalesModel.cartList.get(i).getTotal_number_of_products().equals(null)) {
                        view.displayOnErrorMessage("One or more products have zero value!", v);
                        isValid = false;
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.hideProgressIndicatorCart();
                            }
                        });
                        break;
                    }

                    if (isValid) {
                        try {
                            service.insertOrderToDB(model);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (isValid) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displaySuccessfullPrompt();
                            view.hideProgressIndicatorCart();
                        }
                    });
                } else {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayOnErrorMessage("Products not enough!", v);
                            view.hideProgressIndicatorCart();
                        }
                    });
                }

            }
        });
        thread.start();

    }


}
