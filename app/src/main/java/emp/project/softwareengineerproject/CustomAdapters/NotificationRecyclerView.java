package emp.project.softwareengineerproject.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return new NotificationRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel model = getItem(position);
        holder.txt_title.setText(model.getNotif_title());
        holder.txt_date.setText(model.getNotif_date());
        holder.txt_content.setText(model.getNotif_content());
        //set imageView Later
    }

    private NotificationModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_title, txt_content, txt_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_logo);
            txt_title = itemView.findViewById(R.id.txt_notification_title);
            txt_content = itemView.findViewById(R.id.txt_content);
            txt_date = itemView.findViewById(R.id.txt_date);
        }
    }
}
