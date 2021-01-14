package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PacketTooBigException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;

public class InventoryUpdatePresenter implements IUpdateInventory.IUpdatePresenter {
    private IUpdateInventory.IUupdateInventoryView view;
    private IUpdateInventory.IUpdateInventoryService service;
    private InventoryModel model;

    public InventoryUpdatePresenter(IUpdateInventory.IUupdateInventoryView view, IUpdateInventory.IUpdateInventoryService service) {
        this.view = view;
        this.model = new InventoryModel();
        this.service = service;
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
                    view.showProgressIndicator();
                    TextInputLayout[] arrTexts = new TextInputLayout[5];
                    arrTexts[0] = editText_productTitle;
                    arrTexts[1] = txt_product_description;
                    arrTexts[2] = txt_product_Price;
                    arrTexts[3] = txt_product_Stocks;
                    arrTexts[4] = txt_product_category;
                    if (model.validateProductOnUpdate(arrTexts, upload_picture, product_id) != null) {
                        service.updateProductToDB(model.validateProductOnUpdate(arrTexts, upload_picture, product_id));
                        view.showCheckAnimation();
                        view.displayStatusMessage("Successfully Updated Product!", v);
                        view.hideProgressIndicator();
                    }

                } catch (final ClassNotFoundException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                } catch (final PacketTooBigException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                } catch (final SQLException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    private static final String EMPTY_PRODUCT_NAME = "Empty product name!";
    private static final String EMPTY_PRODUCT_DESCRIPTION = "Empty product description!";
    private static final String EMPTY_PRODUCT_PRICE = "Empty product price";
    private static final String EMPTY_PRODUCT_STOCKS = "Empty product stocks";
    private static final String EMPTY_PRODUCT_CATEGORY = "Empty product category";
    private static final String EMPTY_PICTURE = "Empty product picture";
    private static final String SUCCESSFULL_MESSAGE = "Product Added Successfully!";

    @Override
    public void onAddProductButtonClicked(String product_name,
                                          String product_description,
                                          String product_price,
                                          String product_stocks,
                                          InputStream inputStream,
                                          String product_category,
                                          View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] arrTextValues = new String[5];
                arrTextValues[0] = product_name;
                arrTextValues[1] = product_description;
                arrTextValues[2] = product_price;
                arrTextValues[3] = product_stocks;
                arrTextValues[4] = product_category;
                view.showProgressIndicator();

                List<InventoryModel.VALIDITY_PRODUCTS> validity_products = model.validateProductOnAdd(arrTextValues, inputStream);
                for (int i = 0; i < validity_products.size(); i++) {
                    switch (validity_products.get(i)) {
                        //Invalid
                        case EMPTY_PRODUCT_NAME:
                            view.setErrorProductName(EMPTY_PRODUCT_NAME);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PRODUCT_DESCRIPTION:
                            view.setErrorProductDescription(EMPTY_PRODUCT_DESCRIPTION);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PRODUCT_PRICE:
                            view.setErrorProductPrice(EMPTY_PRODUCT_PRICE);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PRODUCT_STOCKS:
                            view.setErrorProductStocks(EMPTY_PRODUCT_STOCKS);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PRODUCT_CATEGORY:
                            view.setErrorProductCategory(EMPTY_PRODUCT_CATEGORY);
                            view.hideProgressIndicator();
                            break;
                        case EMPTY_PRODUCT_IMAGE:
                            view.displayStatusMessage(EMPTY_PICTURE, v);
                            view.hideProgressIndicator();
                            break;

                        //Valid cases
                        case VALID_PRODUCT_NAME:
                            view.removeErrorProductName();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PRODUCT_DESCRIPTION:
                            view.removeErrorProductDescription();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PRODUCT_PRICE:
                            view.removeErrorProductPrice();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PRODUCT_STOCKS:
                            view.removeErrorProductStocks();
                            view.hideProgressIndicator();
                            break;
                        case VALID_PRODUCT_CATEGORY:
                            view.removeErrorProductCategory();
                            view.hideProgressIndicator();
                            break;
                        case VALID_ALL:
                            view.displayStatusMessage(SUCCESSFULL_MESSAGE, v);
                            view.showCheckAnimation();
                            InventoryModel model = new InventoryModel(product_name, product_description, Long.parseLong(product_price), Integer.parseInt(product_stocks), inputStream, product_category);
                            try {
                                service.addNewProduct(model);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            view.hideProgressIndicator();
                            break;
                    }
                }
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
