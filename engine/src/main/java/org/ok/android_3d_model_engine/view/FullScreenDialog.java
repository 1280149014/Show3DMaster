package org.ok.android_3d_model_engine.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.ok.android_3d_model_engine.R;

import androidx.annotation.NonNull;

public class FullScreenDialog extends Dialog {

    ImageView iconlight;
    AlphaAnimation alphaAnimation;

    public FullScreenDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialog);
        initView(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = d.getHeight();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        iconlight = findViewById(R.id.iconlight);

        alphaAnimation = new AlphaAnimation(0.8f,0.2f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
//        Interpolator interpolator = new AccelerateDecelerateInterpolator();
//        alphaAnimation.setInterpolator(interpolator);

    }

    public void startAnimation(){
        iconlight.startAnimation(alphaAnimation);
    }

    public void stopAnimation(){
        iconlight.clearAnimation();
    }

}

