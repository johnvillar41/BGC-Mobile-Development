package emp.project.softwareengineerproject.View.OrdersView;

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
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Model.Bean.OrdersModel;
import emp.project.softwareengineerproject.Model.Bean.SpecificOrdersModel;
import emp.project.softwareengineerproject.R;

public class OrdersSpecificRecyclerView extends RecyclerView.Adapter<OrdersSpecificRecyclerView.MyViewHolder> {

    private Context context;
    private List<SpecificOrdersModel> orderList;

    public OrdersSpecificRecyclerView(Context context, List<SpecificOrdersModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_specific_orders, parent, false);
        return new OrdersSpecificRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SpecificOrdersModel model = getItem(position);
        holder.number_products.setText(model.getTotalOrders());
        holder.product_id.setText(model.getProductID());
        holder.product_name.setText(model.getProductID());
        final Blob b = (Blob) model.getProductID();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .skipMemoryCache(true)
                    .into(holder.circleImageView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public SpecificOrdersModel getItem(int position) {
        return orderList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_id, number_products, product_name;
        CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id = itemView.findViewById(R.id.product_id);
            number_products = itemView.findViewById(R.id.number_products);
            product_name = itemView.findViewById(R.id.product_name);
            circleImageView = itemView.findViewById(R.id.image_product);
        }
    }
}
