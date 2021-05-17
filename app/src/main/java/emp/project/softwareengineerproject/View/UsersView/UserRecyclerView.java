package emp.project.softwareengineerproject.View.UsersView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersService;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.LoginActivityView;

import static android.content.Context.MODE_PRIVATE;

public class UserRecyclerView extends RecyclerView.Adapter<UserRecyclerView.MyViewHolder> {
    List<UserModel> list;
    Context context;
    IUsers.IUsersPresenter presenter;

    public UserRecyclerView(List<UserModel> list, Context context, IUsers.IUsersView activity) {
        this.list = list;
        this.context = context;
        this.presenter = new UsersPresenter(activity, UsersService.getInstance());
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
        final UserModel model = getItem(position);
        final Blob b = (Blob) model.getUserPicture();
        final int[] blobLength = new int[1];
        try {
            blobLength[0] = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
            Glide.with(context)
                    .load(blobAsBytes)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(holder.circleImageView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        holder.txt_userName.setText(model.getUsername());
        holder.txt_userId.setText(model.getUserID());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle("Delete Item");
                dialogBuilder.setIcon(R.drawable.ic_delete);
                dialogBuilder.setMessage("Are you sure you want to delete this User?: " + model.getFullName());
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mPrefs = context.getSharedPreferences(LoginActivityView.MyPREFERENCES, MODE_PRIVATE); //add key
                        String username = mPrefs.getString(LoginActivityView.USERNAME_PREFS, null);
                        presenter.onCardViewLongClicked(model.getUsername(), username);
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

    private UserModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txt_userId, txt_userName;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_userId = itemView.findViewById(R.id.txt_user_id);
            txt_userName = itemView.findViewById(R.id.txt_user_username);
            circleImageView = itemView.findViewById(R.id.profile_image);
            cardView = itemView.findViewById(R.id.cardView_Users);
        }
    }
}
