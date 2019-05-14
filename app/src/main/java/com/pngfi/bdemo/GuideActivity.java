package com.pngfi.bdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pngfi.bdemo.guide.GuideTransformer;
import com.pngfi.bdemo.guide.GuideViewPager;
import com.pngfi.bdemo.guide.indicator.DotIndicator;
import com.pngfi.bdemo.guide.interf.GuideViewHolder;
import com.pngfi.bdemo.guide.interf.GuideViewPagerPageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 原文地址：https://www.jianshu.com/p/4708e7da2013
 */
public class GuideActivity extends AppCompatActivity implements GuideViewPagerPageListener {

    private GuideViewPager mViewPager;
    private DotIndicator dotView;

    private LinearLayout mIn_ll;
    private List<String> mViewList;
    private ImageView mLight_dots;
    private int mDistance;
    private ImageView mOne_dot;
    private ImageView mTwo_dot;
    private ImageView mThree_dot;
    private Button mBtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();

        mViewPager.setOnPageChangeListener(this);
        mViewPager.setViewHolder(new GuideViewHolderImp());
        mViewPager.addIndicator(dotView);
        mViewPager.setData(mViewList);
        mViewPager.setPageTransformer(true,new GuideTransformer());

    }

    private void initData() {
        mViewList = new ArrayList<>();
        mViewList.add("https://pixabay.com/get/eb3db00821f1013ed1584d05fb1d4797e27fe4d21cb60c4090f5c678a6e8b5bcdd_640.jpg");
        mViewList.add("https://pixabay.com/get/e136b8082ff11c22d2524518b74d4797eb71e5d01eac104490f7c078a2edb1bd_640.jpg");
        mViewList.add("https://pixabay.com/get/e137b90a2ff41c22d2524518b74d4797eb71e5d01eac104490f7c078a2edb1bd_640.jpg");
    }

    private void initView() {
        mViewPager = findViewById(R.id.in_viewpager);
        dotView = findViewById(R.id.dotView);

        mIn_ll = (LinearLayout) findViewById(R.id.in_ll);
        mLight_dots = (ImageView) findViewById(R.id.iv_light_dots);
        mBtn_next = (Button) findViewById(R.id.bt_next);
    }

    @Override
    public void currentItem(int sum, int position, boolean isEnd) {
        LOG("currentItem" + sum + " position " + position + " isEnd " + isEnd);

        if(isEnd){
            mBtn_next.setVisibility(View.VISIBLE);
        }else{
            mBtn_next.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class GuideViewHolderImp implements GuideViewHolder<String> {
        private boolean isRoundedCorner = true;

        public GuideViewHolderImp() {

        }

        public GuideViewHolderImp(boolean roundedCorner) {
            isRoundedCorner = roundedCorner;
        }

        @Override
        public View getView(Context context, final int position, String data) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_discover_home_viewpager, null);

            ImageView imageView = view.findViewById(R.id.iv_commodity_picture);

            Glide.with(imageView)
                    .load(data)
//					.apply(options)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(GuideActivity.this, "click" + position, Toast.LENGTH_SHORT).show();

                }
            });
            return view;
        }
    }

    private void LOG(String data){
        Log.i("spoort_list","轮播图控件： " + data);
    }
}
