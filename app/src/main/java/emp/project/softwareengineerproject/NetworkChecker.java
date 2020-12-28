package emp.project.softwareengineerproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import java.lang.ref.WeakReference;

import emp.project.softwareengineerproject.View.LoginActivityView;

public class NetworkChecker {
    private static NetworkChecker SINGLE_INSTANCE = null;
    private WeakReference<Context> weakReferenceContext;
    private WeakReference<LoginActivityView> weakReferenceActivity;

    private NetworkChecker(Context context) {
        this.weakReferenceContext = new WeakReference<>(context);
    }


    private NetworkChecker(WeakReference<LoginActivityView> weakReferenceActivity) {
        this.weakReferenceActivity = weakReferenceActivity;
    }

    public static NetworkChecker getSingleInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new NetworkChecker(context);
        }
        return SINGLE_INSTANCE;
    }

    public static NetworkChecker getSingleInstance(WeakReference<LoginActivityView> weakReferenceActivity) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new NetworkChecker(weakReferenceActivity);
        }
        return SINGLE_INSTANCE;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) weakReferenceContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void displayNoNetworkConnection() {
        if (!isNetworkAvailable()) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(weakReferenceContext.get());
            LayoutInflater inflater = ((Activity) weakReferenceContext.get()).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_popup_no_network, null);
            dialogBuilder.setView(dialogView);
            AlertDialog dialog = dialogBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }
    }


}
