# BannerViewPager
>>>>基于viewpager封装的banner广告导航栏,可以动态配置是否轮播，轮播速度<br>
该banner当你手指触碰时可停止自动轮播事件，松开继续轮播，解决listview 或者scrollview 或者recycleview<br> 
嵌套viewpager的情况下按住viewpager上下滚动后不自动轮播问题。<br>

###1、使用方法示例<br>
 ` ``viewPager.setAdapter(pagerAdapter);
 ` ``BannerUntils.setBannerUntil(this,pagerAdapter,viewPager,res,dotlayout,true);
 
###2、网络请求数据后通知listview 、recycleview等更新数据使用BannerUntils下
   ` ``/**
   ` ``  * 通知viewpager改变
   ` `` * @param list
   ` `` */
   ` `` public static void notifyDataSetChanged(int[] list){
   ` ``     Adpater.notifyDataSetChanged();
   ` ``     addDotView(mContext,list,layout);
   ` ``    if (isAutoRun1) {
   ` ``        //准备开始轮播
    ` ``        removeHandlerMsg();
   ` ``         handler.sendEmptyMessageDelayed(HANDLER_01, MSG_DELAY);
   ` ``    }
   ` `` }
###3、onDestroy中移除handler信息
 @Override
    protected void onDestroy() {
        super.onDestroy();
        BannerUntils.removeHandlerMsg();
    }

在具体项目中根据数据结构不同可更改BannerAdapter、BannerUntils 中内容

