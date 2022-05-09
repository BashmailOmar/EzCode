package com.arabcoderz.ezcode;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

public class AnimationUtil {

    public static void animate(RecyclerView.ViewHolder holder , boolean goesDown){

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", goesDown==true ? 300 : -300, 0);
        animatorTranslateY.setDuration(1000);
        animatorSet.playTogether(animatorTranslateY);
//        animatorSet.start();
    }
}
