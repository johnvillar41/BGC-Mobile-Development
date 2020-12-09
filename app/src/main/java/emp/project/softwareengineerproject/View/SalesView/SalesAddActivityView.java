package emp.project.softwareengineerproject.View.SalesView;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesAdd;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesAddPresenter;
import emp.project.softwareengineerproject.R;

public class SalesAddActivityView extends AppCompatActivity implements ISalesAdd.ISalesAddView {
    private ISalesAdd.ISalesAddPresenter presenter;
    private ProgressIndicator progressIndicator;
    private ProgressIndicator progressIndicatorCart;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sales_add_transaction_view);

        try {
            initViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() throws SQLException, ClassNotFoundException {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
        FloatingActionButton floatingActionButton_Cart = findViewById(R.id.fab_cart);
        recyclerView = findViewById(R.id.recyclerView_Sale);
        progressIndicator = findViewById(R.id.progressBar_AddSales);

        presenter = new SalesAddPresenter(this, this);
        presenter.directProductList();
        floatingActionButton_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCartButtonClicked(SalesModel.cartList);
            }
        });
    }

    @Override
    public void displayCart(List<InventoryModel> cartList) {
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
                presenter.onConfirmButtonClicked(v);
            }
        });

    }

    @Override
    public void onBackPressed() {
        SalesModel.cartList.clear();
        super.onBackPressed();
    }

    @Override
    public void displayProductRecyclerView(List<InventoryModel> list) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(SalesAddActivityView.this, LinearLayoutManager.VERTICAL, false);
        SalesAddRecyclerView adapter = new SalesAddRecyclerView(
                list, SalesAddActivityView.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void displaySuccessfullPrompt() {
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
    public void displayOnErrorMessage(String message, View v) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void displayProgressIndicator() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.hide();
    }

    @Override
    public void displayProgressIndicatorCart() {
        progressIndicatorCart.show();
    }

    @Override
    public void hideProgressIndicatorCart() {
        progressIndicatorCart.hide();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            SalesModel.cartList.clear();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}