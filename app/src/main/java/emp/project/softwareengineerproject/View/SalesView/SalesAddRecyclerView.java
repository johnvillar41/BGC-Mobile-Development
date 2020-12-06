package emp.project.softwareengineerproject.View.SalesView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        InventoryModel model = getItem(position);
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
        holder.txt_productName.setText(model.getProduct_name());
        Integer[] arrNumber = new Integer[10];
        for (int i = 0; i < arrNumber.length; i++) {
            arrNumber[i] = i+1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item, arrNumber);
        holder.spinner_total_number.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txt_productName;
        CheckBox checkBox;
        Spinner spinner_total_number;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_product);
            txt_productName = itemView.findViewById(R.id.txt_product_name);
            checkBox = itemView.findViewById(R.id.checkbox_product);
            spinner_total_number = itemView.findViewById(R.id.spinner_number);
        }
    }
}
