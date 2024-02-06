package com.kldv.gibought.Utils.Error.Network;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kldv.gibought.*;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
@SuppressWarnings({"SpellCheckingInspection", "RedundantIfStatement", "deprecation"})
public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        //Internet Offline
        if (!Common.isConnectedToInternet(ctx)){
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            View layout_dialog = LayoutInflater.from(ctx).inflate(R.layout.check_internet_dialog, null);
            builder.setView(layout_dialog);
            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btnRetry);
            //Abrir Erro de conexão
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            btnRetry.setOnClickListener(v -> {
                dialog.dismiss();
                onReceive(ctx, intent);
            });
        }
    }
}
