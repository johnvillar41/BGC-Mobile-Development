package emp.project.softwareengineerproject.View.InventoryView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;
import emp.project.softwareengineerproject.R;

public class InventoryActivityView extends AppCompatActivity implements IInvetory.IinventoryView {
    private RecyclerView recyclerView_GreenHouse, recyclerView_Hydroponics, recyclerView_others;

    private IInvetory.IinventoryPresenter presenter;
    private ProgressBar progressBar_greenHouse, progressBar_hydroponics, progressBar_others;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView image_empty_greenhouse, image_empty_hydroponics, image_empty_others;
    private Spinner spinner_category;

    private Thread thread;
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter = new InventoryPresenter(this);

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
        spinner_category = findViewById(R.id.spinner_category);
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

        final LinearLayoutManager layoutManagerGreenhouse
                = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManagerHydroponics
                = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager layoutManagerOthers
                = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);

        final InventoryRecyclerView adapterGreenhouse = new InventoryRecyclerView(
                InventoryActivityView.this, productList[0]);
        final InventoryRecyclerView adapterHydroponics = new InventoryRecyclerView(
                InventoryActivityView.this, productList[1]);
        final InventoryRecyclerView adapterOthers = new InventoryRecyclerView(
                InventoryActivityView.this, productList[2]);


        recyclerView_GreenHouse.setLayoutManager(layoutManagerGreenhouse);
        recyclerView_GreenHouse.setAdapter(adapterGreenhouse);
        recyclerView_GreenHouse.scheduleLayoutAnimation();
        progressBar_greenHouse.setVisibility(View.INVISIBLE);
        if (adapterGreenhouse.getItemCount() == 0) {
            Glide.with(getApplicationContext()).load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_empty_greenhouse);
            image_empty_greenhouse.setVisibility(View.VISIBLE);
        } else {
            image_empty_greenhouse.setVisibility(View.GONE);
        }

        recyclerView_Hydroponics.setLayoutManager(layoutManagerHydroponics);
        recyclerView_Hydroponics.setAdapter(adapterHydroponics);
        recyclerView_Hydroponics.scheduleLayoutAnimation();
        progressBar_hydroponics.setVisibility(View.INVISIBLE);
        if (adapterHydroponics.getItemCount() == 0) {
            Glide.with(getApplicationContext()).load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_empty_hydroponics);
            image_empty_hydroponics.setVisibility(View.VISIBLE);
        } else {
            image_empty_hydroponics.setVisibility(View.GONE);
        }
        recyclerView_others.setLayoutManager(layoutManagerOthers);
        recyclerView_others.setAdapter(adapterOthers);
        recyclerView_others.scheduleLayoutAnimation();
        progressBar_others.setVisibility(View.INVISIBLE);
        if (adapterOthers.getItemCount() == 0) {
            Glide.with(getApplicationContext()).load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_empty_others);
            image_empty_others.setVisibility(View.VISIBLE);
        } else {
            image_empty_others.setVisibility(View.GONE);
        }
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
    public void goToAddProductPage() {
        try {
            InventoryRecyclerView.PRODUCT_MODEL.setProduct_id("-1");
            Intent intent = new Intent(this, InventoryUpdateView.class);
            intent.putExtra("Button_Name", "Add Product");
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(this, InventoryUpdateView.class);
            intent.putExtra("Button_Name", "Add Product");
            startActivity(intent);
        }

    }

    @Override
    public void showProgressBarRecyclers() {
        progressBar_greenHouse.setVisibility(View.VISIBLE);
        progressBar_hydroponics.setVisibility(View.VISIBLE);
        progressBar_others.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBarReyclers() {
        progressBar_greenHouse.setVisibility(View.INVISIBLE);
        progressBar_hydroponics.setVisibility(View.INVISIBLE);
        progressBar_others.setVisibility(View.INVISIBLE);
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
    public void displayCategory(final List<String> categories) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItem = spinner_category.getSelectedItem().toString();
                try {
                    presenter.onItemSpinnerSelected(selectedItem);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void displayRecyclerViewFromCategory(final List<InventoryModel> list) {

        final LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
        final InventoryRecyclerView adapter = new InventoryRecyclerView(
                InventoryActivityView.this, list);

        recyclerView_others.setLayoutManager(linearLayoutManager);
        recyclerView_others.setAdapter(adapter);
        progressBar_others.setVisibility(View.INVISIBLE);
        if (adapter.getItemCount() == 0) {
            Glide.with(InventoryActivityView.this).load(R.drawable.no_result_imag2).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(image_empty_others);
            image_empty_others.setVisibility(View.VISIBLE);
        } else {
            image_empty_others.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayProgressBarRecycler_Others() {
        progressBar_others.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBarRecycler_Others() {
        progressBar_others.setVisibility(View.INVISIBLE);
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