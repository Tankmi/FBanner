package com.pngfi.bdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.pngfi.banner.indicator.DotIndicator;
import com.pngfi.banner.BannerViewPager;
import com.pngfi.banner.adapter.ViewHolder;
import com.pngfi.banner.indicator.NumberIndicator;
import com.pngfi.banner.indicator.TitleIndicator;
import com.pngfi.banner.transformer.RotateUpTransformer;
import com.pngfi.banner.transformer.StackTransformer;
import com.pngfi.bdemo.banner.DiscoverSlideShowView;
import com.pngfi.bdemo.vierpager_ani.ViewPagerAniRotation;

import java.util.Arrays;

/**
 * 原文地址：https://www.jianshu.com/p/4708e7da2013
 */
public class MainActivity extends AppCompatActivity {

    private BannerViewPager firstBanner;
    private BannerViewPager secondBanner;
    private BannerViewPager thirdBanner;
    private BannerViewPager fourthBanner;
    private BannerViewPager fifthBanner;


    private DotIndicator dotView;
    private DotIndicator dotView2;
    private NumberIndicator numberIndicator;
    private TitleIndicator titleIndicator;
    private TitleIndicator titleIndicator2;
    private NumberIndicator numberIndicator2;

    private Button btn_next;


    private String[] images = {
            "https://pixabay.com/get/eb3db00821f1013ed1584d05fb1d4797e27fe4d21cb60c4090f5c678a6e8b5bcdd_640.jpg",
            "https://pixabay.com/get/e136b8082ff11c22d2524518b74d4797eb71e5d01eac104490f7c078a2edb1bd_640.jpg",
            "https://pixabay.com/get/e137b90a2ff41c22d2524518b74d4797eb71e5d01eac104490f7c078a2edb1bd_640.jpg",
            "https://images.pexels.com/photos/1137511/pexels-photo-1137511.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
            "https://pixabay.com/get/ed37b90c21f41c22d2524518b74d4797eb71e5d01eac104490f7c078a2edb1bd_640.jpg",
    };

    private CharSequence[] titles = {"在不一样的十几节", "昨夜小楼又东风", "此去经年", "贝加尔湖畔", "悠长悠长而寂寥的雨巷"};


    protected DiscoverSlideShowView mBanner;

    private void initBanner(){
        mBanner = findViewById(R.id.view_slidmenu);
        mBanner.setImageValues(Arrays.asList(images));
    }

    private void initLoading(){
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG("firstBanner.getCurrentItem():" + firstBanner.getCurrentItem());
                firstBanner.setCurrentItem(firstBanner.getCurrentItem()+1);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        mBanner.startTask(1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBanner();
        initLoading();

        //first
        firstBanner = findViewById(R.id.banner);
        dotView = findViewById(R.id.dotView);
        firstBanner.setViewHolder(new BannerViewHolder());
        firstBanner.addIndicator(dotView);
//        firstBanner.setPageMargin(dp2px(18));
//        firstBanner.setPageTransformer(true, new ViewPagerAniRotation());
        firstBanner.setPageTransformer(true, new StackTransformer());
        firstBanner.setData(Arrays.asList(images));


        //second
        secondBanner = findViewById(R.id.second);
        dotView2 = findViewById(R.id.dotView2);
        secondBanner.setViewHolder(new BannerViewHolder(false));
        secondBanner.addIndicator(dotView2);
        secondBanner.setData(Arrays.asList(images));

        //third
        thirdBanner = findViewById(R.id.thirdBanner);
        numberIndicator = findViewById(R.id.numberIndicator);
        thirdBanner.setViewHolder(new BannerViewHolder());
        thirdBanner.addIndicator(numberIndicator);
        thirdBanner.setData(Arrays.asList(images));

        //fourth
        fourthBanner = findViewById(R.id.fourthBanner);
        titleIndicator = findViewById(R.id.titleIndicator);
        fourthBanner.setViewHolder(new BannerViewHolder(false));
        titleIndicator.setTitles(Arrays.asList(titles));
        fourthBanner.addIndicator(titleIndicator);
        fourthBanner.setData(Arrays.asList(images));


        //fifth
        fifthBanner = findViewById(R.id.fifthBanner);
        titleIndicator2 = findViewById(R.id.titleIndicator2);
        numberIndicator2 = findViewById(R.id.numberIndicator2);

        fifthBanner.setViewHolder(new BannerViewHolder(false));
        fifthBanner.addIndicator(titleIndicator2);
        fifthBanner.addIndicator(numberIndicator2);
        titleIndicator2.setTitles(Arrays.asList(titles));
        fifthBanner.setAutoTurning(false);
        fifthBanner.setData(Arrays.asList(images));
        fifthBanner.setPageTransformer(true, new RotateUpTransformer());

    }



    public class BannerViewHolder implements ViewHolder<String> {
        private boolean isRoundedCorner = true;

        public BannerViewHolder() {

        }

        public BannerViewHolder(boolean roundedCorner) {
            isRoundedCorner = roundedCorner;
        }

        @Override
        public View getView(Context context, final int position, String data) {
            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_banner_apartment_activity, null);
            ImageView imageView = inflate.findViewById(R.id.image);
            if (isRoundedCorner) {
                GlideApp.with(imageView).load(data).transforms(new CenterCrop(), new RoundedCorners(dp2px(10))).disallowHardwareConfig().into(imageView);
            } else {
                GlideApp.with(imageView).load(data).transforms(new CenterCrop()).disallowHardwareConfig().into(imageView);
            }

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "click" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return inflate;
        }
    }

    public int dp2px(float f) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
    }

    private void LOG(String data){
        Log.i("spoort_list","轮播图控件： " + data);
    }
}
