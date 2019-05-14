package com.pngfi.bdemo.vierpager_ani;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAniRotation implements ViewPager.PageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0F;
    private float mMaxRotate = 15.0F;

    public ViewPagerAniRotation() {
    }

    @Override
    public void transformPage(View view, float position) {
        if (position < -1.0F) {
            view.setRotation(this.mMaxRotate * -1.0F);
            view.setPivotX((float)view.getWidth());
            view.setPivotY((float)view.getHeight());
        } else if (position <= 1.0F) {
            if (position < 0.0F) {  //-1
                view.setPivotX((float)view.getWidth() * (0.5F + 0.5F * -position));
                view.setPivotY((float)view.getHeight());
                view.setRotation(this.mMaxRotate * position);
            } else {    //0,1
                view.setPivotX((float)view.getWidth() * 0.5F * (1.0F - position));
                view.setPivotY((float)view.getHeight());
                view.setRotation(this.mMaxRotate * position);
            }
        } else {    //大于1
            view.setRotation(this.mMaxRotate);
            view.setPivotX((float)(view.getWidth() * 0));
            view.setPivotY((float)view.getHeight());
        }

    }
}
