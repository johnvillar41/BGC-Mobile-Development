package emp.project.softwareengineerproject.View.InventoryView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.R;

public class InventorySearchedRecyclerView extends RecyclerView.Adapter<InventorySearchedRecyclerView.MyViewHolder> {
    List<InventoryModel> list;
    Context context;

    public InventorySearchedRecyclerView(List<InventoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_search_product, parent, false);
        return new InventorySearchedRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        InventoryModel model = getItem(position);
        final Blob b = model.getProduct_picture();
        final int[] blobLength = new int[1];
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    final Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(context)
                                    .load(btm)
                                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                    .skipMemoryCache(true)
                                    .into(holder.circleImageView);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });thread.start();

        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_stocks_number.setText(String.valueOf(model.getProduct_stocks()));
    }

    private InventoryModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView txt_product_name, txt_stocks_number;
        CardView cardView_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_stocks_number = itemView.findViewById(R.id.txt_product_Stocks);
            cardView_item = itemView.findViewById(R.id.cardView_products_search);
        }
    }
}
