package com.example.arnold.weather;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.arnold.weather.fragement.calendarviewall.calendarview;
import com.example.arnold.weather.fragement.weatherallview.Today24HourView;
import com.example.arnold.weather.fragement.weatherallview.weatherview;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String addselectcity;

    private PopupWindow popupWindow;
    private FragmentManager myfragmentmanager = getFragmentManager();
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

    private NavigationView headerView;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer1);

        Bundle bundle;
        fragments1 = new ArrayList<Fragment>();
        fragments2 = new ArrayList<Fragment>();


        String cityname[] = {"哈尔滨", "北京", "石家庄"};

        for (i = 0; i < 3; i++) {
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
        headerView = (NavigationView) findViewById(R.id.navigation_view1);

        View myheadview = headerView.getHeaderView(0);


        addcity = (Button) myheadview.findViewById(R.id.addcity);
        editcity = (Button) myheadview.findViewById(R.id.editcity);

        addcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i==10){//最多9个页面
                    drawerLayout.closeDrawers();
                    return;
                }
                System.out.println("添加城市");
                bottomwindow(addcity);
//                vp1.removeAllViewsInLayout();
//                Bundle bundle;
//                bundle = new Bundle();
//                bundle.putString("City", addselectcity);
//                fragments1.add(new weatherview());
//                fragments1.get(i).setArguments(bundle);
//                i++;
//                FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments1);
//                vp1.setAdapter(adapter);
//                vp1.setOnPageChangeListener(new PagerListener());
//
//                drawerLayout.closeDrawers();
//                vp1.setCurrentItem(i-1);
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

    public void bottomwindow(View view) {

        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.selectcity, null);
        popupWindow = new PopupWindow(layout,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        int[] location = {screenWidth, screenHeight};
        view.getLocationInWindow(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
//        popupWindow.setOnDismissListener(new poponDismissListener());
//        backgroundAlpha(1f);
    }

    private void setButtonListeners(LinearLayout layout) {

        Button nocity = (Button) layout.findViewById(R.id.cityno);
        final TextView city1 = (TextView) layout.findViewById(R.id.city1);
        final TextView city2 = (TextView) layout.findViewById(R.id.city2);
        final TextView city3 = (TextView) layout.findViewById(R.id.city3);

        final TextView city4 = (TextView) layout.findViewById(R.id.city4);
        final TextView city5 = (TextView) layout.findViewById(R.id.city5);
        final TextView city6 = (TextView) layout.findViewById(R.id.city6);

        final TextView city7 = (TextView) layout.findViewById(R.id.city7);
        final TextView city8 = (TextView) layout.findViewById(R.id.city8);
        final TextView city9 = (TextView) layout.findViewById(R.id.city9);

        final TextView city10 = (TextView) layout.findViewById(R.id.city10);
        final TextView city11 = (TextView) layout.findViewById(R.id.city11);
        final TextView city12 = (TextView) layout.findViewById(R.id.city12);

        final TextView city13 = (TextView) layout.findViewById(R.id.city13);
        final TextView city14 = (TextView) layout.findViewById(R.id.city14);
        final TextView city15 = (TextView) layout.findViewById(R.id.city15);

        final TextView city16 = (TextView) layout.findViewById(R.id.city16);
        final TextView city17 = (TextView) layout.findViewById(R.id.city17);
        final TextView city18 = (TextView) layout.findViewById(R.id.city18);

        nocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    //在此处添加你的按键处理 xxx
                    popupWindow.dismiss();
                }
            }
        });

        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city1.getText().toString();
                addcity ();
            }

        });
        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city2.getText().toString();
                addcity ();
            }
        });
        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city3.getText().toString();
                addcity ();
            }
        });
        city4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city4.getText().toString();
                addcity ();
            }
        });
        city5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city5.getText().toString();
                addcity ();
            }
        });
        city6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city6.getText().toString();
                addcity ();
            }
        });
        city7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city7.getText().toString();
                addcity ();
            }
        });
        city8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city8.getText().toString();
                addcity ();
            }
        });
        city9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city9.getText().toString();
                addcity ();
            }
        });
        city10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city10.getText().toString();
                addcity ();
            }
        });
        city11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city11.getText().toString();
                addcity ();
            }
        });
        city12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city12.getText().toString();
                addcity ();
            }
        });
        city13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city13.getText().toString();
                addcity ();
            }
        });
        city14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city14.getText().toString();
                addcity ();
            }
        });
        city15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city15.getText().toString();
                addcity ();
            }
        });
        city16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city16.getText().toString();
                addcity ();
            }
        });
        city17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city17.getText().toString();
                addcity ();
            }
        });
        city18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addselectcity = city18.getText().toString();
                addcity ();
            }
        });
    }
    public void addcity () {
        Today24HourView.setcitymo++;
        Today24HourView.cityname.add(addselectcity+"1");

        headerView.getMenu().add(addselectcity).setIcon(R.drawable.a100).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                popupWindow.dismiss();
                return true;
            }
        });
        popupWindow.dismiss();
        vp1.removeAllViewsInLayout();
        Bundle bundle;
        bundle = new Bundle();
        bundle.putString("City", addselectcity);
        fragments1.add(new weatherview());
        fragments1.get(i).setArguments(bundle);
        i++;
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments1);
        vp1.setAdapter(adapter);
        vp1.setOnPageChangeListener(new PagerListener());

        drawerLayout.closeDrawers();
        vp1.setCurrentItem(i-1);
    }
}
