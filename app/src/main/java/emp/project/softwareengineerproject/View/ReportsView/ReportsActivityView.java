package emp.project.softwareengineerproject.View.ReportsView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import emp.project.softwareengineerproject.Interface.IReports;
import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Presenter.ReportsPresenter;
import emp.project.softwareengineerproject.R;

public class ReportsActivityView extends AppCompatActivity implements IReports.IReportsView {
    private IReports.IReportsPresenter presenter;
    private ProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private TextView textView_Total, textView_Average, textView_Average_Monthly;
    private LineChart lineChart;

    private List<Integer> MENU_LIST_ID = new ArrayList<>();
    private List<String> MENU_LIST_NAMES = new ArrayList<>();

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

        presenter.loadTotals(null);
        presenter.loadChartValues();
        presenter.loadSortedAdministrators();

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        try {
            for (int i = 0; i < presenter.loadAdministratorValues().size(); i++) {
                menu.add(0, i, Menu.NONE, presenter.loadAdministratorValues().get(i)).setIcon(R.drawable.ic_user);
                MENU_LIST_ID.add(i);
                MENU_LIST_NAMES.add(presenter.loadAdministratorValues().get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onPrepareOptionsMenu(menu);
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
    public void displayChart(ReportsModel monthValue, String username) {
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

    @Override
    public void displayRecyclerView(List<UserModel> sortedUserList) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(ReportsActivityView.this, LinearLayoutManager.VERTICAL, false);
        ReportsRecyclerView adapter = new ReportsRecyclerView(
                sortedUserList,this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        for (int i = 0; i < MENU_LIST_ID.size(); i++) {
            if (item.getItemId() == MENU_LIST_ID.get(i)) {
                Toast.makeText(this, String.valueOf(MENU_LIST_NAMES.get(i)), Toast.LENGTH_SHORT).show();
                presenter.onMenuButtonClicked(MENU_LIST_NAMES.get(i));
            }
        }


        return super.onOptionsItemSelected(item);
    }
}