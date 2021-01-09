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

public class NetworkChecker {
    private WeakReference<Context> weakReferenceContext;

    public NetworkChecker(Context context) {
        this.weakReferenceContext = new WeakReference<>(context);
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = null;
        NetworkInfo activeNetworkInfo = null;
        try {
            connectivityManager = (ConnectivityManager) weakReferenceContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch(Exception ignored){

        }


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
