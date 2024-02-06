package com.kldv.gibought.Utils.Animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {
    private Context ctx;
    private TextView txtProgress;
    private ProgressBar progressBar;
    private float from,to;

    public ProgressBarAnimation(Context ctx, TextView txtProgress, ProgressBar progressBar, float from, float to) {
        this.ctx = ctx;
        this.txtProgress = txtProgress;
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        txtProgress.setText((int)value+"%");
        if (value == to){

        }
    }
}
