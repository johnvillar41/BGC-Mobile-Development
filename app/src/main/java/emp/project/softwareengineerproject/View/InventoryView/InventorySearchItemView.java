package emp.project.softwareengineerproject.View.InventoryView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.List;

import emp.project.softwareengineerproject.CustomAdapters.ProductSearchedRecyclerView;
import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventorySearchItemPresenter;
import emp.project.softwareengineerproject.R;

public class InventorySearchItemView extends AppCompatActivity implements ISearchInventory.ISearchInventoryView {
    private FloatingSearchView txt_searchItem;
    private ISearchInventory.ISearchInventoryPresenter presenter;
    private RecyclerView recyclerViewSearchedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory_search_item_view);

        initViews();
    }

    private void initViews() {
        presenter = new InventorySearchItemPresenter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_searchItem = findViewById(R.id.txt_searchItem);
        recyclerViewSearchedItem = findViewById(R.id.recyclerView_SearchProduct);
        txt_searchItem.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                try {
                    presenter.onSearchItemProduct(txt_searchItem.getQuery());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void displayRecyclerView(final List<InventoryModel> product_list) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManager
                        = new LinearLayoutManager(InventorySearchItemView.this, LinearLayoutManager.VERTICAL, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductSearchedRecyclerView adapter = new ProductSearchedRecyclerView(
                                product_list, InventorySearchItemView.this);
                        recyclerViewSearchedItem.setLayoutManager(layoutManager);
                        recyclerViewSearchedItem.setAdapter(adapter);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}