package emp.project.softwareengineerproject.View.ReportsView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Presenter.ReportsPresenter;
import emp.project.softwareengineerproject.R;

public class ReportsActivityView extends AppCompatActivity implements IReports.IReportsView{
    private IReports.IReportsPresenter presenter;
    private ProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private TextView textView_Total, textView_Average, textView_Average_Monthly;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reports__view);


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
        presenter = new ReportsPresenter(this, this);

        progressIndicator = findViewById(R.id.progressBar_Reports);
        progressIndicator.hide();
        recyclerView = findViewById(R.id.recyclerView_Reports);
        textView_Total = findViewById(R.id.txt_total);
        textView_Average = findViewById(R.id.txt_Average);
        textView_Average_Monthly = findViewById(R.id.txtMonthly);
        lineChart = findViewById(R.id.line_Chart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter.loadTotals();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        //Testing only
        displayChart(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
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
    public void displayTotals(String total, String average, String ave_Monthly) {
        textView_Total.setText(total);
        textView_Average.setText(average);
        textView_Average_Monthly.setText(ave_Monthly);
    }

    @Override
    public void displayChart(List<String> monthValues) {
        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(1, 60f));
        yValues.add(new Entry(2, 60f));
        yValues.add(new Entry(3, 60f));
        yValues.add(new Entry(4, 60f));
        yValues.add(new Entry(5, 60f));
        yValues.add(new Entry(6, 60f));
        yValues.add(new Entry(7, 60f));
        yValues.add(new Entry(8, 60f));
        yValues.add(new Entry(9, 60f));
        yValues.add(new Entry(10, 60f));
        yValues.add(new Entry(11, 60f));
        yValues.add(new Entry(12, 60f));

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
        set1.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}