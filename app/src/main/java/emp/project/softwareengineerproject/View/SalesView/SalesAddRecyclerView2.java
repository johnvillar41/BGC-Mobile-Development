package emp.project.softwareengineerproject.View.SalesView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Model.Bean.CartListModel;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.R;

public class SalesAddRecyclerView2 extends RecyclerView.Adapter<SalesAddRecyclerView2.MyViewHolder> {

    List<InventoryModel> list;
    Context context;

    public SalesAddRecyclerView2(List<InventoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_cart_final, parent, false);
        return new SalesAddRecyclerView2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final InventoryModel model = CartListModel.getInstance().cartList.get(position);
        holder.txt_name.setText(model.getProductName());
        final Blob b = (Blob) model.getProductPicture();
        final int[] blobLength = new int[1];
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(context)
                                    .load(blobAsBytes)
                                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                                    .into(holder.imageView);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });thread.start();

        String[] arrNums = new String[11];
        for (int i = 0; i < arrNums.length; i++) {
            arrNums[i] = String.valueOf(i);
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrNums);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                long newValue = model.getProductPrice() * Long.parseLong(parent.getSelectedItem().toString());
                //CartListModel.getInstance().cartList.get(position).setNewPrice(newValue);
                //CartListModel.getInstance().cartList.get(position).setTotal_number_of_products(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //SalesModel.cartList.get(position).setProduct_price(model.getProduct_price());
            }
        });

        holder.imageViewRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartListModel.getInstance().cartList.remove(model);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView imageViewRemoveItem;
        TextView txt_name;
        Spinner spinner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewRemoveItem = itemView.findViewById(R.id.image_remove);
            imageView = itemView.findViewById(R.id.image_product);
            txt_name = itemView.findViewById(R.id.txtProduct_name);
            spinner = itemView.findViewById(R.id.product_total_number);
        }
    }
}
