package com.pngfi.bdemo.banner;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pngfi.bdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DiscoverSlideShowView extends RelativeLayout {
	private Context mContext;
	// 自定义轮播图的资源
	private List<String> imageValues;
	// 放轮播图片的ImageView 的list
//	private List<View> imageViewsList;
	

	private BannerViewPager mViewPager;
	private LinearLayout slideview_dot;
	/** 占位条 */
	private ImageView slideview_iv_ph;
	private ScorllListener mScorllListener;
	
	Timer mTimer;
	TimerTask mTask;
	// 当前轮播页
	private int currentItems = 0;
	// 定时任务
//	public static ScheduledExecutorService scheduledExecutorService;
	// 自动轮播启用开关
	private static boolean isAutoPlay = true;


	public DiscoverSlideShowView(Context context) {
		this(context, null);
	}

	public DiscoverSlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public void scrollListenter(ScorllListener listenter){
		this.mScorllListener = listenter;
	}
	
	public DiscoverSlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		imageValues = new ArrayList<String>();

	}
	
	public void setImageValues(List<String> imageUrls){
		if(imageValues!= null){
			imageValues.clear();
		}
		this.imageValues = imageUrls;
		initData();
	}

	/**
	 * 开启定时任务
	 * @param state 0,第一次不延时
	 */
	public void startTask(int state) {
		// TODO Auto-generated method stub
		if(imageValues == null || imageValues.size()<=0) return;
		isAutoPlay = true;
		if(mTimer!=null){
			mTimer.cancel();
			mTask.cancel();
			System.gc();
		}
		mTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
//				LOG("执行定时切换");
				currentItems ++;
				mHandler.sendEmptyMessage(0);
			}
		};
		mTimer.schedule(mTask,state * 5 * 1000, 5 * 1000);// 这里设置自动切换的时间，单位是毫秒，2*1000表示2秒
	}

	/**
	 * 停止定时任务
	 */
	public void stopTask() {
		// TODO Auto-generated method stub
		try {
			isAutoPlay = false;
			if(mTimer!=null){
				mTimer.cancel();
				mTask.cancel();
				System.gc();
			}
		LOG("广告轮播控件 关闭定时任务");
		} catch (Exception e) {
			
		}
	}

	public void delete(){
		if(imageValues != null){
			imageValues.clear();
			imageValues = null;
		}
	}

	// 处理EmptyMessage(0)
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				setCurrentItem();
			}
		};

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		//获取资源
		initUI(mContext);
//		startTask(0);
	}

//	private MyPagerAdapter mAdapter;
	private BannerPageAdapter mAdapter;

	/**
	 * 初始化Views等UI
	 */
	private void initUI(Context context) {
		if (imageValues == null || imageValues.size() == 0)
			return;

		if (mAdapter ==null){
			mAdapter = new BannerPageAdapter<String>();
			mAdapter.setData(imageValues);
		}

		View viwPagerLayout = LayoutInflater.from(context).inflate(R.layout.layout_discover_first_slideshow, this, true);

		slideview_dot = viwPagerLayout.findViewById(R.id.slideview_dot);
		slideview_iv_ph = viwPagerLayout.findViewById(R.id.slideview_iv_ph);
		
//		initDot(slideview_dot,imageValues.size()-2,0);
		initDot(slideview_dot,imageValues.size(),0);
		
		mViewPager = viwPagerLayout.findViewById(R.id.viewPager);
		mViewPager.setFocusable(true);
		mViewPager.setBannerAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
	}

	/**
	 * 设置选中的点的位置
	 * @param size : 总共的点,列表两边各加了一个缓冲区，所以总数要-2
	 * @param position : 选中的位置，下标0开始计算，viewpager是从1开始的，所以要-1
	 *  */
	private void initDot(LinearLayout ll_dot , int size, int position){
//		if(ll_dot != null)ll_dot.removeAllViews();
			selectPostion = 0;
			for (int i = 0; i < size; i++) {
				if (i == position) {
					ImageView imageview = new ImageView(mContext);

					LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(dp2px(15), dp2px(15));
					ll.setMargins(dp2px(30), 0, 0, 0);
					imageview.setLayoutParams(ll);
					imageview.setImageResource(R.mipmap.iv_slideview_dot_sel);
					ll_dot.addView(imageview);
				} else {
					ImageView imageview = new ImageView(mContext);
					LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(dp2px(15), dp2px(15));
					ll.setMargins(dp2px(30), 0, 0, 0);
					imageview.setLayoutParams(ll);
					imageview.setImageResource(R.mipmap.iv_slideview_dot_unsel);
					ll_dot.addView(imageview);
				}
			}
	}

	private int selectPostion;

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		//0,什么都没做。1正在滑动。2滑动完毕
		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING) {	//空闲或者滑动中
				if (mViewPager.getCurrentItem() == mAdapter.getCount() - 1) {
					mViewPager.setCurrentItem(1,false);
				} else if (mViewPager.getCurrentItem() == 0) {
					mViewPager.setCurrentItem(mAdapter.getCount()-2,false);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {


		}

		@Override
		public void onPageSelected(int pos) {

			int realPosition = mAdapter.toRealPosition(pos);

			int lastPosition = selectPostion;
//			selectPostion = mViewPager.getCurrentItem()-1;
//			selectPostion = realPosition;
			selectPostion = pos;


//			if(selectPostion == -1){selectPostion = imageValues.size()-1;}
//			if(selectPostion == imageValues.size()){selectPostion = 0;}

//			LOG("mViewPager.getCurrentItem()   " + mViewPager.getCurrentItem());
			LOG("selectPostion   " + selectPostion);
//			LOG("lastPosition   " + lastPosition);

//			ImageView imageview = (ImageView) slideview_dot.getChildAt(selectPostion);
//			imageview.setImageResource(R.mipmap.iv_slideview_dot_sel);
//
//			ImageView imageviewUnSel = (ImageView) slideview_dot.getChildAt(lastPosition);
//			imageviewUnSel.setImageResource(R.mipmap.iv_slideview_dot_unsel);
		}
	}

	/**
	 * 处理Page的切换逻辑
	 */
	private void setCurrentItem() {
		if (currentItems < 1) {
			currentItems = (imageValues.size() - 2);
		} else if (currentItems > (imageValues.size() - 2)) {
			currentItems = 1;
		}

		initDot(slideview_dot, imageValues.size() - 2, currentItems - 1);

		if (currentItems == (imageValues.size() - 2) || currentItems == 1) {
			mViewPager.setCurrentItem(currentItems, false);// 取消切换动画
		} else mViewPager.setCurrentItem(currentItems, true);
	}

	/**
	 * 滑动  回调
	 */
	public interface ScorllListener{
		public void setOnClick(int position);
	}

	private void LOG(String data){
		Log.i("spoort_list","轮播图控件： " + data);
	}

	public int dp2px(float f) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, getResources().getDisplayMetrics());
	}
}
