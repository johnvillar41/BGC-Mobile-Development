package emp.project.softwareengineerproject.View.ReportsView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.Bean.ReportsModel;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.ReportsService;
import emp.project.softwareengineerproject.Presenter.ReportsPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class ReportsActivityView extends AppCompatActivity implements IReports.IReportsView {
    private IReports.IReportsPresenter presenter;
    private ProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private TextView textView_Total, textView_Average, textView_Average_Monthly;
    private LineChart lineChart;
    private ProgressBar progressBar, progressBar_Users;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reports__view);

        presenter = new ReportsPresenter(this, ReportsService.getInstance(new ReportsModel()));
        presenter.initializeViews();

    }

    @Override
    public void initViews() {
        progressIndicator = findViewById(R.id.progressBar_Reports);
        progressIndicator.hide();
        recyclerView = findViewById(R.id.recyclerView_Reports);
        textView_Total = findViewById(R.id.txt_total);
        textView_Average = findViewById(R.id.txt_Average);
        textView_Average_Monthly = findViewById(R.id.txtMonthly);
        lineChart = findViewById(R.id.line_Chart);
        progressBar = findViewById(R.id.progress_bar_reports);
        progressBar_Users = findViewById(R.id.progress_bar_reports_sales);
        spinner = findViewById(R.id.spinnerName);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);

        presenter.loadTotals(LoginActivityView.USERNAME_VALUE);
        presenter.loadChartValues();
        presenter.loadSortedAdministrators();
        presenter.loadAdministratorValues();
    }

    @Override
    public void displayAdministratorList(List<String> adminList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ReportsActivityView.this, android.R.layout.simple_spinner_dropdown_item, adminList);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        presenter.onSpinnerItemClicked(spinner.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    @Override
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.show();
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.hide();
            }
        });
    }

    @Override
    public void displayProgressCircle() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressCircle() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void displayProgressCircle_Users() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_Users.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressCircle_Users() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar_Users.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void displayTotals(String total, String average, String ave_Monthly) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView_Total.setText(total);
                textView_Average.setText(average);
                textView_Average_Monthly.setText(ave_Monthly);
            }
        });
    }

    @Override
    public void displayChart(ReportsModel monthValue, String username) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Entry> yValues = new ArrayList<>();
                yValues.add(new Entry(1, Float.parseFloat(monthValue.getSales_month_1())));
                yValues.add(new Entry(2, Float.parseFloat(monthValue.getSales_month_2())));
                yValues.add(new Entry(3, Float.parseFloat(monthValue.getSales_month_3())));
                yValues.add(new Entry(4, Float.parseFloat(monthValue.getSales_month_4())));
                yValues.add(new Entry(5, Float.parseFloat(monthValue.getSales_month_5())));
                yValues.add(new Entry(6, Float.parseFloat(monthValue.getSales_month_6())));
                yValues.add(new Entry(7, Float.parseFloat(monthValue.getSales_month_7())));
                yValues.add(new Entry(8, Float.parseFloat(monthValue.getSales_month_8())));
                yValues.add(new Entry(9, Float.parseFloat(monthValue.getSales_month_9())));
                yValues.add(new Entry(10, Float.parseFloat(monthValue.getSales_month_10())));
                yValues.add(new Entry(11, Float.parseFloat(monthValue.getSales_month_11())));
                yValues.add(new Entry(12, Float.parseFloat(monthValue.getSales_month_12())));


                LineDataSet set1 = new LineDataSet(yValues, "Data set for: " + username);
                set1.setFillAlpha(110);
                set1.setColor(Color.RED);
                set1.setLineWidth(3f);
                set1.setValueTextSize(10f);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                LineData data = new LineData(dataSets);
                lineChart.setData(data);

                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }
        });

    }

    @Override
    public void displayRecyclerView(List<UserModel> sortedUserList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager
                        = new LinearLayoutManager(ReportsActivityView.this, LinearLayoutManager.VERTICAL, false);
                ReportsRecyclerView adapter = new ReportsRecyclerView(
                        sortedUserList, ReportsActivityView.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
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