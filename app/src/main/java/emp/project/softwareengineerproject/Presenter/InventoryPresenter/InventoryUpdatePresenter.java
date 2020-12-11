package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PacketTooBigException;

import java.io.InputStream;
import java.sql.SQLException;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Services.InventoryService.InventoryUpdateService;
import emp.project.softwareengineerproject.View.InventoryView.InventoryUpdateView;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    IUpdateInventory.IUupdateInventoryView view;
    IUpdateInventory.IUpdateInventoryService service;
    InventoryModel model;
    InventoryUpdateView context;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view, InventoryUpdateView context) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = new InventoryUpdateService();
        this.context = context;
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
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showProgressIndicator();
                        }
                    });
                    TextInputLayout[] texts = new TextInputLayout[5];
                    texts[0] = editText_productTitle;
                    texts[1] = txt_product_description;
                    texts[2] = txt_product_Price;
                    texts[3] = txt_product_Stocks;
                    texts[4] = txt_product_category;

                    if (model.validateProductOnUpdate(texts, upload_picture, product_id) != null) {
                        service.updateProductToDB(model.validateProductOnUpdate(texts, upload_picture, product_id));
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.showCheckAnimation();
                                view.displayStatusMessage("Successfully Updated Product!", v);
                                view.hideProgressIndicator();
                            }
                        });
                    }

                } catch (final ClassNotFoundException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                } catch (final PacketTooBigException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                } catch (final SQLException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayStatusMessage(e.getMessage(), v);
                        }
                    });
                }
            }
        });
        thread.start();

    }

    @Override
    public void onAddProductButtonClicked(final TextInputLayout product_name,
                                          final TextInputLayout product_description,
                                          final TextInputLayout product_price,
                                          final TextInputLayout product_stocks,
                                          final InputStream inputStream,
                                          final TextInputLayout product_category,
                                          final View v) {
        //This is a lame execution of thread but this will work for now
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showProgressIndicator();
                    }
                });
                final InventoryModel[] modelFinal = new InventoryModel[1];
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        modelFinal[0] = model.validateProductOnAdd(product_name,
                                product_description,
                                product_price,
                                product_stocks,
                                inputStream, product_category);
                        if (modelFinal[0] != null) {
                            Thread thread1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        service.addNewProduct(model.validateProductOnAdd(product_name,
                                                product_description,
                                                product_price,
                                                product_stocks,
                                                inputStream, product_category));
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.showCheckAnimation();
                                                view.displayStatusMessage("Successfully creating product!", v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    } catch (final PacketTooBigException e) {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                view.displayStatusMessage(e.getMessage(), v);
                                                view.hideProgressIndicator();
                                            }
                                        });
                                    } catch (ClassNotFoundException | SQLException e) {
                                        context.runOnUiThread(new Runnable() {
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
                            context.runOnUiThread(new Runnable() {
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
