package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kldv.gibought.R;
import com.kldv.gibought.Utils.ListbkgoAccountFragment;

public class GoalActivity extends AppCompatActivity {
    public GoalActivity() {
        super(R.layout.activity_goal);
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
                    .add(R.id.addgoac, ListbkgoAccountFragment.class, bundle)
                    .commit();

        }
    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
    }
}