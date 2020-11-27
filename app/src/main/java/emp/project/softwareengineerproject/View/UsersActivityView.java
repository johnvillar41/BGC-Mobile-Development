package emp.project.softwareengineerproject.View;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.sql.Blob;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.IUsersActivity;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Presenter.UsersPresenter;
import emp.project.softwareengineerproject.R;

public class UsersActivityView extends AppCompatActivity implements IUsersActivity.IUsersView {
    CircleImageView circleImageView;
    CardView cardView;
    IUsersActivity.IUsersPresenter presenter;
    SharedPreferences sharedPreferences;
    TextView txt_user_id, txt_username, txt_password, txt_real_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users_view);

        try {
            initViews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViews() throws SQLException, ClassNotFoundException {
        presenter = new UsersPresenter(this);
        circleImageView = findViewById(R.id.profile_image);
        cardView = findViewById(R.id.cardView_Profile);
        txt_user_id = findViewById(R.id.user_id);
        txt_username = findViewById(R.id.user_username);
        txt_password = findViewById(R.id.user_password);
        txt_real_name = findViewById(R.id.user_real_name);
        sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES_USERNAME, MODE_PRIVATE);
        presenter.onPageDisplayProfile(sharedPreferences.getString(LoginActivityView.MyPREFERENCES_USERNAME, null));

    }

    @Override
    public void displayProfile(UserModel model) {
        Blob b = model.getUser_image();
        int blobLength;
        try {
            blobLength = (int) b.length();
            byte[] blobAsBytes = b.getBytes(1, blobLength);
            Bitmap btm = BitmapFactory.decodeByteArray(blobAsBytes, 0, blobAsBytes.length);
            Glide.with(this).load(btm).into(circleImageView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txt_user_id.setText(String.valueOf(model.getUser_id()));
        txt_username.setText(model.getUser_username());
        txt_password.setText(model.getUser_password());
        txt_real_name.setText(model.getUser_full_name());
    }
}