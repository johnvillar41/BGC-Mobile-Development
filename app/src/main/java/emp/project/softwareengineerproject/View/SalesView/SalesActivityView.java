package emp.project.softwareengineerproject.View.SalesView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.sql.SQLException;
import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesPresenter;
import emp.project.softwareengineerproject.R;

public class SalesActivityView extends AppCompatActivity implements ISales.ISalesView {
    private TextView txtBalance;
    private ImageView image_create_sale;
    private CircleImageView image_view_transactions;
    private ISales.ISalesPresenter presenter;
    private ProgressIndicator progressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sales_view);

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
        txtBalance = findViewById(R.id.txt_balance);
        image_create_sale = findViewById(R.id.image_create_sale);
        image_view_transactions = findViewById(R.id.image_view_transactions);
        progressIndicator = findViewById(R.id.progressBar_Sales);

        //Using Glide library to load images to avoid crash
        Glide.with(this).load(R.drawable.ic_money).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_create_sale);
        Glide.with(this).load(R.drawable.ic_list).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_view_transactions);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter = new SalesPresenter(this,SalesActivityView.this);

        presenter.onLoadPage();

        image_create_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateSaleClicked();
            }
        });
        image_view_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onViewTransactionClicked();
            }
        });

    }

    @Override
    protected void onResume() {
        try {
            presenter.onLoadPage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void displayTotalBalance(String totalBalance) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        txtBalance.setText(decimalFormat.format(Integer.parseInt(totalBalance)));
    }

    @Override
    public void goToSaleActivity() {
        Intent intent = new Intent(this, SalesAddActivityView.class);
        startActivity(intent);
    }

    @Override
    public void goToTransActionActivity() {
        Intent intent = new Intent(this, SalesTransactionView.class);
        startActivity(intent);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}