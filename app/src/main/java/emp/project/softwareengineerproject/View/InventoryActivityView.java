package emp.project.softwareengineerproject.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.CustomAdapters.GreenHouseRecyclerView;
import emp.project.softwareengineerproject.Interface.IInvetory;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter;
import emp.project.softwareengineerproject.R;

public class InventoryActivityView extends AppCompatActivity implements IInvetory.IinventoryView {
    RecyclerView recyclerView_GreenHouse;
    InventoryPresenter presenter;

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
        }
    }

    @Override
    public void initViews() throws SQLException, ClassNotFoundException {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter=new InventoryPresenter(this);
        recyclerView_GreenHouse=findViewById(R.id.recyclerView_greenHouse);
        presenter.getGreenHouseFromDB();
    }

    @Override
    public void displayRecyclerViewGreenHouse(List<ProductModel> productList) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        GreenHouseRecyclerView adapter = new GreenHouseRecyclerView(
        InventoryActivityView.this, productList);
        recyclerView_GreenHouse.setAdapter(adapter);
        recyclerView_GreenHouse.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        if(item.getItemId()==R.id.action_addfav){
            //goto new ActivityPage
        }
        return super.onOptionsItemSelected(item);
    }
}