package emp.project.softwareengineerproject.View.SalesView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.R;

public class SalesTransactionRecyclerView extends RecyclerView.Adapter<SalesTransactionRecyclerView.MyViewHolder> {

    List<SalesModel> list;
    Context context;

    public SalesTransactionRecyclerView(List<SalesModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_sales_transactions, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SalesModel model = getItem(position);
        holder.txt_date.setText(model.getSales_date());
        holder.txt_product_name.setText(model.getSales_title());
        holder.txt_product_id.setText(model.getProduct_id());
        Glide.with(context).load(R.drawable.ic_money_large).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);

    }

    public SalesModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_product_name, txt_product_id, txt_name_here, txt_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_id = itemView.findViewById(R.id.txt_product_id);
            txt_name_here = itemView.findViewById(R.id.txt_name_here);
            txt_date = itemView.findViewById(R.id.txt_date);
        }
    }
}
