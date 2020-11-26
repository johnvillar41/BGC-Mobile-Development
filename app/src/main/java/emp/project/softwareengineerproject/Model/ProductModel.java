package emp.project.softwareengineerproject.Model;

import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.InputStream;
import java.io.Serializable;

public class ProductModel implements Serializable {
    String product_id, product_name, product_description;
    long product_price;
    Blob product_picture;
    int product_stocks;
    InputStream upload_picture;

    public ProductModel(String product_id, String product_name, String product_description,
                        long product_price, Blob product_picture, int product_stocks) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_picture = product_picture;
        this.product_stocks = product_stocks;
    }

    public InputStream getUpload_picture() {
        return upload_picture;
    }

    public ProductModel(String product_id, String product_name, String product_description, long product_price, int product_stocks, InputStream upload_picture) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_stocks = product_stocks;
        this.upload_picture = upload_picture;
    }

    public ProductModel() {
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

    public ProductModel validateUpdate(TextInputLayout editText_productTitle,
                                  TextInputLayout txt_product_description,
                                  TextInputLayout txt_product_Price,
                                  TextInputLayout txt_product_Stocks,String product_id,InputStream product_picture){
        if(editText_productTitle.getEditText().getText().toString().isEmpty()){
            setProduct_name((((TextInputLayout)editText_productTitle).getHint().toString()));
        }else{
            setProduct_name(editText_productTitle.getEditText().getText().toString());
        }

        if(txt_product_description.getEditText().getText().toString().isEmpty()){
            setProduct_description(((TextInputLayout)txt_product_description).getHint().toString());
        }else{
            setProduct_description(txt_product_description.getEditText().getText().toString());
        }

        if(txt_product_Price.getEditText().getText().toString().isEmpty()){
            setProduct_price(Long.parseLong(((TextInputLayout)txt_product_Price).getHint().toString()));
        }else{
            setProduct_price(Long.parseLong(txt_product_Price.getEditText().getText().toString()));
        }

        if((txt_product_Stocks.getEditText().getText().toString().isEmpty())){
            setProduct_stocks(Integer.parseInt(((TextInputLayout)txt_product_Stocks).getHint().toString()));
        }else{
            setProduct_stocks(Integer.parseInt(txt_product_Stocks.getEditText().getText().toString()));
        }
        return new ProductModel(product_id,getProduct_name(),getProduct_description(),getProduct_price(),getProduct_stocks(),product_picture);
    }

    public String validateProduct(ProductModel model) {
        String message = null;
        if (model.getProduct_name().isEmpty()) {
            message = "Product name field is empty!\n";
        }
        if (model.getProduct_description().isEmpty()) {
            message = "Product description is empty!\n";
        }
        if (String.valueOf(model.getProduct_price()).isEmpty()) {
            message = "Product price is empty!\n";
        }
        if (model.getUpload_picture() == null) {
            message = "Product Image is empty!\n";
        }
        if (String.valueOf(model.getProduct_stocks()).isEmpty()) {
            message = "Product stocks is empty!\n";
        }
        return message;
    }
}
