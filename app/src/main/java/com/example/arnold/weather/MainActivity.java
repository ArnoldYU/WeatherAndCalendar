package com.example.arnold.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.arnold.weather.fragement.calendarviewall.calendarview;
import com.example.arnold.weather.fragement.weatherallview.weatherview;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addcity;
    private Button editcity;
    private ViewPager vp1;
    private ViewPager vp2;
    private DrawerLayout drawerLayout;

    private MenuItem menuItem;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_weather:
                    vp1.setVisibility(View.VISIBLE);
                    vp2.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_calendar:
                    vp1.setVisibility(View.GONE);
                    vp2.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };
    private ArrayList<Fragment> fragments1;
    private ArrayList<Fragment> fragments2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer1);

        Bundle bundle;
        fragments1 = new ArrayList<Fragment>();
        fragments2 = new ArrayList<Fragment>();

        String cityname[] = {"哈尔滨", "北京", "石家庄"};
        String cityname1 = "北京";
        String cityname2 = "哈尔滨";
        String cityname3 = "石家庄";

        for (int i = 0; i < 3; i++) {
            bundle = new Bundle();
            bundle.putString("City", cityname[i]);
            fragments1.add(new weatherview());
            fragments1.get(i).setArguments(bundle);
        }

        fragments2.add(new calendarview());

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments1);
//        //设定适配器
        vp1 = (ViewPager) findViewById(R.id.viewpager);
        vp1.setAdapter(adapter);

        vp1.setOnPageChangeListener(new PagerListener());

        adapter = new FragAdapter(getSupportFragmentManager(), fragments2);

        vp2 = (ViewPager) findViewById(R.id.viewpager1);
        vp2.setAdapter(adapter);

        vp2.setOnPageChangeListener(new PagerListener());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        final NavigationView headerView = (NavigationView) findViewById(R.id.navigation_view1);

        View myheadview = headerView.getHeaderView(0);
        addcity = (Button)myheadview.findViewById(R.id.addcity);
        editcity = (Button)myheadview.findViewById(R.id.editcity);

        addcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("添加城市");
                
                drawerLayout.closeDrawers();
            }
        });

        editcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("编辑城市");
                drawerLayout.closeDrawers();
            }
        });


        headerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //在这里处理item的点击事件
                switch (item.getItemId()) {
                    case R.id.item_city1:
                        vp1.setCurrentItem(0);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_city2:
                        vp1.setCurrentItem(1);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.item_city3:
                        vp1.setCurrentItem(2);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return true;
            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public class PagerListener implements ViewPager.OnPageChangeListener {

        /*
         * （非 Javadoc）
         *
         * @see android.support.v4.view.ViewPager.OnPageChangeListener#
         * onPageScrollStateChanged(int) 此方法是在状态改变的时候调用，其中arg0这个参数
         * 有三种状态（0，1，2）。
         *
         * arg0 == 1的时辰默示正在滑动，
         * arg0 == 2的时辰默示滑动完毕了，
         * arg0 == 0的时辰默示什么都没做。
         *
         * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0），演示如下：
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO 自动生成的方法存根
//            Log.d("lala","1");
        }

        /*
         * （非 Javadoc）
         *
         * @see
         * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
         *
         * arg0 :当前页面，及你点击滑动的页面
         * arg1 :当前页面偏移的百分比
         * arg2 :当前页面偏移的像素位置
         *
         * (int, float, int) pagerNum:第几个界面（从0开始计数） offset:偏移量，计算页面滑动的距离
         */
        @Override
        public void onPageScrolled(int pagerNum, float arg1, int offset) {

//            Log.d("lala","2");

        }

        /*
         * （非 Javadoc）
         *
         * @see
         * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
         * (int) 判断当前是哪个view
         */
        @Override
        public void onPageSelected(int position) {
            // TODO 自动生成的方法存根
            try {
//            tipsTv.setText(String.valueOf(position+1));
            } catch (Exception e) {
                Log.d("sda", "saddas");
            }

        }

    }

    //设置搜索栏中图片

    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu != null) {

            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
//                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

}
