package emp.project.softwareengineerproject.View.InventoryView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.Inventory.IInvetory;
import emp.project.softwareengineerproject.Model.InventoryModel;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventoryPresenter;
import emp.project.softwareengineerproject.R;

public class InventoryRecyclerView extends RecyclerView.Adapter<InventoryRecyclerView.MyViewHolder> {


    public static InventoryModel PRODUCT_MODEL;
    Context context;
    List<InventoryModel> list;
    InventoryPresenter presenter;

    public InventoryRecyclerView(Context context, List<InventoryModel> list) {
        this.context = context;
        this.list = list;
        this.presenter = new InventoryPresenter((IInvetory.IinventoryView) context);
        PRODUCT_MODEL = new InventoryModel();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_greenhouse, parent, false);
        return new InventoryRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final InventoryModel model = getItem(position);

        if (model.getProduct_category().equals("GREENHOUSE")) {
            holder.cardView_item.setCardBackgroundColor(Color.parseColor("#90ee90"));
        } else if (model.getProduct_category().equals("HYDROPONICS")) {
            holder.cardView_item.setCardBackgroundColor(Color.parseColor("#b5651d"));
        } else {
            holder.cardView_item.setCardBackgroundColor(Color.parseColor("#C58BE7"));
        }
        final Blob b = model.getProduct_picture();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            Glide.with(context).load(btm).into(holder.image_product);
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
                Button btn_update = dialogView.findViewById(R.id.btn_update);
                Button btn_back = dialogView.findViewById(R.id.btn_back);

                final AlertDialog dialog = dialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();

                txt_product_name.setText(model.getProduct_name());
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
                    Glide.with(context).load(btm).into(imageView_product);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                txt_product_description.setText(model.getProduct_description());
                txt_product_Price.setText(String.valueOf(model.getProduct_price()));
                txt_product_Stocks.setText(String.valueOf(model.getProduct_stocks()));

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, InventoryUpdateView.class);
                        context.startActivity(intent);
                        InventoryRecyclerView.PRODUCT_MODEL = new InventoryModel(model.getProduct_id(),
                                model.getProduct_name(), model.getProduct_description(),
                                model.getProduct_price(), model.getProduct_picture(),
                                model.getProduct_stocks(), model.getProduct_category());
                    }
                });
            }
        });
        holder.cardView_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(context);
                dialogBuilder.setTitle("Delete Item");
                dialogBuilder.setIcon(R.drawable.ic_delete);
                dialogBuilder.setMessage("Are you sure you want to delete this item?: "+model.getProduct_name());
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            presenter.onCardViewLongClicked(model.getProduct_id(),model.getProduct_name());
                            presenter.onSwipeRefresh();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("No",null);
                dialogBuilder.show();
                return true;
            }
        });
    }


    public InventoryModel getItem(int position) {
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
