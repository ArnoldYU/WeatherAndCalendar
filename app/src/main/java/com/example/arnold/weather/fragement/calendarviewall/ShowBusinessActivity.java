package com.example.arnold.weather.fragement.calendarviewall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnold.weather.R;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.example.arnold.weather.fragement.calendarviewall.calendarview.CALL_REQUEST;
import static com.example.arnold.weather.fragement.calendarviewall.calendarview.SEND_SMS_REQUEST;

/**
 * Created by Arnold on 2017/7/18.
 */

public class ShowBusinessActivity extends AppCompatActivity {

    private ListView showbusinesslistview;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> mydatalist;
    private int Backposition;//用于辨别返回的是第几个item 的数据
    private Button searchbutton;
    private EditText searchedittext;

    private String searchname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_show);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        showbusinesslistview = (ListView) findViewById(R.id.showbusinesslistview);
        mydatalist = getData();

        searchbutton = (Button) findViewById(R.id.searchbutton);
        searchedittext = (EditText) findViewById(R.id.searchEdittext);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchname = searchedittext.getText().toString();
                if (searchname.length() == 0) {
                    searchname = "(无名称)";
                }
                mydatalist = new OrderDao(ShowBusinessActivity.this).search(searchname);
                adapter = new SimpleAdapter(ShowBusinessActivity.this, mydatalist, R.layout.business_view_list,
                        new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                        new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

                adapter.setViewBinder(new ShowBusinessActivity.MyViewBinder());
                showbusinesslistview.setAdapter(adapter);
            }

        });

        adapter = new SimpleAdapter(ShowBusinessActivity.this, mydatalist, R.layout.business_view_list,
                new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

        adapter.setViewBinder(new ShowBusinessActivity.MyViewBinder());
        showbusinesslistview.setAdapter(adapter);
        showbusinesslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                intent.setClass(ShowBusinessActivity.this, ChangeBusiness.class);//当前的Activity为Test,目标Activity为Name
                //从下面这行开始是将数据传给新的Activity,如果不传数据，只是简单的跳转，这几行代码请注释掉
                Bundle bundle = new Bundle();
                Backposition = position;
                bundle.putInt("changebusiness_id", (Integer) mydatalist.get(position).get("Id"));
                bundle.putString("changebusiness_BusinessDate", (String) mydatalist.get(position).get("BusinessDate"));
                bundle.putString("changebusiness_getbusinessname", (String) mydatalist.get(position).get("business_name"));//key1为名，value1为值
                bundle.putString("changebusiness_myeditext", (String) mydatalist.get(position).get("business_location"));
                bundle.putString("changebusiness_endTime", (String) mydatalist.get(position).get("business_endTime"));
                bundle.putString("changebusiness_beginTime", (String) mydatalist.get(position).get("business_beginTime"));
                intent.putExtras(bundle);
                //传数据结束
                startActivityForResult(intent, SEND_SMS_REQUEST);
            }
        });

        initViewsMethod();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_SMS_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(ShowBusinessActivity.this, "Send SMS RESULT_OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                mydatalist = getData();
                adapter = new SimpleAdapter(ShowBusinessActivity.this, mydatalist, R.layout.business_view_list,
                        new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                        new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

                adapter.setViewBinder(new ShowBusinessActivity.MyViewBinder());
                showbusinesslistview.setAdapter(adapter);
            }
        } else if (requestCode == CALL_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(ShowBusinessActivity.this, "Call RESULT_CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new OrderDao(ShowBusinessActivity.this).returnalldate();
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

    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.addcontactsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
//            case R.id.showinfo:
//                Toast.makeText(this, "展示信息", Toast.LENGTH_SHORT).show();
//                break;
            case android.R.id.home:
                Log.d("llaal", "finished");
                ShowBusinessActivity.this.finish();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewsMethod() {
        showbusinesslistview = (ListView) findViewById(R.id.showbusinesslistview);
        showbusinesslistview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

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
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                android.support.constraint.ConstraintLayout ll = (android.support.constraint.ConstraintLayout) showbusinesslistview.getChildAt(info.position);
                mytext = (TextView)ll.findViewById(R.id.myid);
                myid = Integer.parseInt(mytext.getText().toString());
                OrderDao.delete(myid);
                break;
        }
        mydatalist = getData();
        adapter = new SimpleAdapter(ShowBusinessActivity.this, mydatalist, R.layout.business_view_list,
                new String[]{"Id", "business_name", "business_beginTime", "business_endTime", "business_location"},
                new int[]{R.id.myid, R.id.business_name, R.id.business_beginTime, R.id.business_endTime, R.id.business_location});

        adapter.setViewBinder(new ShowBusinessActivity.MyViewBinder());
        showbusinesslistview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

}