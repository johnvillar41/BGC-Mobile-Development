package emp.project.softwareengineerproject.View.InventoryView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;
import java.util.List;

import emp.project.softwareengineerproject.Interface.Inventory.ISearchInventory;
import emp.project.softwareengineerproject.Model.Bean.InventoryModel;
import emp.project.softwareengineerproject.Model.Database.Services.InventoryService.InventorySearchItemService;
import emp.project.softwareengineerproject.Presenter.InventoryPresenter.InventorySearchItemPresenter;
import emp.project.softwareengineerproject.R;

import static emp.project.softwareengineerproject.View.InventoryView.InventoryRecyclerView.STOCK_LEVEL.HIGH;
import static emp.project.softwareengineerproject.View.InventoryView.InventoryRecyclerView.STOCK_LEVEL.LOW;

public class InventorySearchedRecyclerView extends RecyclerView.Adapter<InventorySearchedRecyclerView.MyViewHolder> {
    List<InventoryModel> list;
    Context context;
    private static int numberOfDialogsOpenned = 0;
    private InventorySearchItemPresenter presenter;

    public InventorySearchedRecyclerView(List<InventoryModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.presenter = new InventorySearchItemPresenter((ISearchInventory.ISearchInventoryView) context, InventorySearchItemService.getInstance(new InventoryModel()));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_search_product, parent, false);
        return new InventorySearchedRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final InventoryModel model = getItem(position);
        final Blob b = model.getProduct_picture();
        final int[] blobLength = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(context)
                                    .load(blobAsBytes)
                                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                    .skipMemoryCache(true)
                                    .into(holder.circleImageView);
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

        holder.txt_product_name.setText(model.getProduct_name());
        holder.txt_stocks_number.setText(String.valueOf(model.getProduct_stocks()));
        /**
         * Color coding for the product number of
         * stocks
         */
        if (model.getProduct_stocks() >= HIGH.getVal()) { //Greater than or equal to 50 = Green
            holder.txt_stocks_number.setTextColor(Color.GREEN);
        } else if (model.getProduct_stocks() <= HIGH.getVal() && //
                model.getProduct_stocks() >= LOW.getVal()) {
            holder.txt_stocks_number.setTextColor(Color.BLUE);
        } else if (model.getProduct_stocks() < LOW.getVal()) {
            holder.txt_stocks_number.setTextColor(Color.RED);
            holder.txt_stocks_number.setError("Low number of stocks!!");
        }
        holder.cardView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfDialogsOpenned = 1;
                if (numberOfDialogsOpenned == 1) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.custom_popup_show_product, null);
                    dialogBuilder.setView(dialogView);

                    TextView txt_product_name = dialogView.findViewById(R.id.txt_product_name);
                    final ImageView imageView_product = dialogView.findViewById(R.id.image_product);
                    TextView txt_product_description = dialogView.findViewById(R.id.txt_product_description);
                    TextView txt_product_Price = dialogView.findViewById(R.id.txt_product_Price);
                    TextView txt_product_Stocks = dialogView.findViewById(R.id.txt_product_Stocks);
                    Button btn_update = dialogView.findViewById(R.id.btn_update);
                    Button btn_back = dialogView.findViewById(R.id.btn_back);

                    final AlertDialog dialog = dialogBuilder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();

                    txt_product_name.setText(model.getProduct_name());
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                blobLength[0] = (int) b.length();
                                byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(context)
                                                .load(blobAsBytes)
                                                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                                                .skipMemoryCache(true)
                                                .into(imageView_product);
                                    }
                                });
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();

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
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            numberOfDialogsOpenned = 0;
                        }
                    });
                }
            }
        });

        holder.cardView_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle("Delete Item");
                dialogBuilder.setIcon(R.drawable.ic_delete);
                dialogBuilder.setMessage("Are you sure you want to delete this item?: " + model.getProduct_name());
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onCardViewLongClicked(model.getProduct_id(), model.getProduct_name());
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
