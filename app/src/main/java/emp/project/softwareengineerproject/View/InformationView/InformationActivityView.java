package emp.project.softwareengineerproject.View.InformationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.List;

import emp.project.softwareengineerproject.Interface.IInformation;
import emp.project.softwareengineerproject.Model.Bean.InformationModel;
import emp.project.softwareengineerproject.Model.Database.Services.InformationService;
import emp.project.softwareengineerproject.Presenter.InformationPresenter;
import emp.project.softwareengineerproject.R;

public class InformationActivityView extends AppCompatActivity implements IInformation.IInformationView {
    private ProgressIndicator progressIndicator;
    private RecyclerView recyclerView_Information;
    private IInformation.IInformationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information_view);
        presenter = new InformationPresenter(this, InformationService.getInstance());
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        progressIndicator = findViewById(R.id.progressBar_Information);
        recyclerView_Information = findViewById(R.id.recyclerView_Information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter.loadData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void displayRecyclerViewData(List<InformationModel> informationModelList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InformationRecyclerView adapter = new InformationRecyclerView(
                        InformationActivityView.this, informationModelList, presenter);
                recyclerView_Information.setLayoutManager(new GridLayoutManager(InformationActivityView.this, 3));
                recyclerView_Information.setAdapter(adapter);
                recyclerView_Information.scheduleLayoutAnimation();
            }
        });
    }

    @Override
    public void displayMessage(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InformationActivityView.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}