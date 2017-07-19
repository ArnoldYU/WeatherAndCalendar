package com.example.arnold.weather.fragement.calendarviewall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Arnold on 2017/7/17.
 */

public class OrderDao {
    private Context context;
    private static OrderDBHelper ordersDBHelper;
    private static SQLiteDatabase db;

    public OrderDao(Context context) {
        this.context = context;
        ordersDBHelper = new OrderDBHelper(context);
    }

    public void test() {
        db = ordersDBHelper.getWritableDatabase();
        db.beginTransaction();

        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text1', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text2', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text3', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text4', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text5', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text6', '11:00', '12:00', 'location1')");
        db.execSQL("insert into " + OrderDBHelper.TABLE_NAME +
                " (BusinessDate, business_name, business_beginTime, business_endTime, business_location) values ( '2017-7-17', 'text7', '11:00', '12:00', 'location1')");
        db.setTransactionSuccessful();
    }

    public List<Map<String, Object>> returnalldate() {
        db = ordersDBHelper.getReadableDatabase();


        Cursor cur = db.rawQuery("SELECT * FROM Items", null);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String BusinessDate;
        String business_name;
        String business_begintime;
        String business_endtime;
        String business_location;
        int id;
        Map<String, Object> map = new HashMap<String, Object>();

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                id = cur.getInt(0);
                BusinessDate = cur.getString(1);
                business_name = cur.getString(2);
                business_begintime = cur.getString(3);
                business_endtime = cur.getString(4);
                business_location = cur.getString(5);
                map = new HashMap<String, Object>();
                map.put("Id", id);
                map.put("BusinessDate", BusinessDate);
                map.put("business_name", business_name);
                map.put("business_beginTime", business_begintime);
                map.put("business_endTime", business_endtime);
                map.put("business_location", business_location);
                list.add(map);
            }
        }

        return list;
    }

    //用于返回当前日期下所有的事物
    public List<Map<String, Object>> returndate(String date) {
//        test();
        db = ordersDBHelper.getReadableDatabase();
        
        Cursor cur = db.rawQuery("SELECT * FROM Items", null);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String BusinessDate;
        String business_name;
        String business_begintime;
        String business_endtime;
        String business_location;
        int id;

//        BusinessDate = date;

        Map<String, Object> map = new HashMap<String, Object>();

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                BusinessDate = cur.getString(1);
                if (BusinessDate != null && BusinessDate.equals(date)) {
                    id = cur.getInt(0);
                    business_name = cur.getString(2);
                    business_begintime = cur.getString(3);
                    business_endtime = cur.getString(4);
                    business_location = cur.getString(5);
                    map = new HashMap<String, Object>();
                    map.put("Id", id);
                    map.put("BusinessDate", BusinessDate);
                    map.put("business_name", business_name);
                    map.put("business_beginTime", business_begintime);
                    map.put("business_endTime", business_endtime);
                    map.put("business_location", business_location);
                    list.add(map);
                }

            }
        }

        return list;
    }

    //用于事务的添加
    public static void insert(String BusinessDate, String business_name, String business_beginTime, String business_endTime, String business_location) {

        db = ordersDBHelper.getWritableDatabase();
//        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BusinessDate", BusinessDate);
        contentValues.put("business_name", business_name);
        contentValues.put("business_beginTime", business_beginTime);
        contentValues.put("business_endTime", business_endTime);
        contentValues.put("business_location", business_location);

        db.insert(OrderDBHelper.TABLE_NAME, null, contentValues);

    }

    //用于事物的修改
    public static void change(int id, String BusinessDate, String business_name, String business_beginTime, String business_endTime, String business_location) {
        db = ordersDBHelper.getWritableDatabase();
        System.out.println(id);
        ContentValues cv = new ContentValues();
        cv.put("BusinessDate", BusinessDate);
        cv.put("business_name", business_name);
        cv.put("business_beginTime", business_beginTime);
        cv.put("business_endTime", business_endTime);
        cv.put("business_location", business_location);
        db.update(OrderDBHelper.TABLE_NAME, cv, "Id = ?", new String[]{String.valueOf(id)});
    }

    //用于事物的搜索
    public static List<Map<String, Object>> search(String name) {
        db = ordersDBHelper.getReadableDatabase();


        Cursor cur = db.rawQuery("SELECT * FROM Items", null);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String BusinessDate;
        String business_name;
        String business_begintime;
        String business_endtime;
        String business_location;
        int id;

        Map<String, Object> map = new HashMap<String, Object>();
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                business_name = cur.getString(2);
                if (business_name != null && business_name.equals(name)) {
                    id = cur.getInt(0);
                    BusinessDate = cur.getString(1);
                    business_begintime = cur.getString(3);
                    business_endtime = cur.getString(4);
                    business_location = cur.getString(5);
                    map = new HashMap<String, Object>();
                    map.put("Id", id);
                    map.put("BusinessDate", BusinessDate);
                    map.put("business_name", business_name);
                    map.put("business_beginTime", business_begintime);
                    map.put("business_endTime", business_endtime);
                    map.put("business_location", business_location);
                    list.add(map);
                }
            }
        }
        return list;
    }

    //用于事物的删除
    public static void delete(int id) {
        SQLiteDatabase db1 = ordersDBHelper.getWritableDatabase();
//        db1.beginTransaction();
        db1.delete(OrderDBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(id)});
        System.out.println("delete:"+id);
//        db1.setTransactionSuccessful();
    }
}
