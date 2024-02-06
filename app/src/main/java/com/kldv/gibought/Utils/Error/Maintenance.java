package com.kldv.gibought.Utils.Error;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.kldv.gibought.Connex.Connexion;
import com.kldv.gibought.R;

import java.sql.Connection;

public class Maintenance extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Connection conn = Connexion.join(getApplicationContext());
        if (conn != null) {
            finish();
        }
        else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maintenance);
            AppCompatButton btnRetry = findViewById(R.id.btnRetry);
            btnRetry.setOnClickListener(v -> {
                try {
                    //noinspection ConstantConditions
                    if (conn != null) {
                        finish();
                    }
                } catch (Exception e) {
                    Log.d("Erro", String.valueOf(e));
                }
            });
        }
        }
        //fechar ao voltar
        @Override
        public void onBackPressed () {
            finishAffinity();
        }
}