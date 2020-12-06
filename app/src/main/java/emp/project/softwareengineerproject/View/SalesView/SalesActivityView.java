package emp.project.softwareengineerproject.View.SalesView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.ISales.ISales;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesPresenter;
import emp.project.softwareengineerproject.R;

public class SalesActivityView extends AppCompatActivity implements ISales.ISalesView {
    private TextView txtBalance;
    private ImageView image_create_sale;
    private CircleImageView image_view_transactions;
    private ISales.ISalesPresenter presenter;


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

        //Using Glide library to load images to avoid crash
        Glide.with(this).load(R.drawable.ic_money).into(image_create_sale);
        Glide.with(this).load(R.drawable.ic_list).into(image_view_transactions);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        presenter = new SalesPresenter(this);

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
    public void displayTotalBalance(String totalBalance) {
        txtBalance.setText(totalBalance);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}