package emp.project.softwareengineerproject.View.OrdersView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import emp.project.softwareengineerproject.Model.OrdersModel;
import emp.project.softwareengineerproject.R;

public class OrdersRecyclerView extends RecyclerView.Adapter<OrdersRecyclerView.MyViewHolder> {

    List<OrdersModel> list;
    Context context;

    public OrdersRecyclerView(List<OrdersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_orders, parent, false);
        return new OrdersRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrdersModel model = getItem(position);
        holder.txt_year.setText(model.getOrder_date_year());
        holder.txt_day.setText(model.getOrder_date_day());
        holder.txt_month.setText(model.getOrder_date_month());
        holder.customer_name.setText(model.getCustomer_name());
        holder.customer_email.setText(model.getCustomer_email());
        holder.txt_total.setText(model.getOrder_total_price());
        holder.txt_order_id.setText(model.getOrder_id());
        if (model.getOrder_status().equals("Processing")) {
            holder.order_status.setTextColor(Color.parseColor("#024C05"));
        }
        holder.order_status.setText(model.getOrder_status());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OrdersModel getItem(int position) {
        return list.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_year, txt_day, txt_month, txt_order_id, customer_name, customer_email, txt_total, order_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_year);
            txt_day = itemView.findViewById(R.id.txt_day);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_order_id = itemView.findViewById(R.id.txt_order_number);
            customer_name = itemView.findViewById(R.id.txt_customer_name);
            customer_email = itemView.findViewById(R.id.txt_customer_email);
            txt_total = itemView.findViewById(R.id.txt_total_value);
            order_status = itemView.findViewById(R.id.txt_status);
        }
    }
}
