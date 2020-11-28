package emp.project.softwareengineerproject.View.UsersActivityView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.CustomAdapters.UserRecyclerView;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.UserModel;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class UsersActivityView extends AppCompatActivity implements IUsers.IUsersView {
    private CircleImageView circleImageView;
    private CardView cardView;
    private IUsers.IUsersPresenter presenter;
    private TextInputLayout txt_user_id, txt_username, txt_password, txt_real_name;


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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("User Profile");

        circleImageView = findViewById(R.id.profile_image);
        cardView = findViewById(R.id.cardView_Profile);
        txt_user_id = findViewById(R.id.user_id);
        txt_username = findViewById(R.id.user_username);
        txt_password = findViewById(R.id.user_password);
        txt_real_name = findViewById(R.id.user_real_name);
        cardView = findViewById(R.id.cardView_Profile);
        FloatingActionButton floatingActionButton=findViewById(R.id.fab_view);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES_USERNAME, MODE_PRIVATE);
        presenter.onPageDisplayProfile(sharedPreferences.getString(LoginActivityView.MyPREFERENCES_USERNAME, null));

        Animation atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        Animation atg2 = AnimationUtils.loadAnimation(this, R.anim.atg2);
        cardView.setAnimation(atg);
        circleImageView.setAnimation(atg2);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onViewButtonClicked();
            }
        });

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
        txt_user_id.getEditText().setText(String.valueOf(model.getUser_id()));
        txt_username.getEditText().setText(model.getUser_username());
        txt_password.getEditText().setText(model.getUser_password());
        txt_real_name.getEditText().setText(model.getUser_full_name());
    }

    @Override
    public void displayPopupUsers(final List<UserModel> userList) throws InterruptedException {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UsersActivityView.this);
        LayoutInflater inflater = (UsersActivityView.this).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_show_users, null);
        dialogBuilder.setView(dialogView);

        final RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView_Users);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final LinearLayoutManager layoutManager
                        = new LinearLayoutManager(UsersActivityView.this, LinearLayoutManager.VERTICAL, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserRecyclerView adapter = new UserRecyclerView(
                                userList, UsersActivityView.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        thread.start();
        thread.join();


        final AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void goToAddPage() {
        Intent intent=new Intent(this,UsersAddActivityView.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.action_addfav) {
            presenter.onAddButtonClicked();
        }
        return super.onOptionsItemSelected(item);
    }
}