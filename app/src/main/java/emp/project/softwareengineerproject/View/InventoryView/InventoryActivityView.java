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
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventoryService;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryUpdatePresenter;
import emp.project.softwareengineerproject.R;

public class InventoryActivityView extends AppCompatActivity implements IInvetory.IinventoryView {
    private RecyclerView recyclerView_GreenHouse, recyclerView_Hydroponics, recyclerView_others;

    private IInvetory.IinventoryPresenter presenter;
    private ProgressBar progressBar_greenHouse, progressBar_hydroponics, progressBar_others;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView animationView_Greenhouse, animationView_Hydroponics, animationView_Others;
    private Spinner spinner_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory_view);
        presenter = new InventoryPresenter(this, InventoryService.getInstance(new InventoryModel()));
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        progressBar_greenHouse = findViewById(R.id.progress_bar_greenhouse);
        progressBar_hydroponics = findViewById(R.id.progress_bar_hydroponics);
        progressBar_others = findViewById(R.id.progress_bar_others);
        recyclerView_GreenHouse = findViewById(R.id.recyclerView_greenHouse);
        recyclerView_Hydroponics = findViewById(R.id.recyclerView_hydroPonics);
        recyclerView_others = findViewById(R.id.recyclerView_others);
        swipeRefreshLayout = findViewById(R.id.refresh);

        animationView_Greenhouse = findViewById(R.id.animationView_noResult1);
        animationView_Hydroponics = findViewById(R.id.animationView_noResult2);
        animationView_Others = findViewById(R.id.animationView_noResult3);

        spinner_category = findViewById(R.id.spinner_category);
        presenter.loadData();

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
        if (InventoryUpdatePresenter.isAddProductClicked) {
            presenter.loadData();
        }
        super.onResume();
    }

    @Override
    public void displayRecyclerView(final List<InventoryModel>[] productList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
                    animationView_Greenhouse.setVisibility(LottieAnimationView.VISIBLE);
                } else {
                    animationView_Greenhouse.setVisibility(LottieAnimationView.GONE);
                }

                recyclerView_Hydroponics.setLayoutManager(layoutManagerHydroponics);
                recyclerView_Hydroponics.setAdapter(adapterHydroponics);
                recyclerView_Hydroponics.scheduleLayoutAnimation();
                progressBar_hydroponics.setVisibility(View.INVISIBLE);
                if (adapterHydroponics.getItemCount() == 0) {
                    animationView_Hydroponics.setVisibility(LottieAnimationView.VISIBLE);
                } else {
                    animationView_Hydroponics.setVisibility(LottieAnimationView.GONE);
                }
                recyclerView_others.setLayoutManager(layoutManagerOthers);
                recyclerView_others.setAdapter(adapterOthers);
                recyclerView_others.scheduleLayoutAnimation();
                progressBar_others.setVisibility(View.INVISIBLE);
                if (adapterOthers.getItemCount() == 0) {
                    animationView_Others.setVisibility(LottieAnimationView.VISIBLE);
                } else {
                    animationView_Others.setVisibility(LottieAnimationView.GONE);
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
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_greenHouse.setVisibility(View.VISIBLE);
                progressBar_hydroponics.setVisibility(View.VISIBLE);
                progressBar_others.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_greenHouse.setVisibility(View.INVISIBLE);
                progressBar_hydroponics.setVisibility(View.INVISIBLE);
                progressBar_others.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void goToSearchPage() {
        Intent intent = new Intent(this, InventorySearchItemView.class);
        startActivity(intent);
    }

    @Override
    public void refreshPage() {
        presenter.loadData();
    }

    @Override
    public void displayCategory(final List<String> categories) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(InventoryActivityView.this, android.R.layout.simple_spinner_dropdown_item, categories);
                spinner_category.setAdapter(adapter);
                spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final String selectedItem = spinner_category.getSelectedItem().toString();
                        presenter.onItemSpinnerSelected(selectedItem);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }


    @Override
    public void displayRecyclerViewFromCategory(final List<InventoryModel> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(InventoryActivityView.this, LinearLayoutManager.HORIZONTAL, false);
                final InventoryRecyclerView adapter = new InventoryRecyclerView(
                        InventoryActivityView.this, list);

                recyclerView_others.setLayoutManager(linearLayoutManager);
                recyclerView_others.setAdapter(adapter);
                progressBar_others.setVisibility(View.INVISIBLE);
                if (adapter.getItemCount() == 0) {
                    animationView_Others.setVisibility(View.VISIBLE);
                } else {
                    animationView_Others.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void displayProgressBarRecycler_Others() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_others.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBarRecycler_Others() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_others.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_addfav) {
            presenter.onAddProductButtonClicked();
        } else if (item.getItemId() == R.id.search_item) {
            presenter.searchButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }
}