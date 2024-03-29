package emp.project.softwareengineerproject.View.SalesView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.Bean.CartListModel;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesAddService;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesAddPresenter;
import emp.project.softwareengineerproject.R;

public class SalesAddActivityView extends AppCompatActivity implements ISalesAdd.ISalesAddView {
    private ISalesAdd.ISalesAddPresenter presenter;
    private ProgressIndicator progressIndicator;
    private ProgressIndicator progressIndicatorCart;
    private RecyclerView recyclerView;
    private LottieAnimationView animationView_Noresult;

    private static int numberOfDialogsOpen = 0;
    public boolean isUpdateButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sales_add_transaction_view);

        presenter = new SalesAddPresenter(this, SalesAddService.getInstance());
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
        FloatingActionButton floatingActionButton_Cart = findViewById(R.id.fab_cart);
        recyclerView = findViewById(R.id.recyclerView_Sale);
        progressIndicator = findViewById(R.id.progressBar_AddSales);
        animationView_Noresult = findViewById(R.id.animationView_noResult);

        presenter.loadProductList();
        floatingActionButton_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpdateButtonClicked = false;
                presenter.onCartButtonClicked(CartListModel.getInstance().cartList);
            }
        });
    }

    @Override
    public void displayCart(List<InventoryModel> cartList) {
        numberOfDialogsOpen++;
        if (numberOfDialogsOpen == 1) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();

            View dialogView = inflater.inflate(R.layout.custom_popup_cart, null);
            dialogBuilder.setView(dialogView);

            final RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView_cart);
            Button btn_back = dialogView.findViewById(R.id.btn_back);
            Button btn_confirm = dialogView.findViewById(R.id.btn_confirm);
            progressIndicatorCart = dialogView.findViewById(R.id.progressBar_Cart);
            progressIndicatorCart.hide();

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(SalesAddActivityView.this, LinearLayoutManager.VERTICAL, false);
            SalesAddRecyclerView2 adapter = new SalesAddRecyclerView2(
                    cartList, SalesAddActivityView.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.scheduleLayoutAnimation();

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUpdateButtonClicked = true;
                    presenter.onConfirmButtonClicked(v);
                }
            });

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (isUpdateButtonClicked) {
                        presenter.loadProductList();
                    }
                    CartListModel.getInstance().cartList.clear();
                    numberOfDialogsOpen = 0;
                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        CartListModel.getInstance().cartList.clear();
        numberOfDialogsOpen = 0;
        super.onBackPressed();
    }

    @Override
    public void displayProducts(List<InventoryModel> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(SalesAddActivityView.this, LinearLayoutManager.VERTICAL, false);
                SalesAddRecyclerView adapter = new SalesAddRecyclerView(
                        list, SalesAddActivityView.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();
                if (adapter.getItemCount() == 0) {
                    animationView_Noresult.setVisibility(View.VISIBLE);
                } else {
                    animationView_Noresult.setVisibility(View.GONE);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void displaySuccessfullPrompt() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SalesAddActivityView.this);
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
        });
    }

    @Override
    public void displayOnErrorMessage(String message, View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snack = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
                snack.getView().setBackgroundColor(Color.parseColor("#f9b207"));
                View view = snack.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(SalesAddActivityView.this, R.color.black));
                tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                tv.setGravity(Gravity.CENTER);
                snack.show();
            }
        });
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
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.show();
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.hide();
            }
        });
    }

    @Override
    public void displayProgressIndicatorCart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicatorCart.show();
            }
        });
    }

    @Override
    public void hideProgressIndicatorCart() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicatorCart.hide();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CartListModel.getInstance().cartList.clear();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}