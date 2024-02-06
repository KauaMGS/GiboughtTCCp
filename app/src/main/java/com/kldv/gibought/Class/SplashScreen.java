package com.kldv.gibought.Class;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.kldv.gibought.R;

@SuppressWarnings("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Após 3 segundos chama a função goBanner()//
        new Handler(Looper.getMainLooper()).postDelayed(this::goBanner, 2000);
    }
    
    /*================================================
    Chama a ação de ir para  a Activity BannerActivity
    ================================================*/
    private void goBanner() {
        Intent intent = new Intent(
                SplashScreen.this,BannerActivity.class
        );
        startActivity(intent);
        finish();
    }
}