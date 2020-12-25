package emp.project.softwareengineerproject;

import android.content.Context;

import com.bumptech.glide.Glide;

import java.io.File;
import java.lang.ref.WeakReference;

public class CacheManager {
    /**
     * SingleTon
     */
    private static CacheManager SINGLE_INSTANCE = null;
    private static WeakReference<Context> contextWeakReference;

    private CacheManager() {

    }

    public static CacheManager getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new CacheManager();
        }
        contextWeakReference = new WeakReference<>(context);
        return SINGLE_INSTANCE;
    }


    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    /**
     * May be removed due to exception
     */
    public void clearGlideMemory() {
        Glide.get(contextWeakReference.get().getApplicationContext()).clearMemory();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(contextWeakReference.get().getApplicationContext()).clearDiskCache();
            }
        });
        thread.start();
    }

}
