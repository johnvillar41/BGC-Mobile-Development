package emp.project.softwareengineerproject.CustomAdapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.R;

public class GreenHouseRecyclerView extends RecyclerView.Adapter<GreenHouseRecyclerView.MyViewHolder> {

    Context context;
    List<ProductModel> list;

    public GreenHouseRecyclerView(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_greenhouse, parent, false);
        return new GreenHouseRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProductModel model = getItem(position);
        final Blob b = model.getProduct_picture();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            holder.image_product.setImageBitmap(btm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_stock_number.setText(String.valueOf(model.getProduct_stocks()));
        holder.cardView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_show_product, null);
                dialogBuilder.setView(dialogView);

                TextView txt_product_name = dialogView.findViewById(R.id.txt_product_name);
                ImageView imageView_product = dialogView.findViewById(R.id.image_product);
                TextView txt_product_description = dialogView.findViewById(R.id.txt_product_description);
                TextView txt_product_Price = dialogView.findViewById(R.id.txt_product_Price);
                TextView txt_product_Stocks = dialogView.findViewById(R.id.txt_product_Stocks);

                AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                txt_product_name.setText(model.getProduct_name());
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
                    imageView_product.setImageBitmap(btm);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                txt_product_description.setText(model.getProduct_description());
                txt_product_Price.setText(String.valueOf(model.getProduct_price()));
                txt_product_Stocks.setText(String.valueOf(model.getProduct_stocks()));
            }
        });
    }

    public ProductModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView_item;
        CircleImageView image_product;
        TextView txt_product_name;
        TextView txt_stock_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_product = itemView.findViewById(R.id.imageProduct_id);
            txt_product_name = itemView.findViewById(R.id.txtProduct_name);
            txt_stock_number = itemView.findViewById(R.id.txtProduct_stocks);
            cardView_item = itemView.findViewById(R.id.cardView_item);
        }
    }
}
