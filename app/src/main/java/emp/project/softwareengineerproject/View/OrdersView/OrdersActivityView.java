package emp.project.softwareengineerproject.View.OrdersView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.io.File;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Model.Database.Services.OrdersService;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;
import emp.project.softwareengineerproject.R;

public class OrdersActivityView extends AppCompatActivity implements IOrders.IOrdersView, BottomNavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private IOrders.IOrdersPresenter presenter;
    private ProgressIndicator progressIndicator;
    private BottomNavigationView bottomNavigationView;
    private LottieAnimationView animationView_NoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_orders_view);
        initViews();


    }

    @Override
    public void initViews() {
        presenter = new OrdersPresenter(this, OrdersService.getInstance(new OrdersModel()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        recyclerView = findViewById(R.id.recyclerView_Orders);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        animationView_NoResult = findViewById(R.id.animationView_noResult);
        progressIndicator = findViewById(R.id.progress_bar_orders);
        progressIndicator.hide();

        presenter.onNavigationPendingOrders();


    }

    @Override
    public void displayProgressIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.show();
            }
        });
    }

    @Override
    public void hideProgressIndicator() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.hide();
            }
        });
    }

    @Override
    public void displayRecyclerView(List<OrdersModel> orderList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(OrdersActivityView.this, LinearLayoutManager.VERTICAL, false);
                OrdersRecyclerView adapter = new OrdersRecyclerView(
                        orderList, OrdersActivityView.this, OrdersActivityView.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.scheduleLayoutAnimation();
                if (adapter.getItemCount() == 0) {
                    animationView_NoResult.setVisibility(View.VISIBLE);
                } else {
                    animationView_NoResult.setVisibility(View.GONE);
                }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean clicked = false;
        if (item.getItemId() == R.id.page_finished_orders) {
            clicked = true;
            presenter.onNavigationFinishedOrders();
        } else if (item.getItemId() == R.id.page_cancelled_orders) {
            clicked = true;
            presenter.onNavigationCancelledOrders();
        } else if (item.getItemId() == R.id.page_pending_orders) {
            clicked = true;
            presenter.onNavigationPendingOrders();
        }
        return clicked;
    }
}