package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;

@SuppressWarnings({"SpellCheckingInspection"})
public class NotificationActivity extends AppCompatActivity {
    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        @SuppressLint("CutPasteId") ImageView add = findViewById(R.id.btnAdc);
        add.setOnClickListener(view -> {
            @SuppressLint("CutPasteId") ImageView bt_add = findViewById(R.id.btnAdc);
            bt_add.setImageResource(R.drawable.baddoff);
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
        });
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int idUser = prefs.getInt("idUser", 0);
        boolean exist = bkAccountDao.searchselectbk(idUser, getApplicationContext());
        if (!exist){
            add.setImageDrawable(getDrawable(R.drawable.baddoff));
            add.setEnabled(false);
        }
        else{
            add.setImageDrawable(getDrawable(R.drawable.badd));
            add.setEnabled(true);
        }
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bot_nav_view);

        mbottomNavigationView.setSelectedItemId(R.id.app_notif);

        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.app_home:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(0, 0 );
                    return true;

                case R.id.app_graphics:
                    startActivity(new Intent(getApplicationContext(),GraphicsActivity.class));
                    overridePendingTransition(0, 0 );
                    return true;


                case R.id.app_engine:
                    startActivity(new Intent(getApplicationContext(),ConfigActivity.class));
                    overridePendingTransition(0, 0 );
                    return true;

            }

            return false;
        });
    }
}