package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PacketTooBigException;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryUpdateService;
import emp.project.softwareengineerproject.View.InventoryView.InventoryUpdateView;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    private IUpdateInventory.IUupdateInventoryView view;
    private IUpdateInventory.IUpdateInventoryService service;
    private InventoryModel model;
    private WeakReference<InventoryUpdateView> context;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view, InventoryUpdateView context) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = InventoryUpdateService.getInstance();
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCancelButtonClicked() {
        view.goBack();
    }


    @Override
    public void onSaveProductButtonClicked(final String product_id,
                                           final TextInputLayout editText_productTitle,
                                           final TextInputLayout txt_product_description,
                                           final TextInputLayout txt_product_Price,
                                           final TextInputLayout txt_product_Stocks,
                                           final InputStream upload_picture,
                                           final TextInputLayout txt_product_category, final View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showProgressIndicator();
                        }
                    });
                    TextInputLayout[] arrTexts = new TextInputLayout[5];
                    arrTexts[0] = editText_productTitle;
                    arrTexts[1] = txt_product_description;
                    arrTexts[2] = txt_product_Price;
                    arrTexts[3] = txt_product_Stocks;
                    arrTexts[4] = txt_product_category;

                    if (model.validateProductOnUpdate(arrTexts, upload_picture, product_id) != null) {
                        service.updateProductToDB(model.validateProductOnUpdate(arrTexts, upload_picture, product_id));
                        context.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.showCheckAnimation();
                                view.displayStatusMessage("Successfully Updated Product!", v);
                                view.hideProgressIndicator();
                            }
                        });
                    }

                } catch (final ClassNotFoundException e) {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                } catch (final PacketTooBigException e) {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                } catch (final SQLException e) {
                    context.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    @Override
    public void onAddProductButtonClicked(final TextInputLayout product_name,
                                          final TextInputLayout product_description,
                                          final TextInputLayout product_price,
                                          final TextInputLayout product_stocks,
                                          final InputStream inputStream,
                                          final TextInputLayout product_category,
                                          final View v) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                final TextInputLayout[] arrTextInputLayouts = new TextInputLayout[5];
                arrTextInputLayouts[0] = product_name;
                arrTextInputLayouts[1] = product_description;
                arrTextInputLayouts[2] = product_price;
                arrTextInputLayouts[3] = product_stocks;
                arrTextInputLayouts[4] = product_category;

                context.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showProgressIndicator();
                        final InventoryModel modelFinal = model.validateProductOnAdd(arrTextInputLayouts, inputStream);
                        if (modelFinal != null) {
                            Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        service.addNewProduct(modelFinal);
                                        context.get().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.showCheckAnimation();
                                                view.displayStatusMessage("Successfully creating product!", v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    } catch (final PacketTooBigException e) {
                                        context.get().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.displayStatusMessage(e.getMessage(), v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    } catch (ClassNotFoundException | SQLException e) {
                                        context.get().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.displayStatusMessage(e.getMessage(), v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    }
                                }
                            });
                            thread1.start();
                        } else {
                            context.get().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.displayStatusMessage("Error creating product!", v);
                                    view.hideProgressIndicator();
                                }
                            });
                        }
                    }
                });

            }
        });
        thread.start();
        thread.interrupt();
    }

    @Override
    public void onPageLoadHints(InventoryModel model) throws SQLException {
        view.setHints(model);
    }

    @Override
    public void ImageButtonClicked() {
        view.loadImageFromGallery();
    }


}
