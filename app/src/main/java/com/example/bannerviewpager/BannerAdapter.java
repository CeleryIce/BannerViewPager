package com.example.bannerviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/14.
 */
public class BannerAdapter extends PagerAdapter {

    private Context context;
    private int[] ints;
    private BannerOnTuch bannerOnTuch;

    public void setBannerOnTuch(BannerOnTuch bannerOnTuch){
        this.bannerOnTuch = bannerOnTuch;
    }

    public BannerAdapter(Context context, int[] ints){
        this.ints = ints;
        this.context = context;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        position %= ints.length;
        if (position<0){
            position = ints.length + position;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_viewpager_banner_item,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        imageView.setImageResource(ints[position]);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        final int finalPosition = position;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点击了第 " + (finalPosition +1) + " 图",Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        bannerOnTuch.Down(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        bannerOnTuch.Up(true);
                        break;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    /**
     * 监听触摸接口
     */
    public interface BannerOnTuch{
        void Down(boolean down);
        void Up(boolean up);
    }
}
