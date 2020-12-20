package emp.project.softwareengineerproject.Model;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("ALL")
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

    public InventoryModel validateProductOnUpdate(TextInputLayout[] text, InputStream upload_picture, String product_id) {
        String[] textData = new String[5];
        for (int i = 0; i < text.length; i++) {
            if (text[i].getEditText().getText().toString().isEmpty()) {
                textData[i] = text[i].getHint().toString();
            } else {
                textData[i] = text[i].getEditText().getText().toString();
            }
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

    public InventoryModel validateProductOnAdd(TextInputLayout[] textInputLayouts, InputStream product_picture) {
        boolean isValid = false;

        for (TextInputLayout txt : textInputLayouts) {
            if (txt.getEditText().getText().toString().isEmpty()) {
                txt.setError("Dont leave this empty!");
            } else {
                txt.setErrorEnabled(false);
            }
        }

        if (textInputLayouts[0].getError() == null &&
                textInputLayouts[1].getError() == null &&
                textInputLayouts[2].getError() == null &&
                textInputLayouts[3].getError() == null &&
                isValid == false) {
            return new InventoryModel(
                    textInputLayouts[0].getEditText().getText().toString(),
                    textInputLayouts[1].getEditText().getText().toString(),
                    Long.parseLong(textInputLayouts[2].getEditText().getText().toString()),
                    Integer.parseInt(textInputLayouts[3].getEditText().getText().toString()),
                    product_picture, textInputLayouts[4].getEditText().getText().toString());
        } else {
            return null;
        }
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
