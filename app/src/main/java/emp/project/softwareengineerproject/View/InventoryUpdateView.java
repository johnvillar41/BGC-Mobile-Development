package emp.project.softwareengineerproject.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;

import emp.project.softwareengineerproject.CustomAdapters.GreenHouseRecyclerView;
import emp.project.softwareengineerproject.Interface.IUpdateInventory;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.Presenter.InventoryUpdatePresenter;
import emp.project.softwareengineerproject.R;

public class InventoryUpdateView extends AppCompatActivity implements IUpdateInventory.IUupdateInventoryView {
    private static final int RESULT_LOAD_IMG = 777;
    EditText editText_productTitle;
    ImageView imageView;
    EditText txt_product_description;
    EditText txt_product_Price;
    EditText txt_product_Stocks;
    Button btn_save;
    Button btn_cancel;

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
        presenter=new InventoryUpdatePresenter(this);

        editText_productTitle = findViewById(R.id.txt_product_name);
        imageView = findViewById(R.id.image_product);
        txt_product_description = findViewById(R.id.txt_product_description);
        txt_product_Price = findViewById(R.id.txt_product_Price);
        txt_product_Stocks = findViewById(R.id.txt_product_Stocks);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_back);

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

        final Blob b = model.getProduct_picture();
        final int blobLength;

        blobLength = (int) b.length();
        byte[] blobAsBytes = b.getBytes(1, blobLength);
        Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
        imageView.setImageBitmap(btm);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    presenter.onSaveButtonClicked(editText_productTitle.getText().toString(),txt_product_description.getText().toString(),
                            Long.parseLong(txt_product_Price.getText().toString()),Integer.parseInt(txt_product_Stocks.getText().toString()),model.getProduct_picture(),v);
                }catch (Exception e){
                    displayErrorMessage("Error!",v);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goToImageLibrary();
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
        startActivity(Intent.createChooser(intent, "Select image"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                Toast.makeText(this, "Image picked" +
                                columnIndex,
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            Log.d("ERROR",e.getMessage());
        }
    }
}