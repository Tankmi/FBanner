package com.pngfi.bdemo.vierpager_ani;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAniScale implements ViewPager.PageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0F;
    private float mMaxRotate = 15.0F;
    private float mMaxScale = 1.5F;

    public ViewPagerAniScale() {
    }

    @Override
    public void transformPage(View view, float position) {

        float scale = 1;
        if(position >= -1.0f && position <0.0f){
            scale = position*mMaxScale;
        }else if (position > 0.0f && position <= 1.0f){
            scale = position*mMaxScale;
        }else if(position == 0.0f){
            scale = 1.5f;
        }

        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
