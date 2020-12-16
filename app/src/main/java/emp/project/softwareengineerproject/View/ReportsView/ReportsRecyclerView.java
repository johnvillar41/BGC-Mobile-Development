package emp.project.softwareengineerproject.View.ReportsView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import emp.project.softwareengineerproject.Model.ReportsModel;
import emp.project.softwareengineerproject.R;

public class ReportsRecyclerView extends RecyclerView.Adapter<ReportsRecyclerView.MyViewHolder> {

    List<ReportsModel> list;
    Context context;

    public ReportsRecyclerView(List<ReportsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_reports, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReportsModel model = getItem(position);
        holder.txt_month.setText(String.valueOf(model.getDate_month()));
        holder.txt_date.setText(String.valueOf(model.getDate()));
        holder.txt_sales.setText(String.valueOf(model.getTotal_transactions_monthly()));
    }

    private ReportsModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_month, txt_date, txt_sales;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_sales = itemView.findViewById(R.id.txt_sales);
        }
    }
}
