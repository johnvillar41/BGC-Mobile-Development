package emp.project.softwareengineerproject.View.ReportsView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.progressindicator.ProgressIndicator;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.Database.Services.ReportsService;
import emp.project.softwareengineerproject.Presenter.ReportsPresenter;
import emp.project.softwareengineerproject.R;

public class ReportsActivityView extends AppCompatActivity implements IReports.IReportsView {
    private IReports.IReportsPresenter presenter;
    private ProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reports__view);

        presenter = new ReportsPresenter(this, ReportsService.getInstance());
        presenter.initializeViews();

    }

    @Override
    public void displayProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void initViews() {
        progressIndicator = findViewById(R.id.progressBar_Reports);
        progressIndicator.hide();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);


    }
}