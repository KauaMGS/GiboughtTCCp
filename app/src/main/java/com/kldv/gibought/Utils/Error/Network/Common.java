package com.kldv.gibought.Utils.Error.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
@Deprecated
@SuppressWarnings({"SpellCheckingInspection", "deprecation",
        "BooleanMethodIsAlwaysInverted", "AccessStaticViaInstance",
        "CStyleArrayDeclaration", "ForLoopReplaceableByForEach"})

public class Common {

    public static boolean isConnectedToInternet(Context ctx) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info[] = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }

        return false;
    }

}
