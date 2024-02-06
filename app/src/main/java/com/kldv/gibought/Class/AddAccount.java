package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.kldv.gibought.Class.CRUD.AddActivity;
import com.kldv.gibought.Utils.ListbkAccountFragment;
import com.kldv.gibought.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddAccount extends AppCompatActivity {
    public AddAccount() {
        super(R.layout.activity_add_account);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int id = prefs.getInt("idUser",0);
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);
            bundle.putInt("idUser",id);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.addac, ListbkAccountFragment.class, bundle)
                    .commit();

        }
    }
    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        int sit = prefs.getInt("Situ", 0);
        if (sit == 0) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        } else if (sit == 1) {
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), GraphicsActivity.class));
            finish();
        }
    }

}