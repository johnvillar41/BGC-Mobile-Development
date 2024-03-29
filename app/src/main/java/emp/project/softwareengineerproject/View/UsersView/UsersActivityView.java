package emp.project.softwareengineerproject.View.UsersView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.ProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.Interface.IUsers.IUsers;
import emp.project.softwareengineerproject.Model.Bean.UserModel;
import emp.project.softwareengineerproject.Model.Database.Services.UsersService.UsersService;
import emp.project.softwareengineerproject.Presenter.UsersPresenter.UsersPresenter;
import emp.project.softwareengineerproject.R;
import emp.project.softwareengineerproject.View.LoginActivityView;

public class UsersActivityView extends AppCompatActivity implements IUsers.IUsersView {
    private CardView cardView;
    private IUsers.IUsersPresenter presenter;
    private TextInputLayout txt_user_id, txt_username, txt_password, txt_real_name;
    private ProgressIndicator progressIndicator;

    private static int numberOfDialogs = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users_view);

        presenter = new UsersPresenter(this, UsersService.getInstance());
        presenter.initializeViews();
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_final_toolbar);
        toolbar.setTitle("User Profile");

        PROFILE_PICTURE = findViewById(R.id.profile_image);
        cardView = findViewById(R.id.cardView_Profile);
        txt_user_id = findViewById(R.id.user_id);
        txt_username = findViewById(R.id.user_username);
        txt_password = findViewById(R.id.user_password);
        txt_real_name = findViewById(R.id.user_real_name);
        txt_username.setEnabled(false);
        txt_password.setEnabled(false);
        txt_real_name.setEnabled(false);
        cardView = findViewById(R.id.cardView_Profile);
        progressIndicator = findViewById(R.id.progress_bar_users);
        progressIndicator.hide();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_view);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, MODE_PRIVATE);
        presenter.onPageDisplayProfile(sharedPreferences.getString(LoginActivityView.USERNAME_PREFS, null));

        Animation atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        Animation atg2 = AnimationUtils.loadAnimation(this, R.anim.atg2);
        cardView.setAnimation(atg);
        PROFILE_PICTURE.setAnimation(atg2);
        PROFILE_PICTURE.setEnabled(false);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onViewButtonClicked();
            }
        });

        PROFILE_PICTURE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onImageClicked();
            }
        });

    }

    private static final int IMAGE_PICK_CODE = 777;
    public static InputStream FILE_INPUT_STREAM;

    @Override
    public void displayPopupUsers(final List<UserModel> userList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                numberOfDialogs++;
                if (numberOfDialogs == 1) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UsersActivityView.this);
                    LayoutInflater inflater = (UsersActivityView.this).getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.custom_popup_show_users, null);
                    dialogBuilder.setView(dialogView);

                    RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView_Users);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(UsersActivityView.this, LinearLayoutManager.VERTICAL, false);
                    UserRecyclerView adapter = new UserRecyclerView(
                            userList, UsersActivityView.this, UsersActivityView.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                    AlertDialog dialog = dialogBuilder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();

                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            numberOfDialogs = 0;
                        }
                    });
                }
            }
        });

    }

    @Override
    public void goToAddPage() {
        Intent intent = new Intent(this, UsersAddActivityView.class);
        startActivity(intent);
    }

    @Override
    public void displayProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.show();
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressIndicator.hide();
            }
        });
    }

    private static CircleImageView PROFILE_PICTURE;

    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        super.onDestroy();
    }

    @Override
    public void displayStatusMessage(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UsersActivityView.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }



    @Override
    public void displayProfile(UserModel model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Blob b = model.getUserPicture();
                    int blobLength;
                    blobLength = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength);
                    Glide.with(UsersActivityView.this)
                            .load(blobAsBytes)
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .into(PROFILE_PICTURE);
                } catch (SQLException ignored) {

                }
                txt_user_id.getEditText().setText(String.valueOf(model.getUserID()));
                txt_username.getEditText().setText(model.getUsername());
                txt_password.getEditText().setText(model.getPassword());
                txt_real_name.getEditText().setText(model.getFullName());
            }
        });
    }

    @Override
    public boolean makeTextViewsEdittable() {
        boolean isEnabled;
        if (txt_password.isEnabled() || txt_password.isEnabled() || txt_real_name.isEnabled() || PROFILE_PICTURE.isEnabled()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_username.setEnabled(false);
                    txt_password.setEnabled(false);
                    txt_real_name.setEnabled(false);
                    PROFILE_PICTURE.setEnabled(false);
                }
            });
            isEnabled = false;
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayStatusMessage("You can now Edit your credentials! To save your credentials click on edit icon again!");
                    txt_username.setEnabled(true);
                    txt_password.setEnabled(true);
                    txt_real_name.setEnabled(true);
                    PROFILE_PICTURE.setEnabled(true);
                }
            });
            isEnabled = true;
        }
        return isEnabled;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_addfav) {
            presenter.onAddButtonClicked();
        } else if (item.getItemId() == R.id.action_editfav) {
            presenter.onEditAccountButtonClicked(
                    txt_user_id.getEditText().getText().toString(),
                    txt_username.getEditText().getText().toString(),
                    txt_password.getEditText().getText().toString(),
                    txt_real_name.getEditText().getText().toString(),
                    FILE_INPUT_STREAM);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressIndicator.show();
                    }
                });
                Bitmap originBitmap = null;
                Uri selectedImage;
                try {
                    selectedImage = data.getData();
                } catch (NullPointerException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressIndicator.hide();
                        }
                    });
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
                        final Bitmap finalOriginBitmap = originBitmap;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PROFILE_PICTURE.setImageBitmap(finalOriginBitmap);
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap image = ((BitmapDrawable) PROFILE_PICTURE.getDrawable()).getBitmap();
                                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                        FILE_INPUT_STREAM = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressIndicator.hide();
                                            }
                                        });
                                    }
                                });
                                thread1.start();
                            }
                        });
                    } else {
                        FILE_INPUT_STREAM = null;
                        PROFILE_PICTURE = null;
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void loadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void removeUserCredentialsOnSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivityView.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void finishActivity() {
        finish();
        Intent intent = new Intent(this, LoginActivityView.class);
        startActivity(intent);
    }

    @Override
    public void setErrorOnUsername(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_username.setError(errorMessage);
            }
        });
    }

    @Override
    public void setErrorOnPassword(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_password.setError(errorMessage);
            }
        });
    }

    @Override
    public void setErrorOnRealName(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_real_name.setError(errorMessage);
            }
        });
    }

    @Override
    public void removeErrorOnUsername() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_username.setError(null);
            }
        });
    }

    @Override
    public void removeErrorOnPassword() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_password.setError(null);
            }
        });
    }

    @Override
    public void removeErrorOnRealName() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_real_name.setError(null);
            }
        });
    }
}