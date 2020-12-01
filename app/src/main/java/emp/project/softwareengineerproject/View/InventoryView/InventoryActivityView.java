package emp.project.softwareengineerproject.View.InventoryView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;
import emp.project.softwareengineerproject.R;

public class InventoryActivityView extends AppCompatActivity implements IInvetory.IinventoryView {
    private RecyclerView recyclerView_GreenHouse, recyclerView_Hydroponics, recyclerView_others;

    private IInvetory.IinventoryPresenter presenter;
    private ProgressBar progressBar, progressBar_greenHouse, progressBar_hydroponics, progressBar_others;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CircleImageView image_empty_greenhouse, image_empty_hydroponics, image_empty_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory_view);

        try {
            initViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() throws SQLException, ClassNotFoundException, InterruptedException {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter = new InventoryPresenter(this);

        progressBar = findViewById(R.id.progressLoader);
        progressBar_greenHouse = findViewById(R.id.progress_bar_greenhouse);
        progressBar_hydroponics = findViewById(R.id.progress_bar_hydroponics);
        progressBar_others = findViewById(R.id.progress_bar_others);
        recyclerView_GreenHouse = findViewById(R.id.recyclerView_greenHouse);
        recyclerView_Hydroponics = findViewById(R.id.recyclerView_hydroPonics);
        recyclerView_others = findViewById(R.id.recyclerView_others);
        swipeRefreshLayout = findViewById(R.id.refresh);
        image_empty_greenhouse = findViewById(R.id.empty_image_greenhouse);
        image_empty_hydroponics = findViewById(R.id.empty_image_hydroponics);
        image_empty_others = findViewById(R.id.empty_image_others);
        presenter.getGreenHouseFromDB();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onSwipeRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        try {
            presenter.getGreenHouseFromDB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void displayRecyclerView(final List<InventoryModel>[] productList) {
        progressBar_greenHouse.setVisibility(View.VISIBLE);
        progressBar_hydroponics.setVisibility(View.VISIBLE);
        progressBar_others.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManagerGreenhouse
                        = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
                final LinearLayoutManager layoutManagerHydroponics
                        = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
                final LinearLayoutManager layoutManagerOthers
                        = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InventoryRecyclerView adapterGreenhouse = new InventoryRecyclerView(
                                InventoryActivityView.this, productList[0]);
                        recyclerView_GreenHouse.setLayoutManager(layoutManagerGreenhouse);
                        recyclerView_GreenHouse.setAdapter(adapterGreenhouse);
                        progressBar_greenHouse.setVisibility(View.INVISIBLE);
                        if (adapterGreenhouse.getItemCount() == 0) {
                            Glide.with(InventoryActivityView.this).load(R.drawable.no_notifications_logo).into(image_empty_greenhouse);
                            image_empty_greenhouse.setVisibility(View.VISIBLE);
                        } else {
                            image_empty_greenhouse.setVisibility(View.GONE);
                        }

                        InventoryRecyclerView adapterHydroponics = new InventoryRecyclerView(
                                InventoryActivityView.this, productList[1]);
                        recyclerView_Hydroponics.setLayoutManager(layoutManagerHydroponics);
                        recyclerView_Hydroponics.setAdapter(adapterHydroponics);
                        progressBar_hydroponics.setVisibility(View.INVISIBLE);
                        if (adapterHydroponics.getItemCount() == 0) {
                            Glide.with(InventoryActivityView.this).load(R.drawable.no_notifications_logo).into(image_empty_hydroponics);
                            image_empty_hydroponics.setVisibility(View.VISIBLE);
                        } else {
                            image_empty_hydroponics.setVisibility(View.GONE);
                        }

                        InventoryRecyclerView adapterOthers = new InventoryRecyclerView(
                                InventoryActivityView.this, productList[2]);
                        recyclerView_others.setLayoutManager(layoutManagerOthers);
                        recyclerView_others.setAdapter(adapterOthers);
                        progressBar_others.setVisibility(View.INVISIBLE);
                        if (adapterOthers.getItemCount() == 0) {
                            Glide.with(InventoryActivityView.this).load(R.drawable.no_notifications_logo).into(image_empty_others);
                            image_empty_others.setVisibility(View.VISIBLE);
                        } else {
                            image_empty_others.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void goToAddProductPage() {
        InventoryRecyclerView.PRODUCT_MODEL.setProduct_id("-1");
        Intent intent = new Intent(this, InventoryUpdateView.class);
        intent.putExtra("Button_Name", "Add Product");
        startActivity(intent);
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void goToSearchPage() {
        Intent intent = new Intent(this, InventorySearchItemView.class);
        startActivity(intent);
    }

    @Override
    public void refreshPage() {
        try {
            presenter.getGreenHouseFromDB();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                break;
            }
            case R.id.action_addfav: {
                presenter.onAddProductButtonClicked();
                break;
            }
            case R.id.search_item: {
                presenter.searchButtonClicked();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}