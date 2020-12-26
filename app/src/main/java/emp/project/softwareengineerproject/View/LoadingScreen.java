package emp.project.softwareengineerproject.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineerproject.CacheManager;
import emp.project.softwareengineerproject.R;

public class LoadingScreen extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txt1, txt2, txt3;
    Animation animationUtils1, animationUtils2, animationUtils3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_screen);

        circleImageView = findViewById(R.id.image_logo);
        txt1 = findViewById(R.id.txt1st);
        txt2 = findViewById(R.id.txt2nd);
        txt3 = findViewById(R.id.txt3rd);

        animationUtils1 = AnimationUtils.loadAnimation(this, R.anim.loading1);
        animationUtils2 = AnimationUtils.loadAnimation(this, R.anim.loading2);
        animationUtils3 = AnimationUtils.loadAnimation(this, R.anim.loading3);

        circleImageView.setAnimation(animationUtils1);
        txt1.setAnimation(animationUtils1);
        txt2.setAnimation(animationUtils2);
        txt3.setAnimation(animationUtils3);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingScreen.this, LoginActivityView.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        try {
            File dir = getCacheDir();
            CacheManager cacheManager = CacheManager.getInstance(getApplicationContext());
            cacheManager.deleteDir(dir);
            //cacheManager.clearGlideMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onTrimMemory(TRIM_MEMORY_RUNNING_CRITICAL);
        super.onDestroy();
    }
}