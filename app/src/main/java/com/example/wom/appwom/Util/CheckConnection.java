package com.example.wom.appwom.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by hotrongtam on 7/1/2017.
 */

public class CheckConnection {
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectionWifi = false;
        boolean haveConnectionMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectionWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectionMobile = true;


        }
        return haveConnectionWifi || haveConnectionMobile;
    }
    public static void ShowToast_Short(Context context,String thongbao){

        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }

}
