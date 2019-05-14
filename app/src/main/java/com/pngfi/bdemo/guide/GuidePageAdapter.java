package com.pngfi.bdemo.guide;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pngfi.bdemo.R;
import com.pngfi.bdemo.banner.BannerViewHolder;
import com.pngfi.bdemo.guide.interf.GuideViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GuidePageAdapter<T> extends PagerAdapter {
    private List<T> mViewList;
    private GuideViewHolder<T> mGuideViewHolder;


    public GuidePageAdapter(GuideViewHolder<T> guideViewHolder) {
        this.mGuideViewHolder = guideViewHolder;
        mViewList = new ArrayList();
    }

    public void setData(List<T> lists){
        this.mViewList = lists;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mGuideViewHolder.getView(container.getContext(), position, mViewList.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    private void LOG(String data){
        Log.i("spoort_list","GuidePageAdapter： " + data);
    }
}