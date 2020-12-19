package emp.project.softwareengineerproject.View.NotificationView;

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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Model.NotificationModel;
import emp.project.softwareengineerproject.R;

public class NotificationRecyclerView extends RecyclerView.Adapter<NotificationRecyclerView.MyViewHolder> {

    Context context;
    List<NotificationModel> list;

    public NotificationRecyclerView(Context context, List<NotificationModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_notifications, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel model = getItem(position);
        holder.txt_title.setText(model.getNotif_title());
        holder.txt_date.setText(model.getNotif_date());
        holder.txt_content.setText(model.getNotif_content());
        holder.txt_username.setText(model.getUser_name());

        if (model.getNotif_title().equals(PRODUCT_STATUS.DELETED_PRODUCT.getProduct_status())) {
            Glide.with(context).load(R.drawable.delete_logo).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if (model.getNotif_title().equals(PRODUCT_STATUS.UPDATED_PRODUCT.getProduct_status())) {
            Glide.with(context).load(R.drawable.update_logo).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if (model.getNotif_title().equals(PRODUCT_STATUS.ADDED_PRODUCT.getProduct_status())) {
            Glide.with(context).load(R.drawable.add_product).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if (model.getNotif_title().equals(PRODUCT_STATUS.ADDED_SALES.getProduct_status())) {
            Glide.with(context).load(R.drawable.ic_money_large).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if (model.getNotif_title().equals(PRODUCT_STATUS.ADDED_NEW_USER.getProduct_status())) {
            Glide.with(context).load(R.drawable.ic_add_user).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if(model.getNotif_title().equals(PRODUCT_STATUS.ORDER_PENDING.getProduct_status())){
            Glide.with(context).load(R.drawable.update_icon_order).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if(model.getNotif_title().equals(PRODUCT_STATUS.ORDER_FINISHED.getProduct_status())){
            Glide.with(context).load(R.drawable.update_icon_order).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        } else if(model.getNotif_title().equals(PRODUCT_STATUS.ORDER_CANCEL.getProduct_status())){
            Glide.with(context).load(R.drawable.update_icon_order).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
        }

    }


    private enum PRODUCT_STATUS {
        DELETED_PRODUCT("Deleted product"),
        UPDATED_PRODUCT("Updated product"),
        ADDED_PRODUCT("Added product"),
        ADDED_SALES("Added sales"),
        ADDED_NEW_USER("Added new User"),
        ORDER_PENDING("Order moved to pending"),
        ORDER_FINISHED("Order is finished"),
        ORDER_CANCEL("Order cancelled");

        private String product_status;

        PRODUCT_STATUS(String product_status) {
            this.product_status = product_status;
        }

        private String getProduct_status() {
            return product_status;
        }
    }

    private NotificationModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_title, txt_content, txt_date, txt_username;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_logo);
            txt_title = itemView.findViewById(R.id.txt_notification_title);
            txt_content = itemView.findViewById(R.id.txt_content);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_username = itemView.findViewById(R.id.txt_username);
        }
    }
}
