package com.pngfi.bdemo.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.util.List;

public class BannerViewPager extends ViewPager {

    BannerPageAdapter mAdapter;
    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBannerAdapter( BannerPageAdapter adapter) {
        mAdapter = adapter;
        super.setAdapter(mAdapter);

    }

    public <T> void setData(List<T> data) {
        mAdapter.setData(data);
        setCurrentItem(1);
    }

    @Override
    public void setCurrentItem(int item) {
        LOG("setCurrentItem" + item + "   mAdapter.toPosition(item)  " + mAdapter.toPosition(item));
//        super.setCurrentItem(mAdapter.toPosition(item));
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        LOG("setCurrentItem boolean   " + item + " to " + mAdapter.toPosition(item));
//        BannerViewPager.super.setCurrentItem(mAdapter.toPosition(item),smoothScroll);
        BannerViewPager.super.setCurrentItem(item,smoothScroll);
    }

    private void LOG(String data){
        Log.i("spoort_list","BannerViewPager： " + data);
    }

}
