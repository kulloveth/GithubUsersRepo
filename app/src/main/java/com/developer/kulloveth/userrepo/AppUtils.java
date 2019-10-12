package com.developer.kulloveth.userrepo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AppUtils {

    public static void checkInternet(Context context, ProgressBar progressBar){
        if (haveNetwork(context)) {
            Toast.makeText(context, "network access", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "no network access", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean haveNetwork(Context context) {
        boolean have_WIFI = false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) ;
            if (info.isConnected())
                if (info.isConnected())
                    have_WIFI = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;
        }
        return have_MobileData || have_WIFI;
    }




}
