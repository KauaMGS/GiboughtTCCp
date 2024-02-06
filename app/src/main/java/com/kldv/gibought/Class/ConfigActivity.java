package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Dao.bkAccountDao;
import com.kldv.gibought.R;
import com.kldv.gibought.Utils.NotiFActivity;

public class ConfigActivity extends AppCompatActivity {
    @SuppressLint({"NonConstantResourceId", "CommitPrefEdits", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);

        @SuppressLint("CutPasteId") ImageView add = findViewById(R.id.btnAdc);
        add.setOnClickListener(view -> {
            @SuppressLint("CutPasteId") ImageView bt_add = findViewById(R.id.btnAdc);
            bt_add.setImageResource(R.drawable.baddoff);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putInt("SituAdd", 3);
            edit.putInt("SituBottomMenu", 4);
            edit.apply();
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
        });
        TextView UserName = findViewById(R.id.userName);
        TextView UserEmail = findViewById(R.id.userEmail);
        ImageView Exit = findViewById(R.id.Exit);
        int idUser = prefs.getInt("idUser", 0);
        String name = prefs.getString("nameUser", String.valueOf(R.string.username));
        String email = prefs.getString("emailUser", String.valueOf(R.string.email_profile));
        UserName.setText(name);
        UserEmail.setText(email);

        SharedPreferences prefers = getSharedPreferences("bkCardSelected",
                Context.MODE_PRIVATE);
        SharedPreferences preferUs = getSharedPreferences("userValue",
                Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        BottomNavigationView mbottomNavigationView = findViewById(R.id.bat_nav_view);
        mbottomNavigationView.setSelectedItemId(R.id.app_exit);

        Exit.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Logged", "NO");
            editor.apply();
            SharedPreferences.Editor editar = prefers.edit();
            editar.putString("nameBank", getString(R.string.none));
            editar.apply();
            prefs.edit().clear();
            preferUs.edit().clear();
            prefers.edit().clear();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        });
        boolean exist = bkAccountDao.searchselectbk(idUser, getApplicationContext());
        if (!exist){
            add.setImageDrawable(getDrawable(R.drawable.baddoff));
            add.setEnabled(false);
        }
        else{
            add.setImageDrawable(getDrawable(R.drawable.badd));
            add.setEnabled(true);
        }
        mbottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.app_home:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(
                            R.transition.anim_slide_in_right,
                            R.transition.anim_slide_out_right
                    );
                    return true;

                case R.id.app_notif:
                    startActivity(new Intent(getApplicationContext(), NotiFActivity.class));
                    overridePendingTransition(
                            0,
                            0
                    );
                    return true;

                case R.id.app_graphics:
                    startActivity(new Intent(getApplicationContext(),GraphicsActivity.class));
                    overridePendingTransition(
                            R.transition.anim_slide_in_right,
                            R.transition.anim_slide_out_right
                    );
                    return true;


            }
            return false;
        });
    }
    public void onBackPressed(){}
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.app_engine).setVisible(false);
        return true;
    }


}