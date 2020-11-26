package emp.project.softwareengineerproject.CustomAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Model.ProductModel;
import emp.project.softwareengineerproject.R;

public class ProductSearchedRecyclerView extends RecyclerView.Adapter<ProductSearchedRecyclerView.MyViewHolder> {
    List<ProductModel> list;
    Context context;

    public ProductSearchedRecyclerView(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_search_product, parent, false);
        return new ProductSearchedRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel model = getItem(position);
        final Blob b = model.getProduct_picture();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            Glide.with(context).load(btm).into(holder.circleImageView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_stocks_number.setText(String.valueOf(model.getProduct_stocks()));
    }

    private ProductModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_product_name, txt_stocks_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_stocks_number = itemView.findViewById(R.id.txt_product_Stocks);
        }
    }
}
