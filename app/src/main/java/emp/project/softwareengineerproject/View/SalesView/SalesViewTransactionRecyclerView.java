package emp.project.softwareengineerproject.View.SalesView;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.ISales.ISalesTransactions;
import emp.project.softwareengineerproject.Model.Bean.SalesModel;
import emp.project.softwareengineerproject.Model.Database.Services.SalesService.SalesTransactionService;
import emp.project.softwareengineerproject.Presenter.SalesPresenter.SalesTransactionPresenter;
import emp.project.softwareengineerproject.R;

public class SalesViewTransactionRecyclerView extends RecyclerView.Adapter<SalesViewTransactionRecyclerView.MyViewHolder> {

    List<SalesModel> list;
    Context context;
    ISalesTransactions.ISalesTransactionsView activityView;
    ISalesTransactions.ISalesTransactionPresenter presenter;

    public SalesViewTransactionRecyclerView(List<SalesModel> list, Context context, ISalesTransactions.ISalesTransactionsView activityView) {
        this.list = list;
        this.context = context;
        this.activityView = activityView;
        this.presenter = new SalesTransactionPresenter(this.activityView, SalesTransactionService.getInstance(new SalesModel()));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_sales_transactions, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final SalesModel model = getItem(position);
        holder.txt_date.setText(model.getSales_date());
        holder.txt_product_name.setText(model.getSales_title());
        holder.txt_product_id.setText(model.getProduct_id());
        Glide.with(context).load(R.drawable.ic_money_large).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        holder.txt_total_products.setText(model.getTotal_number_of_products());
        holder.txt_totalValue.setText(String.valueOf(model.getProduct_total()));
        holder.txt_transaction_id.setText(model.getSales_id());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle("Delete Item");
                dialogBuilder.setIcon(R.drawable.ic_delete);
                dialogBuilder.setMessage("Are you sure you want to delete this Sales Item?: " + model.getSales_id());
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onLongCardViewClicked(model.getSales_id());
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                    }
                });
                dialogBuilder.setNegativeButton("No", null);
                dialogBuilder.show();
                return true;
            }
        });

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
        TextView txt_product_name, txt_product_id, txt_date, txt_total_products;
        TextView txt_totalValue;
        TextView txt_transaction_id;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_id = itemView.findViewById(R.id.txt_product_id);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_total_products = itemView.findViewById(R.id.txt_total_products);
            txt_totalValue = itemView.findViewById(R.id.txt_total_value);
            cardView = itemView.findViewById(R.id.cardView_Transactions);
            txt_transaction_id = itemView.findViewById(R.id.txt_transaction_id);
        }
    }
}
