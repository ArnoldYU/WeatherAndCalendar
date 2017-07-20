package com.example.arnold.weather.fragement.calendarviewall;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arnold.weather.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;

/**
 * Created by Arnold on 2017/7/18.
 */
public class ChangeBusiness extends AppCompatActivity {

    private List<Map<String, Object>> mydatalist;
    private int id;
    private String business_name;
    private String business_location;
    private String business_beginTime;
    private String business_endTime;
    private String startdate;
    private String enddate;
    private String date;
    private String show1;
    private String show2;
    private String BusinessDate;
    private boolean switchsituation;

    private Button yes;
    private Button no;
    private EditText change_business_name;
    private EditText change_business_location;
    private TextView change_business_beginTime;
    private TextView changebusiness_endTime;
    private ListView listview;

    private SimpleAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changebusiness);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();

        Calendar c = Calendar.getInstance();
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String M = Integer.toString(c.get(Calendar.YEAR));
        String Y = Integer.toString(c.get(Calendar.MONTH) + 1);
        String D = Integer.toString(c.get(Calendar.DATE));

        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week < 0) {
            week = 0;
        }
        date = M + "年" + Y + "月" + D + "日 " + weekDays[week];
        id = bundle.getInt("changebusiness_id");
        BusinessDate = bundle.getString("changebusiness_BusinessDate");
        business_name = bundle.getString("changebusiness_getbusinessname");
        business_location = bundle.getString("changebusiness_myeditext");
        business_beginTime = bundle.getString("changebusiness_beginTime");
        business_endTime = bundle.getString("changebusiness_endTime");

        change_business_location = (EditText) findViewById(R.id.changebusiness_myeditext);
        change_business_name = (EditText) findViewById(R.id.changebusiness_getbusinessname);
        yes = (Button) findViewById(R.id.changebusiness_yes);
        no = (Button) findViewById(R.id.changebusiness_no);

        System.out.println(change_business_name);
        //初始化设置事务事件与事务地点
        if (business_name.equals("(无名称)"))
            change_business_name.setHint(business_name);
        else
            change_business_name.setText(business_name);
        if (business_location.equals("(无地点)"))
            change_business_location.setHint(business_location);
        else
            change_business_location.setText(business_location);

        //初始化获得开始时间与结束时间
        startdate = business_beginTime;
        enddate = business_endTime;

        listview = (ListView) findViewById(R.id.changebusiness_add_business_listview);
        mydatalist = getData();

        //为事务listview添加内容
        adapter = new SimpleAdapter(ChangeBusiness.this, mydatalist, R.layout.business_activity_listiview,
                new String[]{"myimage", "mytext"},
                new int[]{R.id.myimage, R.id.mytext});

        adapter.setViewBinder(new ChangeBusiness.MyViewBinder());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LinearLayout ll = (LinearLayout) listview.getChildAt(position);
                final LinearLayout ll1 = (LinearLayout) listview.getChildAt(1);
                final LinearLayout ll2 = (LinearLayout) listview.getChildAt(2);
                final Switch myswitch = (Switch) ll.findViewById(R.id.myswitch);
                final TextView mytext1 = (TextView) ll1.findViewById(R.id.startorendtime);
                final TextView mytext2 = (TextView) ll2.findViewById(R.id.startorendtime);

                //初始化设置开始时间与结束时间
                mytext1.setText(business_beginTime);
                mytext2.setText(business_endTime);
                //初始化是否选择全天
                if (startdate.equals(enddate)) {
                    switchsituation = true;
                    myswitch.setChecked(true);
                } else {
                    switchsituation = false;
                    myswitch.setChecked(false);
                }

                myswitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (switchsituation) {
                            myswitch.setChecked(false);
                            switchsituation = false;
                            mytext1.setText(startdate);
                            mytext2.setText(enddate);
                        } else {
                            myswitch.setChecked(true);
                            switchsituation = true;
                            mytext1.setText(date);
                            mytext2.setText(date);
                        }
                    }
                });
                if (position == 0) {
                    if (!switchsituation) {
                        myswitch.setChecked(true);
                        switchsituation = true;
                        mytext1.setText(date);
                        mytext2.setText(date);
                    } else {
                        myswitch.setChecked(false);
                        switchsituation = false;
                        mytext1.setText(startdate);
                        mytext2.setText(enddate);
                    }
                } else if (position == 1) {
                    Calendar c = Calendar.getInstance();
                    // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                    new DatePickerDialog(
                            ChangeBusiness.this,
                            THEME_HOLO_LIGHT,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    monthOfYear = monthOfYear + 1;
                                    show1 = year + "年" + monthOfYear
                                            + "月" + dayOfMonth + "日";
                                    BusinessDate = show1;
                                    Toast.makeText(ChangeBusiness.this, show1, 0).show();
                                    startdate = show1 + "   " + show2;
                                    mytext1.setText(startdate);
                                }
                            },
                            // 设置初始日期
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)).show();
                    new TimePickerDialog(
                            ChangeBusiness.this,
                            THEME_HOLO_LIGHT,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    if (minute < 10) {
                                        show2 = hourOfDay + ":0" + minute;
                                    } else {
                                        show2 = hourOfDay + ":" + minute;
                                    }
                                    Toast.makeText(ChangeBusiness.this, show2, 0).show();
                                }
                            },
                            c.get(Calendar.HOUR_OF_DAY),
                            c.get(Calendar.MINUTE),
                            true).show();

                } else if (position == 2) {
                    startdate = mytext1.getText().toString();
                    Calendar c = Calendar.getInstance();
                    // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                    new DatePickerDialog(
                            ChangeBusiness.this,
                            THEME_HOLO_LIGHT,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    monthOfYear += 1;
                                    show1 = year + "年" + monthOfYear
                                            + "月" + dayOfMonth + "日";
                                    Toast.makeText(ChangeBusiness.this, show1, 0).show();
                                    enddate = show1 + "   " + show2;
                                    mytext2.setText(enddate);
                                }
                            },
                            // 设置初始日期
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)).show();
                    new TimePickerDialog(
                            ChangeBusiness.this,
                            THEME_HOLO_LIGHT,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    if (minute < 10) {
                                        show2 = hourOfDay + ":0" + minute;
                                    } else {
                                        show2 = hourOfDay + ":" + minute;
                                    }
                                    Toast.makeText(ChangeBusiness.this, show2, 0).show();
                                }
                            },
                            c.get(Calendar.HOUR_OF_DAY),
                            c.get(Calendar.MINUTE),
                            true).show();


                }

            }
        });

        listview.post(new Runnable() {
            public void run() {
                // fileList为与adapter做连结的list总数
                if (mydatalist.size() == listview.getChildCount()) {
                    //对ListView中的ChildView进行操作。。。
                    for (int i = 0; i < listview.getChildCount(); i++) {
                        LinearLayout ll = (LinearLayout) listview.getChildAt(i); //获得子级
//                        ll.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                System.out.println("dsadasdasds");
//                            }
//                        });
                        TextView mytext = (TextView) ll.findViewById(R.id.startorendtime);
                        Switch myswitch = (Switch) ll.findViewById(R.id.myswitch);
                        if (i != 0) {
                            myswitch.setVisibility(View.GONE);
                        } else {
                            mytext.setVisibility(View.GONE);
                        }
                        if (i == 1) {
                            mytext.setText(startdate);
                        } else if (i == 2) {
                            mytext.setText(enddate);
                        }


                    }

                }

            }

        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("YESorNo", 1);
                business_name = change_business_name.getText().toString();
                business_location = change_business_location.getText().toString();
                if (business_name.length() == 0) {
                    business_name = "(无名称)";
                }
                if (business_location.length() == 0) {
                    business_location = "(无地点)";
                }
                System.out.println("'" + business_location + "'");
                OrderDao.change(id, BusinessDate, business_name, startdate, enddate, business_location);
                ChangeBusiness.this.setResult(RESULT_CANCELED, ChangeBusiness.this.getIntent().putExtras(bundle));
                ChangeBusiness.this.finish();
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("YESorNo",0);
//                AddBusinessActivity.this.setResult(RESULT_CANCELED, AddBusinessActivity.this.getIntent().putExtras(bundle));
//                AddBusinessActivity.this.finish();
                finish();
            }
        });
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
                ChangeBusiness.this.finish();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map = new HashMap<String, Object>();
        map.put("myimage", R.drawable.allday);
        map.put("mytext", "全天");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("myimage", R.drawable.startorend);
        map.put("mytext", "开始");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("myimage", R.drawable.startorend);
        map.put("mytext", "结束");
        list.add(map);

        return list;
    }


}
