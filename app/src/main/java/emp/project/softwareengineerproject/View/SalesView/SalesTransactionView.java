package emp.project.softwareengineerproject.View.SalesView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.Calendar;
import java.util.List;

import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesTransactionPresenter;
import emp.project.softwareengineerproject.R;

public class SalesTransactionView extends AppCompatActivity implements ISalesTransactions.ISalesTransactionsView {
    private ISalesTransactions.ISalesTransactionPresenter presenter;
    private RecyclerView recyclerView;
    private ProgressIndicator progressIndicator;
    private ImageView empty_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sales_transaction_view);
        initViews();

    }

    @Override
    public void initViews() {
        presenter = new SalesTransactionPresenter(this, this);
        recyclerView = findViewById(R.id.recyclerView_transactions);
        empty_image = findViewById(R.id.empty_image_transaction);
        progressIndicator = findViewById(R.id.progressBar_TransactionList);
        progressIndicator.hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        presenter.onLoadPageDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transactions_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.sort_by_date) {
            DatePickerDialog datePicker;
            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    calendar.set(year, month, dayOfMonth);
                    String dateString = sdf.format(calendar.getTime());
                    Toast.makeText(SalesTransactionView.this, dateString, Toast.LENGTH_LONG).show();
                    presenter.onSearchNotificationYesClicked(dateString);
                }
            }, year, month, day);
            datePicker.show();
        }
        if (item.getItemId() == R.id.show_all) {
            presenter.onShowAllListClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayRecyclerView(final List<SalesModel> transactionList) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManager
                        = new LinearLayoutManager(SalesTransactionView.this, LinearLayoutManager.VERTICAL, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SalesTransactionRecyclerView adapter = new SalesTransactionRecyclerView(
                                transactionList, SalesTransactionView.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        if (adapter.getItemCount() == 0) {
                            Glide.with(SalesTransactionView.this).load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(empty_image);
                            empty_image.setVisibility(View.VISIBLE);
                        } else {
                            empty_image.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void displayProgressIndicator() {
        progressIndicator.show();
    }

    @Override
    public void hideProgressIndicator() {
        progressIndicator.hide();
    }


}