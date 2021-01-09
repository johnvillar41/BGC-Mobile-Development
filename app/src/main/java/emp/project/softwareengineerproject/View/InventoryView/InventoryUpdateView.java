package emp.project.softwareengineerproject.View.InventoryView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mysql.jdbc.Blob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.Inventory.IUpdateInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter;
import emp.project.softwareengineerproject.R;

@SuppressLint("StaticFieldLeak")
public class InventoryUpdateView extends AppCompatActivity implements IUpdateInventory.IUupdateInventoryView {

    private static final int IMAGE_PICK_CODE = 1000;
    private TextInputLayout editText_productTitle;
    private static CircleImageView IMAGE_VIEW;
    private TextInputLayout txt_product_description;
    private TextInputLayout txt_product_Price;
    private TextInputLayout txt_product_Stocks;
    private TextInputLayout txt_product_category;
    private Button btn_save;
    private Button btn_cancel;
    private Toolbar toolbar;
    private ProgressIndicator progressIndicator;
    private static InputStream FILE_INPUT_STREAM;

    private IUpdateInventory.IUpdatePresenter presenter;

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
        presenter = new InventoryUpdatePresenter(this, this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        editText_productTitle = findViewById(R.id.txt_product_name);
        IMAGE_VIEW = findViewById(R.id.image_product);
        txt_product_description = findViewById(R.id.txt_product_description);
        txt_product_Price = findViewById(R.id.txt_product_Price);
        txt_product_Stocks = findViewById(R.id.txt_product_Stocks);
        txt_product_category = findViewById(R.id.txt_product_category);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_back);
        progressIndicator = findViewById(R.id.progressBar_Inventory);
        progressIndicator.hide();
        Glide.with(this)
                .load(R.drawable.add_image)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .skipMemoryCache(true)
                .into(IMAGE_VIEW);
        try {
            presenter.onPageLoadHints(InventoryRecyclerView.PRODUCT_MODEL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHints(final InventoryModel model) throws SQLException {
        try {
            if (!model.getProduct_id().equals("-1")) {//This checks the id of the current product
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Update Product");
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
                editText_productTitle.setHint(model.getProduct_name());
                txt_product_description.setHint(model.getProduct_description());
                txt_product_Price.setHint(String.valueOf(model.getProduct_price()));
                txt_product_Stocks.setHint(String.valueOf(model.getProduct_stocks()));
                txt_product_category.setHint(model.getProduct_category());
                Blob b;
                b = model.getProduct_picture();
                int blobLength;

                blobLength = (int) b.length();
                final byte[] blobAsBytes = b.getBytes(1, blobLength);
                Glide.with(this)
                        .load(blobAsBytes)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                        .skipMemoryCache(true)
                        .into(IMAGE_VIEW);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            presenter.onSaveProductButtonClicked(model.getProduct_id(),
                                    editText_productTitle,
                                    txt_product_description,
                                    txt_product_Price,
                                    txt_product_Stocks, FILE_INPUT_STREAM,
                                    txt_product_category, v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Add Product");
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
                Intent intent = getIntent();
                btn_save.setText(intent.getStringExtra("Button_Name"));
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.onAddProductButtonClicked(
                                editText_productTitle,
                                txt_product_description,
                                txt_product_Price,
                                txt_product_Stocks, FILE_INPUT_STREAM,
                                txt_product_category, v);
                    }
                });
            }
        } catch (NullPointerException e) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Add Product");
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
            Intent intent = getIntent();
            btn_save.setText(intent.getStringExtra("Button_Name"));
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onAddProductButtonClicked(
                            editText_productTitle,
                            txt_product_description,
                            txt_product_Price,
                            txt_product_Stocks, FILE_INPUT_STREAM,
                            txt_product_category, v);
                }
            });
        }
        IMAGE_VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ImageButtonClicked();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCancelButtonClicked();
            }
        });

    }

    @Override
    public void goBack() {
        InventoryRecyclerView.PRODUCT_MODEL.setProduct_id("-1");
        this.finish();
    }

    @Override
    public void displayStatusMessage(String message, View v) {
        Snackbar snack = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(Color.parseColor("#f9b207"));
        View view = snack.getView();
        TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(InventoryUpdateView.this, R.color.black));
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
        tv.setGravity(Gravity.CENTER);
        snack.show();
    }

    @Override
    public void loadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void showProgressIndicator() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.hide();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void showCheckAnimation() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_check, null);
        dialogBuilder.setView(dialogView);

        AnimatedVectorDrawableCompat avd;
        AnimatedVectorDrawable avd2;
        ImageView imageView_done = dialogView.findViewById(R.id.done_check);
        Drawable drawable = imageView_done.getDrawable();
        if (drawable instanceof AnimatedVectorDrawableCompat) {
            avd = (AnimatedVectorDrawableCompat) drawable;
            avd.start();
        } else if (drawable instanceof AnimatedVectorDrawable) {
            avd2 = (AnimatedVectorDrawable) drawable;
            avd2.start();
        }

        final AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressIndicator.show();
                    }
                });
                Bitmap originBitmap = null;
                Uri selectedImage;
                try {
                    selectedImage = data.getData();
                } catch (NullPointerException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressIndicator.hide();
                        }
                    });
                    return;
                }

                InputStream imageStream;

                if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK) {

                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        originBitmap = BitmapFactory.decodeStream(imageStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (originBitmap != null) {
                        final Bitmap finalOriginBitmap = originBitmap;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                IMAGE_VIEW.setImageBitmap(finalOriginBitmap);
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap image = ((BitmapDrawable) IMAGE_VIEW.getDrawable()).getBitmap();
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        /**
                                         * Use png instead of jpeg so that images wont be corrupt but the file size of the images
                                         * will be the original size which will inflate the size of the database.
                                         */
                                        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                        FILE_INPUT_STREAM = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressIndicator.hide();
                                            }
                                        });
                                    }
                                });
                                thread1.start();
                            }
                        });
                    } else {
                        //Setting the static variables as null because somehow they change the images to be null
                        IMAGE_VIEW = null;
                        FILE_INPUT_STREAM = null;
                    }
                }
            }
        });
        thread.start();

    }

    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            InventoryRecyclerView.PRODUCT_MODEL.setProduct_id("-1");
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}