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
        switch (model.getNotif_title()) {
            case "Deleted product":
                Glide.with(context).load(R.drawable.delete_logo).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                break;
            case "Updated product":
                Glide.with(context).load(R.drawable.update_logo).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                break;
            case "Added product":
                Glide.with(context).load(R.drawable.add_product).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                break;
            case "Added sales":
                Glide.with(context).load(R.drawable.ic_money_large).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                break;
            case"Added new User":
                Glide.with(context).load(R.drawable.ic_add_user).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(holder.circleImageView);
                break;
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
