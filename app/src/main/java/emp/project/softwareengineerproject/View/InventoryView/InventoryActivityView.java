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

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.CustomAdapters.ProductRecyclerView;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;
import emp.project.softwareengineerproject.R;

public class InventoryActivityView extends AppCompatActivity implements IInvetory.IinventoryView {
    RecyclerView recyclerView_GreenHouse, recyclerView_Hydroponics, recyclerView_others;

    IInvetory.IinventoryPresenter presenter;
    ProgressBar progressBar;

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
        recyclerView_GreenHouse = findViewById(R.id.recyclerView_greenHouse);
        recyclerView_Hydroponics = findViewById(R.id.recyclerView_hydroPonics);
        recyclerView_others = findViewById(R.id.recyclerView_others);
        presenter.getGreenHouseFromDB();


    }

    @Override
    public void displayRecyclerView(final List<ProductModel>[] productList) {
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
                        ProductRecyclerView adapterGreenhouse = new ProductRecyclerView(
                                InventoryActivityView.this, productList[0]);
                        recyclerView_GreenHouse.setLayoutManager(layoutManagerGreenhouse);
                        recyclerView_GreenHouse.setAdapter(adapterGreenhouse);

                        ProductRecyclerView adapterHydroponics = new ProductRecyclerView(
                                InventoryActivityView.this, productList[1]);
                        recyclerView_Hydroponics.setLayoutManager(layoutManagerHydroponics);
                        recyclerView_Hydroponics.setAdapter(adapterHydroponics);

                        ProductRecyclerView adapterOthers = new ProductRecyclerView(
                                InventoryActivityView.this, productList[2]);
                        recyclerView_others.setLayoutManager(layoutManagerOthers);
                        recyclerView_others.setAdapter(adapterOthers);
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void goToAddProductPage() {
        ProductRecyclerView.PRODUCT_MODEL.setProduct_id("-1");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.action_addfav) {
            presenter.onAddProductButtonClicked();
        }
        if (item.getItemId() == R.id.search_item) {
            presenter.searchButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }
}