package emp.project.softwareengineerproject.Presenter.InventoryPresenter;

import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.PacketTooBigException;

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
                                           String txt_product_category, final View v) {
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
                    if (model.validateProductOnUpdate(arrTexts, upload_picture, txt_product_category, product_id) != null) {
                        service.updateProductToDB(model.validateProductOnUpdate(arrTexts, upload_picture, txt_product_category, product_id));
                        view.showCheckAnimation();
                        view.displayStatusMessage(SUCCESSFULL_UPDATE_PRODUCT, v);
                        view.hideProgressIndicator();
                    }

                } catch (final ClassNotFoundException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                } catch (final PacketTooBigException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                } catch (final SQLException e) {
                    view.displayStatusMessage(e.getMessage(), v);
                } catch (Exception e) {
                    view.displayStatusMessage(e.getMessage(), v);
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

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

                        //Zero value cases
                        case INVALID_PRODUCT_PRICE:
                            view.displayStatusMessage(ZERO_VALUE_PRICE, v);
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
                            InventoryModel model = new InventoryModel(
                                    product_name,
                                    product_description,
                                    Long.parseLong(product_price),
                                    Integer.parseInt(product_stocks),
                                    inputStream, product_category);
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
    public void onImageButtonClicked() {
        view.loadImageFromGallery();
    }

    @Override
    public void loadCategories() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgressIndicator();
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
                view.hideProgressIndicator();
            }
        });
        thread.start();
    }


}
