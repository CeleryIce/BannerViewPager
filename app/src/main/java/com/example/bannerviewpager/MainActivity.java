package com.example.bannerviewpager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotlayout;
    private int[] res = {R.mipmap.defaults,R.mipmap.sidewayssky,R.mipmap.defaults,R.mipmap.sidewayssky,R.mipmap.defaults,R.mipmap.sidewayssky,R.mipmap.defaults,R.mipmap.sidewayssky,R.mipmap.defaults,R.mipmap.sidewayssky};
    private BannerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        dotlayout = (LinearLayout) findViewById(R.id.dotlayout);
        pagerAdapter = new BannerAdapter(this,res);
        viewPager.setAdapter(pagerAdapter);
        BannerUntils.setBannerUntil(this,pagerAdapter,viewPager,res,dotlayout,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BannerUntils.removeHandlerMsg();
    }
}
