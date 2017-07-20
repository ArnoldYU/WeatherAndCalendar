package com.example.arnold.weather.fragement.calendarviewall;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.AlertDialog.THEME_HOLO_LIGHT;


public class AddBusinessActivity extends AppCompatActivity {

    private List<Map<String, Object>> mydatalist;
    private Button yes;
    private Button no;
    private ListView listview;
    private SimpleAdapter adapter;

    private Calendar c;
    private String date;

    private boolean switchsituation;

    private String startdate;
    private String enddate;

    private String show1;
    private String show2;

    private String BusinessDate;
    private String business_name;
    private String business_location;

    private EditText getbusiness_name;
    private EditText getbusiness_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_activity);
        switchsituation = false;
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        getbusiness_name = (EditText) findViewById(R.id.getbusinessname);
        getbusiness_location = (EditText) findViewById(R.id.myeditext);


        c = Calendar.getInstance();
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String M = Integer.toString(c.get(Calendar.YEAR));
        String Y = Integer.toString(c.get(Calendar.MONTH) + 1);
        String D = Integer.toString(c.get(Calendar.DATE));

        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week < 0) {
            week = 0;
        }
        BusinessDate = M + "年" + Y + "月" + D + "日";
        date = M + "年" + Y + "月" + D + "日 " + weekDays[week];


        startdate = M + "年" + Y + "月" + D + "日    0:00";
        enddate = M + "年" + Y + "月" + D + "日   24:00";

        listview = (ListView) findViewById(R.id.add_business_listview);
        mydatalist = getData();
        //为事务listview添加内容
        adapter = new SimpleAdapter(AddBusinessActivity.this, mydatalist, R.layout.business_activity_listiview,
                new String[]{"myimage", "mytext"},
                new int[]{R.id.myimage, R.id.mytext});

        adapter.setViewBinder(new AddBusinessActivity.MyViewBinder());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LinearLayout ll = (LinearLayout) listview.getChildAt(position);
                final LinearLayout ll1 = (LinearLayout) listview.getChildAt(1);
                LinearLayout ll2 = (LinearLayout) listview.getChildAt(2);
                final Switch myswitch = (Switch) ll.findViewById(R.id.myswitch);
                final TextView mytext1 = (TextView) ll1.findViewById(R.id.startorendtime);
                final TextView mytext2 = (TextView) ll2.findViewById(R.id.startorendtime);

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
                            AddBusinessActivity.this,
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
                                    Toast.makeText(AddBusinessActivity.this, show1, 0).show();
                                    startdate = show1 + "   " + show2;
                                    mytext1.setText(startdate);
                                }
                            },
                            // 设置初始日期
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)).show();
                    new TimePickerDialog(
                            AddBusinessActivity.this,
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
                                    Toast.makeText(AddBusinessActivity.this, show2, 0).show();
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
                            AddBusinessActivity.this,
                            THEME_HOLO_LIGHT,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    monthOfYear += 1;
                                    show1 = year + "年" + monthOfYear
                                            + "月" + dayOfMonth + "日";
                                    Toast.makeText(AddBusinessActivity.this, show1, 0).show();
                                    enddate = show1 + "   " + show2;
                                    mytext2.setText(enddate);
                                }
                            },
                            // 设置初始日期
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)).show();
                    new TimePickerDialog(
                            AddBusinessActivity.this,
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
                                    Toast.makeText(AddBusinessActivity.this, show2, 0).show();
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

        //设置是否插入business
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("YESorNo", 1);
                business_name = getbusiness_name.getText().toString();
                business_location = getbusiness_location.getText().toString();
                if (business_name.length() == 0) {
                    business_name = "(无名称)";
                }
                if (business_location.length() == 0) {
                    business_location = "(无地点)";
                }
                System.out.println("'" + business_location + "'");
                OrderDao.insert(BusinessDate, business_name, startdate, enddate, business_location);
                AddBusinessActivity.this.setResult(RESULT_CANCELED, AddBusinessActivity.this.getIntent().putExtras(bundle));
                AddBusinessActivity.this.finish();
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


}
