package com.kldv.gibought.Utils.Error.Network;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.kldv.gibought.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class OfflineNetwork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Detecta Se há conexão com Internet
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                NetworkInfo.State.CONNECTED) {
            finish();
        }
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_offline_network);
            AppCompatButton btnRetry = findViewById(R.id.btnRetry);
            TextView offlinetxt = findViewById(R.id.offtxt);
            btnRetry.setOnClickListener(v -> {
                //Se houver Internet
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() ==
                        NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() ==
                        NetworkInfo.State.CONNECTED) {
                    finish();
                } else {
                    offlinetxt.setVisibility(View.INVISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(() -> offlinetxt.setVisibility(View.VISIBLE), 200);
                }
            });
        }
    }
}