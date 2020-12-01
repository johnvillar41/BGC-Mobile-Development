package emp.project.softwareengineerproject.View.UsersView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.Interface.IUsers.IUsersAdd;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersAddPresenter;
import emp.project.softwareengineerproject.R;

public class UsersAddActivityView extends AppCompatActivity implements IUsersAdd.IUsersAddView {
    IUsersAdd.IUsersAddPresenter presenter;
    TextInputLayout txt_username, txt_password1, txt_password2, txt_realName;
    CircleImageView profile_imagePicture;
    MaterialButton btn_add_user;
    private static int IMAGE_PICK_CODE = 777;
    private static InputStream fileInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users_add_view);
        initViews();
    }

    @Override
    public void initViews() {
        presenter = new UsersAddPresenter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_username = findViewById(R.id.txt_username);
        txt_password1 = findViewById(R.id.txt_password1);
        txt_password2 = findViewById(R.id.txt_password2);
        txt_realName = findViewById(R.id.txt_fullName);
        btn_add_user = findViewById(R.id.btn_add_user);
        profile_imagePicture = findViewById(R.id.image_profile);

        Glide.with(this).load(R.drawable.add_image).into(profile_imagePicture);

        btn_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.onAddButtonClicked(txt_username, txt_password1, txt_password2, txt_realName, fileInputStream, v);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        profile_imagePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onImageButtonClicked();
            }
        });
    }

    @Override
    public void loadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onStatusDisplayMessage(String message, View v) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap originBitmap = null;
        Uri selectedImage;

        try {
            selectedImage = data.getData();
        } catch (NullPointerException e) {
            return;
        }

        InputStream imageStream;

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK
                && null != data) {

            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (originBitmap != null) {
                profile_imagePicture.setImageBitmap(originBitmap);
                Bitmap image = ((BitmapDrawable) profile_imagePicture.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                fileInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}