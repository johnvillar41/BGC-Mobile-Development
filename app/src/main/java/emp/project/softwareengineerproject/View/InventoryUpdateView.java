package emp.project.softwareengineerproject.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mysql.jdbc.Blob;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import emp.project.softwareengineerproject.CustomAdapters.GreenHouseRecyclerView;
import emp.project.softwareengineerproject.Interface.IUpdateInventory;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.Presenter.InventoryUpdatePresenter;
import emp.project.softwareengineerproject.R;

public class InventoryUpdateView extends AppCompatActivity implements IUpdateInventory.IUupdateInventoryView {
    private static final int IMAGE_PICK_CODE = 1000;
    private EditText editText_productTitle;
    private static ImageView imageView;
    private EditText txt_product_description;
    private EditText txt_product_Price;
    private EditText txt_product_Stocks;
    private Button btn_save;

    private InventoryUpdatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory_update_view);

        initViews();
    }

    @Override
    public void initViews() {
        presenter = new InventoryUpdatePresenter(this);

        editText_productTitle = findViewById(R.id.txt_product_name);
        imageView = findViewById(R.id.image_product);
        txt_product_description = findViewById(R.id.txt_product_description);
        txt_product_Price = findViewById(R.id.txt_product_Price);
        txt_product_Stocks = findViewById(R.id.txt_product_Stocks);
        btn_save = findViewById(R.id.btn_save);
        Button btn_cancel = findViewById(R.id.btn_back);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCancelButtonClicked();
            }
        });

        try {
            presenter.displayHints(GreenHouseRecyclerView.model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHints(final ProductModel model) throws SQLException {
        editText_productTitle.setHint(model.getProduct_name());
        txt_product_description.setHint(model.getProduct_description());
        txt_product_Price.setHint(String.valueOf(model.getProduct_price()));
        txt_product_Stocks.setHint(String.valueOf(model.getProduct_stocks()));

        Blob b = model.getProduct_picture();
        int blobLength;

        blobLength = (int) b.length();
        final byte[] blobAsBytes = b.getBytes(1, blobLength);
        Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
        Glide.with(this).load(btm).into(imageView);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    java.sql.Blob blob ;
                    blob = new SerialBlob(blobAsBytes);
                    java.sql.Blob finalBlob = blob;

                    presenter.onSaveButtonClicked(model.getProduct_id(), editText_productTitle.getText().toString(), txt_product_description.getText().toString(),
                            Long.parseLong(txt_product_Price.getText().toString()), Integer.parseInt(txt_product_Stocks.getText().toString()), finalBlob, v);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ImageButtonClicked();
            }
        });
    }

    @Override
    public void goBack() {
        this.finish();
    }

    @Override
    public void displayErrorMessage(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void loadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap originBitmap = null;
        Uri selectedImage = data.getData();
        InputStream imageStream;


        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK
                && null != data) {

            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (originBitmap != null) {
                imageView.setImageBitmap(originBitmap);
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                //String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            }
        }
    }

}