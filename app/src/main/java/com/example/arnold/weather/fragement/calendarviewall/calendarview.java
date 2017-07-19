package com.example.arnold.weather.fragement.calendarviewall;

/**
 * Created by Arnold on 2017/7/16.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnold.weather.R;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class calendarview extends Fragment {

    private List<Map<String, Object>> mydatalist;
    private SimpleAdapter adapter;
    private LinearLayout addbusiness;
    private LinearLayout showbusines;
    private OrderDao ordersDBHelper;
    private ListView listView;
    private String date;
    private Calendar c;
    //数据库
    private SQLiteDatabase db;

    static final int SEND_SMS_REQUEST = 0;
    static final int CALL_REQUEST = 1;

    private int Backposition;//用于辨别返回的是第几个item 的数据

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub


        ordersDBHelper = new OrderDao(this.getContext());

        Calendar c = Calendar.getInstance();
        String M = Integer.toString(c.get(Calendar.YEAR));
        String Y = Integer.toString(c.get(Calendar.MONTH) + 1);
        String D = Integer.toString(c.get(Calendar.DATE));

        date = M + "年" + Y + "月" + D + "日";

        System.out.println(date);
        mydatalist = getData(date);

        view = inflater.inflate(R.layout.calendarview, container, false);

        //获取日历控件 和 事务listview控件
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        listView = (ListView) view.findViewById(R.id.calendarViewList);

        addbusiness = (LinearLayout) view.findViewById(R.id.addfilelayout);
        showbusines = (LinearLayout) view.findViewById(R.id.showbusinesslayout);

        //添加事务按钮跳转事件
        addbusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddBusinessActivity.class);
                startActivityForResult(intent, SEND_SMS_REQUEST);
            }
        });
        //添加查看全部事务跳转事件
        showbusines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),ShowBusinessActivity.class);
                startActivityForResult(intent, SEND_SMS_REQUEST);
            }
        });

        //添加日历控件点击事件
        if (calendarView != null) {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth) {
                    // TODO Auto-generated method stub
                    month = month + 1;
                    String mydate = year + "年" + month + "月" + dayOfMonth + "日";
                    Toast.makeText(getActivity().getApplicationContext(), mydate, 0).show();

                    mydatalist = getData(mydate);
                    adapter = new SimpleAdapter(getActivity(), mydatalist, R.layout.business_view_list,
                            new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                            new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

                    adapter.setViewBinder(new calendarview.MyViewBinder());
                    listView.setAdapter(adapter);
                }
            });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "1", 0).show();
        }

        //为事务listview添加内容
        adapter = new SimpleAdapter(getActivity(), mydatalist, R.layout.business_view_list,
                new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

        adapter.setViewBinder(new calendarview.MyViewBinder());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 加入逻辑代码
                Intent intent=new Intent();
                intent.setClass(getActivity(),ChangeBusiness.class);//当前的Activity为Test,目标Activity为Name
                //从下面这行开始是将数据传给新的Activity,如果不传数据，只是简单的跳转，这几行代码请注释掉
                Bundle bundle=new Bundle();
                Backposition = position;
                bundle.putInt("changebusiness_id", (Integer) mydatalist.get(position).get("Id"));
                bundle.putString("changebusiness_BusinessDate",(String)mydatalist.get(position).get("BusinessDate"));
                bundle.putString("changebusiness_getbusinessname",(String)mydatalist.get(position).get("business_name"));//key1为名，value1为值
                bundle.putString("changebusiness_myeditext", (String) mydatalist.get(position).get("business_location"));
                bundle.putString("changebusiness_endTime", (String) mydatalist.get(position).get("business_endTime"));
                bundle.putString("changebusiness_beginTime", (String) mydatalist.get(position).get("business_beginTime"));
                intent.putExtras(bundle);
                //传数据结束
                startActivityForResult(intent, SEND_SMS_REQUEST);
            }
        });
        initViewsMethod();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_SMS_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getActivity(), "Send SMS RESULT_OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                mydatalist = getData(date);
                adapter = new SimpleAdapter(getActivity(), mydatalist, R.layout.business_view_list,
                        new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                        new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

                adapter.setViewBinder(new calendarview.MyViewBinder());
                listView.setAdapter(adapter);
            }
        } else if (requestCode == CALL_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Call RESULT_CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private List<Map<String, Object>> getData(String date1) {
        List<Map<String, Object>> list = new OrderDao(this.getContext()).returndate(date1);
        if (list == null) {
            Log.d("getData", "getData is null");
        }

        return list;
    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {
        /**
         * view：要板顶数据的视图
         * data：要绑定到视图的数据
         * textRepresentation：一个表示所支持数据的安全的字符串，结果是data.toString()或空字符串，但不能是Null
         * 返回值：如果数据绑定到视图返回真，否则返回假
         */
        @Override
        public boolean setViewValue(View view, Object data,
                                    String textRepresentation) {
            if ((view instanceof ImageView) & (data instanceof Bitmap)) {
                ImageView iv = (ImageView) view;
                Bitmap bmp = (Bitmap) data;
                iv.setImageBitmap(bmp);
                return true;
            }
            return false;
        }
    }

    private void initViewsMethod() {
        listView = (ListView) view.findViewById(R.id.calendarViewList);
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            public void onCreateContextMenu(ContextMenu menu1, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                // TODO Auto-generated method stub

                menu1.setHeaderTitle("提示：");
                menu1.setHeaderIcon(android.R.drawable.stat_notify_error);
                menu1.add(0, 0, 1, "删除");
                menu1.add(1, 1, 0, "取消");

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int myid;
        TextView mytext;
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                android.support.constraint.ConstraintLayout ll = (android.support.constraint.ConstraintLayout) listView.getChildAt(info.position);
                mytext = (TextView)ll.findViewById(R.id.myid);
                myid = Integer.parseInt(mytext.getText().toString());
                OrderDao.delete(myid);
                break;
        }
        Calendar c = Calendar.getInstance();
        String M = Integer.toString(c.get(Calendar.YEAR));
        String Y = Integer.toString(c.get(Calendar.MONTH) + 1);
        String D = Integer.toString(c.get(Calendar.DATE));

        date = M + "年" + Y + "月" + D + "日";
        mydatalist = getData(date);
        adapter = new SimpleAdapter(getActivity(), mydatalist, R.layout.business_view_list,
                new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

        adapter.setViewBinder(new calendarview.MyViewBinder());
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

}