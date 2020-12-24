package emp.project.softwareengineerproject.View.OrdersView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;
import emp.project.softwareengineerproject.R;

public class OrdersActivityView extends AppCompatActivity implements IOrders.IOrdersView, BottomNavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private IOrders.IOrdersPresenter presenter;
    private ProgressIndicator progressIndicator;
    private BottomNavigationView bottomNavigationView;
    private ImageView imageView;

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
        presenter = new OrdersPresenter(this, this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        recyclerView = findViewById(R.id.recyclerView_Orders);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        imageView = findViewById(R.id.empty_image);
        progressIndicator = findViewById(R.id.progress_bar_orders);
        progressIndicator.hide();

        presenter.onNavigationPendingOrders();


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
    public void displayRecyclerView(List<OrdersModel> orderList) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(OrdersActivityView.this, LinearLayoutManager.VERTICAL, false);
        OrdersRecyclerView adapter = new OrdersRecyclerView(
                orderList, OrdersActivityView.this,OrdersActivityView.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scheduleLayoutAnimation();
        if (adapter.getItemCount() == 0) {
            Glide.with(this).asBitmap().load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
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
        switch (item.getItemId()) {
            case R.id.page_finished_orders: {
                clicked = true;
                presenter.onNavigationFinishedOrders();
                break;
            }
            case R.id.page_cancelled_orders: {
                clicked = true;
                presenter.onNavigationCancelledOrders();
                break;
            }
            case R.id.page_pending_orders: {
                clicked = true;
                presenter.onNavigationPendingOrders();
                break;
            }
        }
        return clicked;
    }
}