package emp.project.softwareengineerproject.Model;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.InputStream;
import java.io.Serializable;

public class InventoryModel implements Serializable {
    String product_id, product_name, product_description;
    long product_price;
    Blob product_picture;
    int product_stocks;
    InputStream upload_picture;
    String product_category;

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

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_price(long product_price) {
        this.product_price = product_price;
    }

    public void setProduct_stocks(int product_stocks) {
        this.product_stocks = product_stocks;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public InventoryModel validateUpdate(TextInputLayout editText_productTitle,
                                         TextInputLayout txt_product_description,
                                         TextInputLayout txt_product_Price,
                                         TextInputLayout txt_product_Stocks, String product_id, InputStream product_picture,
                                         TextInputLayout txt_product_category) {
        if (editText_productTitle.getEditText().getText().toString().isEmpty()) {
            setProduct_name((((TextInputLayout) editText_productTitle).getHint().toString()));
        } else {
            setProduct_name(editText_productTitle.getEditText().getText().toString());
        }

        if (txt_product_description.getEditText().getText().toString().isEmpty()) {
            setProduct_description(((TextInputLayout) txt_product_description).getHint().toString());
        } else {
            setProduct_description(txt_product_description.getEditText().getText().toString());
        }

        if (txt_product_Price.getEditText().getText().toString().isEmpty()) {
            setProduct_price(Long.parseLong(((TextInputLayout) txt_product_Price).getHint().toString()));
        } else {
            setProduct_price(Long.parseLong(txt_product_Price.getEditText().getText().toString()));
        }

        if ((txt_product_Stocks.getEditText().getText().toString().isEmpty())) {
            setProduct_stocks(Integer.parseInt(((TextInputLayout) txt_product_Stocks).getHint().toString()));
        } else {
            setProduct_stocks(Integer.parseInt(txt_product_Stocks.getEditText().getText().toString()));
        }

        if (txt_product_category.getEditText().getText().toString().isEmpty()) {
            setProduct_category(((TextInputLayout) txt_product_category).getHint().toString());
        } else {
            setProduct_category(txt_product_category.getEditText().getText().toString());
        }

        return new InventoryModel(product_id, getProduct_name(), getProduct_description(), getProduct_price(), getProduct_stocks(), product_picture, getProduct_category());
    }

    public InventoryModel validateProduct(TextInputLayout product_name,
                                          TextInputLayout product_description,
                                          TextInputLayout product_price,
                                          TextInputLayout product_stocks,
                                          InputStream product_picture,
                                          TextInputLayout product_category) {
        String message = null;
        if (product_name.getEditText().getText().toString().isEmpty()) {
            product_name.setError("Dont leave this empty!");
        }
        if (product_description.getEditText().getText().toString().isEmpty()) {
            product_description.setError("Dont leave this empty!");
        }
        if (product_price.getEditText().getText().toString().isEmpty()) {
            product_price.setError("Dont leave this empty!");
        }
        if (product_stocks.getEditText().getText().toString().isEmpty()) {
            product_stocks.setError("Dont leave this empty!");
        }
        if (product_picture == null) {
            message = "Dont leave image empty!";
        }
        if (product_category.getEditText().getText().toString().isEmpty()) {
            product_category.setError("Dont leave this empty!");
        }
        if (product_name.getError() == null &&
                product_description.getError() == null &&
                product_price.getError() == null &&
                product_stocks.getError() == null &&
                message ==null
                ) {
            return new InventoryModel(product_name.getEditText().getText().toString(),
                    product_description.getEditText().getText().toString(),
                    Long.parseLong(product_price.getEditText().getText().toString()),
                    Integer.parseInt(product_stocks.getEditText().getText().toString()),
                    product_picture, product_category.getEditText().getText().toString());
        } else {
            return null;
        }
    }
}
