package com.kldv.gibought.Class;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.kldv.gibought.Utils.ObgAdapter;
import com.kldv.gibought.Utils.ObgItem;
import com.kldv.gibought.R;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    private ObgAdapter obgAdapter;
    private LinearLayoutCompat layoutObg;
    private MaterialButton buttonObgAct;
    private MaterialButton buttonObgPrev;

    public BannerActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        SharedPreferences preferences = getSharedPreferences("PREFERENCES",MODE_PRIVATE);
        String FistTime = preferences.getString("FistTimeInstall","");
        if(FistTime.equals("YES")){
            Intent intent = new Intent(BannerActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        layoutObg = findViewById(R.id.layoutObg);
        buttonObgAct = findViewById(R.id.buttonNextIndicatorIn);
        buttonObgPrev = findViewById(R.id.buttonPrevIndicatorIn);
        MaterialButton buttonObSK = findViewById(R.id.btSkip);


        setupObgItems();
        final ViewPager2 obgViewPager = findViewById(R.id.obgViewPager);
        obgViewPager.setAdapter(obgAdapter);

        setupObgIns();
        setCurrentObgIn(0);

        obgViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentObgIn(position);
            }
        });

        buttonObgAct.setOnClickListener(view -> {
                    if (obgViewPager.getCurrentItem() + 1 < obgAdapter.getItemCount()) {
                        obgViewPager.setCurrentItem(obgViewPager.getCurrentItem() + 1);
                    } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("FistTimeInstall", "YES");
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }

        );
        buttonObgPrev.setOnClickListener(view -> {
                    if (obgViewPager.getCurrentItem() - 1 < obgAdapter.getItemCount()) {
                        obgViewPager.setCurrentItem(obgViewPager.getCurrentItem() - 1);
                    }
                }

        );
        buttonObSK.setOnClickListener(view -> {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("FistTimeInstall", "YES");
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

        );

    }

    private void setupObgItems() {

        List<ObgItem> obgItems = new ArrayList < > ();

        ObgItem itemSlide1 = new ObgItem();
        itemSlide1.setTitle((String) getResources().getText(R.string.about_app_title));
        itemSlide1.setDesc((String) getResources().getText(R.string.about_app_text));
        itemSlide1.setImg(R.drawable.slide1);

        ObgItem itemSlide2 = new ObgItem();
        itemSlide2.setTitle((String) getResources().getText(R.string.features_app_title));
        itemSlide2.setDesc((String) getResources().getText(R.string.features_app_text));
        itemSlide2.setImg(R.drawable.slide2);

        ObgItem itemSlide3 = new ObgItem();
        itemSlide3.setTitle((String) getResources().getText(R.string.deluxe_app_title));
        itemSlide3.setDesc((String) getResources().getText(R.string.deluxe_app_text));
        itemSlide3.setImg(R.drawable.slide3);

        ObgItem itemSlide4 = new ObgItem();
        itemSlide4.setTitle((String) getResources().getText(R.string.benefits_app_title));
        itemSlide4.setDesc((String) getResources().getText(R.string.benefits_app_text));
        itemSlide4.setImg(R.drawable.slide4);

        obgItems.add(itemSlide1);
        obgItems.add(itemSlide2);
        obgItems.add(itemSlide3);
        obgItems.add(itemSlide4);

        obgAdapter = new ObgAdapter(obgItems);

    }


    private void setupObgIns() {
        ImageView[] ins = new ImageView[obgAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(

                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT

        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < ins.length; i++) {
            ins[i] = new ImageView(getApplicationContext());
            ins[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.obg_indicator_inactive
            ));
            ins[i].setLayoutParams(layoutParams);
            layoutObg.addView(ins[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentObgIn(int index) {
        int childCount = layoutObg.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutObg.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.obg_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.obg_indicator_inactive)
                );
            }
        }

        if (index == obgAdapter.getItemCount() - 1) {
            buttonObgAct.setText(getResources().getText(R.string.finish));
        } else {
            buttonObgAct.setText(getResources().getText(R.string.next));
        }
        if (index == obgAdapter.getItemCount() - 1) {
            buttonObgPrev.setText(getResources().getText(R.string.back));
            buttonObgPrev.setEnabled(true);
            buttonObgPrev.setVisibility(View.VISIBLE);
        } else if (index == 0) {
            buttonObgPrev.setEnabled(false);
            buttonObgPrev.setVisibility(View.INVISIBLE);
        } else {
            buttonObgPrev.setText(getResources().getText(R.string.back));
            buttonObgPrev.setEnabled(true);
            buttonObgPrev.setVisibility(View.VISIBLE);
        }

    }

}