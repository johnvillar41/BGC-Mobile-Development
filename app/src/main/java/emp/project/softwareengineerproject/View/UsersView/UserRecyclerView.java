package emp.project.softwareengineerproject.View.UsersView;

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
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.R;

public class UserRecyclerView extends RecyclerView.Adapter<UserRecyclerView.MyViewHolder> {
    List<UserModel> list;
    Context context;

    public UserRecyclerView(List<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_adapter_users, parent, false);
        return new UserRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel model = getItem(position);
        final Blob b = (Blob) model.getUser_image();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            Glide.with(context).load(btm).into(holder.circleImageView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_userName.setText(model.getUser_username());
        holder.txt_userId.setText(model.getUser_id());
    }

    private UserModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_userId, txt_userName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_userId = itemView.findViewById(R.id.txt_user_id);
            txt_userName = itemView.findViewById(R.id.txt_user_username);
            circleImageView = itemView.findViewById(R.id.profile_image);
        }
    }
}
