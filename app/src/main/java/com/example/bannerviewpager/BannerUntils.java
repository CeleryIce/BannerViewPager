package com.example.bannerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class BannerUntils {

    private static final int HANDLER_01 = 0x001;
    private static final int MSG_DELAY = 5000;//自动轮播时间
    private static int currentItem = 0;
    private static List<View> viewList = new ArrayList<>();
    private static ViewPager viewPager;
    private static BannerAdapter Adpater;
    private static boolean isAutoRun1 = false;//是否自动轮播
    private static Context mContext;
    private static LinearLayout layout;

    /**
     *  pagerAdapter = new BannerAdapter(this,res);
     *  viewPager.setAdapter(pagerAdapter);
     *  后设置轮播相关数据和方法
     * @param context      上下文对象
     * @param pagerAdapter  适配器
     * @param viewpager
     * @param ints   显示的图片资源地址集合{R.mipmap.defaults,R.mipmap.sidewayssky}
     * @param dotlayout 添加dot的父布局（必须为LinearLayout）
     * @param isAutoRun  是否自动轮播
     */
    public static void setBannerUntil(Context context, BannerAdapter pagerAdapter, ViewPager viewpager, int[] ints, LinearLayout dotlayout, boolean isAutoRun){
        viewPager = viewpager;
        isAutoRun1 = isAutoRun;
        Adpater = pagerAdapter;
        mContext = context;
        layout = dotlayout;
        addDotView(context,ints,dotlayout);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        currentItem = 0;
        viewPager.setCurrentItem(0);
        if (isAutoRun1) {
            //准备开始轮播
            handler.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
        }
        //触摸监听
       onTuchListener(pagerAdapter,viewPager);
    }


    /**
     * 动态添加控件
     * @param context
     * @param ints
     * @param dotlayout
     */
    private static void addDotView(Context context, int[] ints,LinearLayout dotlayout){
        dotlayout.removeAllViews();
        viewList.clear();
        for (int i=0;i<ints.length;i++) {
            View view = new View(context);
            view.setBackgroundResource(R.drawable.dotview_nor);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip2px(context,8), dip2px(context,8));
            if (i>0){
                layoutParams.setMargins(dip2px(context,5),0,0,0);
            }
            view.setLayoutParams(layoutParams);
            viewList.add(view);
            dotlayout.addView(view);
        }
        if (viewList.size()>0) {
            int current = 0;
            try {
                current = currentItem % viewList.size();
            }catch (Exception e){
                e.printStackTrace();
            }
            viewList.get(current).setBackgroundResource(R.drawable.dotview_nel);
        }
    }


    /**
     * 触摸监听，触摸时停止自动轮播
     * @param pagerAdapter
     * @param viewPager
     */
    private static void onTuchListener(BannerAdapter pagerAdapter, ViewPager viewPager){
        //如果嵌套在listview 或者 scrollview中要添加一个相应的setOnTouchListener事件监听
        //防止触控viewpager时上下滑动listview或者srollview ACTION_UP动作viewPager识别不到

        pagerAdapter.setBannerOnTuch(new BannerAdapter.BannerOnTuch() {
            @Override
            public void Down(boolean down) {
                handler.removeMessages(HANDLER_01);
            }

            @Override
            public void Up(boolean up) {
                if (isAutoRun1) {
                    handler.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
                }
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeMessages(HANDLER_01);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (isAutoRun1) {
                            handler.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     * handler
     */
    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isAutoRun1) {
                switch (msg.what) {
                    case HANDLER_01:
                        currentItem++;
                        viewPager.setCurrentItem(currentItem);
                        //准备下次播放
                        this.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
                        break;
                }
            }
        }
    };

    /**
     * viewpager 切换监听
     */
    private static ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
//            Log.e("onPageSelected","position:" + position);
            currentItem = position;
            for (int i=0;i<viewList.size();i++){
                if (i== (position %= viewList.size())) {
                    viewList.get(i).setBackgroundResource(R.drawable.dotview_nel);
                }else {
                    viewList.get(i).setBackgroundResource(R.drawable.dotview_nor);
                }
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    /**
     * 通知viewpager改变
     * @param list
     */
    public static void notifyDataSetChanged(int[] list){
        Adpater.notifyDataSetChanged();
        addDotView(mContext,list,layout);
        if (isAutoRun1) {
            //准备开始轮播
            removeHandlerMsg();
            handler.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
        }
    }


    /**
     * 移除handler消息处理
     */
    public static void removeHandlerMsg(){
        handler.removeMessages(HANDLER_01);
    }

    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
