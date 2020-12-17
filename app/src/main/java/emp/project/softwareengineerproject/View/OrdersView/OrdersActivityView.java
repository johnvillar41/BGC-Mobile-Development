package emp.project.softwareengineerproject.View.OrdersView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.List;

import emp.project.softwareengineerproject.Interface.IOrders;
import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.Presenter.OrdersPresenter;
import emp.project.softwareengineerproject.R;

public class OrdersActivityView extends AppCompatActivity implements IOrders.IOrdersView {
    private RecyclerView recyclerView;
    private IOrders.IOrdersPresenter presenter;
    private ProgressIndicator progressIndicator;

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
        progressIndicator = findViewById(R.id.progress_bar_orders);
        progressIndicator.hide();
        presenter.onPageLoad();
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
                orderList, OrdersActivityView.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}