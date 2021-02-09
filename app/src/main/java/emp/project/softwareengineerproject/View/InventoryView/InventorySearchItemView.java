package emp.project.softwareengineerproject.View.InventoryView;

import android.os.Bundle;
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

import com.airbnb.lottie.LottieAnimationView;
import com.arlib.floatingsearchview.FloatingSearchView;

import java.io.File;
import java.util.List;

import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventorySearchItemService;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventorySearchItemPresenter;
import emp.project.softwareengineerproject.R;

public class InventorySearchItemView extends AppCompatActivity implements ISearchInventory.ISearchInventoryView {
    private FloatingSearchView txt_searchItem;
    private ISearchInventory.ISearchInventoryPresenter presenter;
    private RecyclerView recyclerViewSearchedItem;
    private ProgressBar progressBar;
    private LottieAnimationView animationView_Noresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory_search_item_view);
        presenter = new InventorySearchItemPresenter(this, InventorySearchItemService.getInstance(new InventoryModel()));
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        animationView_Noresult = findViewById(R.id.animationView_noResult);
        txt_searchItem = findViewById(R.id.txt_searchItem);
        recyclerViewSearchedItem = findViewById(R.id.recyclerView_SearchProduct);
        txt_searchItem.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                presenter.onSearchItemProduct(txt_searchItem.getQuery());
            }
        });
        progressBar = findViewById(R.id.progressBar_search_item);
    }

    @Override
    public void displayRecyclerView(final List<InventoryModel> product_list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManager
                        = new LinearLayoutManager(InventorySearchItemView.this, LinearLayoutManager.VERTICAL, false);
                InventorySearchedRecyclerView adapter = new InventorySearchedRecyclerView(
                        product_list, InventorySearchItemView.this);
                recyclerViewSearchedItem.setLayoutManager(layoutManager);
                recyclerViewSearchedItem.setAdapter(adapter);
                if (adapter.getItemCount() == 0) {
                    animationView_Noresult.setVisibility(View.VISIBLE);
                } else {
                    animationView_Noresult.setVisibility(View.GONE);
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
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}