package emp.project.softwareengineerproject.Model.Bean;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InventoryModel implements Serializable {
    private String product_id, product_name, product_description;
    private long product_price;
    private Blob product_picture;
    private int product_stocks;
    private InputStream upload_picture;
    private String product_category;

    public InventoryModel(String product_id, String product_name, String product_description,
                          long product_price, Blob product_picture, int product_stocks, String product_category) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_picture = product_picture;
        this.product_stocks = product_stocks;
        this.product_category = product_category;
    }


    public InventoryModel(String product_id, String product_name, String product_description, long product_price, int product_stocks, InputStream upload_picture, String product_category) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.upload_picture = upload_picture;
        this.product_category = product_category;
    }

    public InventoryModel(String product_name, String product_description, long product_price, int product_stocks, InputStream upload_picture, String product_category) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.upload_picture = upload_picture;
        this.product_category = product_category;
    }

    public InventoryModel() {
    }

    public void setProduct_price(long product_price) {
        this.product_price = product_price;
    }

    public InputStream getUpload_picture() {
        return upload_picture;
    }

    public String getProduct_category() {
        return product_category;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public long getProduct_price() {
        return product_price;
    }

    public Blob getProduct_picture() {
        return product_picture;
    }

    public int getProduct_stocks() {
        return product_stocks;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public InventoryModel validateProductOnUpdate(TextInputLayout[] text, InputStream upload_picture, String txt_category, String product_id) throws Exception {
        String[] textData = new String[4];
        for (int i = 0; i < text.length; i++) {
            if (text[i].getEditText().getText().toString().isEmpty()) {
                textData[i] = text[i].getHint().toString();
            } else {
                textData[i] = text[i].getEditText().getText().toString();
            }
        }

        if (txt_category.isEmpty()) {
            throw new Exception("Empty Category!");
        }

        return new InventoryModel(
                product_id,
                String.valueOf(textData[0]),
                String.valueOf(textData[1]),
                Long.parseLong(textData[2]),
                Integer.parseInt(textData[3]),
                upload_picture,
                String.valueOf(textData[4]));
    }

    public List<VALIDITY_PRODUCTS> validateProductOnAdd(String[] arrTexts, InputStream product_picture) {
        List<VALIDITY_PRODUCTS> validity = new ArrayList<>();
        boolean isProductNameValid = false;
        boolean isProductDescriptionValid = false;
        boolean isProductPriceValid = false;
        boolean isProductStocksValid = false;
        boolean isProductCategoryValid = false;
        boolean isProductPictureNull = false;
        boolean isPriceZero = false;
        for (int i = 0; i < arrTexts.length; i++) {
            if (arrTexts[i].isEmpty()) {
                switch (i) {
                    case 0:
                        validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_NAME);
                        break;
                    case 1:
                        validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_DESCRIPTION);
                        break;
                    case 2:
                        validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_PRICE);
                        break;
                    case 3:
                        validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_STOCKS);
                        break;
                    case 4:
                        validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_CATEGORY);
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        validity.add(VALIDITY_PRODUCTS.VALID_PRODUCT_NAME);
                        isProductNameValid = true;
                        break;
                    case 1:
                        validity.add(VALIDITY_PRODUCTS.VALID_PRODUCT_DESCRIPTION);
                        isProductDescriptionValid = true;
                        break;
                    case 2:
                        validity.add(VALIDITY_PRODUCTS.VALID_PRODUCT_PRICE);
                        isProductPriceValid = true;
                        break;
                    case 3:
                        validity.add(VALIDITY_PRODUCTS.VALID_PRODUCT_STOCKS);
                        isProductStocksValid = true;
                        break;
                    case 4:
                        validity.add(VALIDITY_PRODUCTS.VALID_PRODUCT_CATEGORY);
                        isProductCategoryValid = true;
                        break;
                }
            }
        }

        if (arrTexts[2].equals("0")) {
            validity.add(VALIDITY_PRODUCTS.INVALID_PRODUCT_PRICE);
            isPriceZero = true;
        }

        if (product_picture != null) {
            isProductPictureNull = true;
        } else {
            validity.add(VALIDITY_PRODUCTS.EMPTY_PRODUCT_IMAGE);
        }

        if (isProductNameValid && isProductDescriptionValid && isProductPriceValid && isProductStocksValid && isProductCategoryValid && isProductPictureNull && !isPriceZero) {
            validity.add(VALIDITY_PRODUCTS.VALID_ALL);
        }

        return validity;
    }

    public enum VALIDITY_PRODUCTS {
        EMPTY_PRODUCT_NAME,
        EMPTY_PRODUCT_IMAGE,
        EMPTY_PRODUCT_DESCRIPTION,
        EMPTY_PRODUCT_PRICE,
        EMPTY_PRODUCT_STOCKS,
        EMPTY_PRODUCT_CATEGORY,

        INVALID_PRODUCT_PRICE,

        VALID_PRODUCT_NAME,
        VALID_PRODUCT_DESCRIPTION,
        VALID_PRODUCT_PRICE,
        VALID_PRODUCT_STOCKS,
        VALID_PRODUCT_CATEGORY,

        VALID_ALL;
    }

    long newPrice;
    private String total_number_of_products;


    public long getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(long newPrice) {
        this.newPrice = newPrice;
    }

    public String getTotal_number_of_products() {
        return total_number_of_products;
    }

    public void setTotal_number_of_products(String total_number_of_products) {
        this.total_number_of_products = total_number_of_products;
    }

}
