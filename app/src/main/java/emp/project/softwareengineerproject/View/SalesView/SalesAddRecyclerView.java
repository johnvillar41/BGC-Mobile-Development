package emp.project.softwareengineerproject.View.SalesView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Model.SalesModel;
import emp.project.softwareengineerproject.R;

public class SalesAddRecyclerView extends RecyclerView.Adapter<SalesAddRecyclerView.MyViewHolder> {

    List<InventoryModel> list;
    Context context;

    public SalesAddRecyclerView(List<InventoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_addtransaction, parent, false);
        return new SalesAddRecyclerView.MyViewHolder(view);
    }

    private InventoryModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final InventoryModel model = getItem(position);
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
                            Glide.with(context).load(btm).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });thread.start();

        if (model.getProduct_stocks() == 0) {
            holder.checkBox.setVisibility(View.GONE);
            holder.txtProduct_stocks.setTextColor(Color.parseColor("#FD0303"));
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        holder.txt_productName.setText(model.getProduct_name());
        holder.txtProduct_stocks.setText(String.valueOf(model.getProduct_stocks()));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    SalesModel.cartList.add(model);
                } else {
                    SalesModel.cartList.remove(model);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txt_productName, txtProduct_stocks;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_productName = itemView.findViewById(R.id.txt_product_name);
            checkBox = itemView.findViewById(R.id.checkbox_product);
            txtProduct_stocks = itemView.findViewById(R.id.txt_product_stocks);
        }
    }
}
