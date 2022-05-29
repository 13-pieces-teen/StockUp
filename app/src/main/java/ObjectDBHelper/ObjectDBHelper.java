package ObjectDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stockup.entity.objectInfo;
import com.example.stockup.entity.wholeObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectDBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;    //构造方法
    private static final String TAG = "数据库";
    //数据库的名字
    public static final String DB_NAME = "objectManager.db";

    //数据库表名
    private static final String TABLE_WHOLE = "whole";
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_DURG = "durg";
    private static final String TABLE_SUPPLIES = "suppllies";
    private static final String TABLE_COSMETICS = "cosmetics";
    SQLiteDatabase db;

    //总表（json ,名称, 保质天数, 图片(URL)）
    private static final String CREATE_TABLE_WHOLE = "create table " + TABLE_WHOLE + " " +
            "(id integer primary key autoincrement," +
            "OB_json text, " +
            "OB_name text, " +
            "OB_guarantee_day text" +
            ", OB_URL text)";

    //食品表
    private static final String CREATE_TABLE_FOOD = "create table " + TABLE_FOOD + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            " OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ",OB_guarantee_day text" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //药品表
    private static final String CREATE_TABLE_DRUG = "create table " + TABLE_DURG + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            " OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ",OB_guarantee_day text" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //化妆品表
    private static final String CREATE_TABLE_SUPPLIES = "create table " + TABLE_SUPPLIES + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            " OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ",OB_guarantee_day text" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //物资表
    private static final String CREATE_TABLE_COSMETICS = "create table " + TABLE_COSMETICS + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            " OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ",OB_guarantee_day text" +
            ", OB_remarks text" +
            ", OB_between integer)";

    public ObjectDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public ObjectDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, VERSION);
    }



    //onCreate仅在构建数据库时调用一次，若对数据库进行修改则需要删表重试
    @Override
    public void onCreate(SQLiteDatabase db) {
        //构建数据库
        db.execSQL(CREATE_TABLE_WHOLE);
        db.execSQL(CREATE_TABLE_DRUG);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_SUPPLIES);
        db.execSQL(CREATE_TABLE_COSMETICS);
    }

    //升级方法，删除旧表增加新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WHOLE + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DURG + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSMETICS + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIES + ";");
//        onCreate(db);
    }



    //添加食物
    public long addFood(objectInfo OB)
    {
        //get一个可写的数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OB_name", OB.getOB_name());
        values.put("OB_type", OB.getOB_type());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_amount", OB.getOB_amount());
        values.put("OB_guarantee_day",OB.getOB_guarantee_day());
        values.put("OB_remarks", OB.getOB_remarks());
        values.put("OB_between", OB.getBetweenDays());

        return  db.insert(TABLE_FOOD, null, values);
    }

    //添加药品
    public long addDurg(objectInfo OB)
    {
        //get一个可写的数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OB_name", OB.getOB_name());
        values.put("OB_type", OB.getOB_type());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_guarantee_day",OB.getOB_guarantee_day());
        values.put("OB_amount", OB.getOB_amount());
        values.put("OB_remarks", OB.getOB_remarks());
        values.put("OB_between", OB.getBetweenDays());

        return  db.insert(TABLE_DURG, null, values);
    }

    //添加化妆品
    public long addCosmetics(objectInfo OB)
    {
        //get一个可写的数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OB_name", OB.getOB_name());
        values.put("OB_type", OB.getOB_type());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_guarantee_day",OB.getOB_guarantee_day());
        values.put("OB_amount", OB.getOB_amount());
        values.put("OB_remarks", OB.getOB_remarks());
        values.put("OB_between", OB.getBetweenDays());

        return  db.insert(TABLE_COSMETICS, null, values);
    }

    //添加物资
    public long addSupplies(objectInfo OB)
    {
        //get一个可写的数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("OB_name", OB.getOB_name());
        values.put("OB_type", OB.getOB_type());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_amount", OB.getOB_amount());
        values.put("OB_guarantee_day",OB.getOB_guarantee_day());
        values.put("OB_remarks", OB.getOB_remarks());
        values.put("OB_between", OB.getBetweenDays());

        return  db.insert(TABLE_SUPPLIES, null, values);
    }



    //获取listview所需信息(food)
    public List<objectInfo> getFoodInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_FOOD, new String[]{"OB_name","OB_type","OB_produce_date",
                "OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(0));
            obj1.setOB_type(cursor.getString(1));
            obj1.setOB_produce_date(cursor.getString(2));
            obj1.setOB_after_date(cursor.getString(3));
            obj1.setOB_open_date(cursor.getString(4));
            obj1.setOB_amount(cursor.getInt(5));
            obj1.setOB_guarantee_day(cursor.getString(6));
            obj1.setOB_remarks(cursor.getString(7));
            obj1.setBetweenDays(cursor.getInt(8));
            infos.add(obj1);
        }
        System.out.println("ObjectDBHelper.getAllUser.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(durg)
    public List<objectInfo> getDurgInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_DURG, new String[]{"OB_name","OB_type","OB_produce_date",
                "OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(0));
            obj1.setOB_type(cursor.getString(1));
            obj1.setOB_produce_date(cursor.getString(2));
            obj1.setOB_after_date(cursor.getString(3));
            obj1.setOB_open_date(cursor.getString(4));
            obj1.setOB_amount(cursor.getInt(5));
            obj1.setOB_guarantee_day(cursor.getString(6));
            obj1.setOB_remarks(cursor.getString(7));
            obj1.setBetweenDays(cursor.getInt(8));
            infos.add(obj1);
        }
        System.out.println("ObjectDBHelper.getAllUser.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(cosmetics)
    public List<objectInfo> getCosmeticsInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_COSMETICS, new String[]{"OB_name","OB_type","OB_produce_date",
                "OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(0));
            obj1.setOB_type(cursor.getString(1));
            obj1.setOB_produce_date(cursor.getString(2));
            obj1.setOB_after_date(cursor.getString(3));
            obj1.setOB_open_date(cursor.getString(4));
            obj1.setOB_amount(cursor.getInt(5));
            obj1.setOB_guarantee_day(cursor.getString(6));
            obj1.setOB_remarks(cursor.getString(7));
            obj1.setBetweenDays(cursor.getInt(8));
            infos.add(obj1);
        }
        System.out.println("ObjectDBHelper.getAllUser.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(Supplies)
    public List<objectInfo> getSuppliesInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_SUPPLIES, new String[]{"OB_name","OB_type","OB_produce_date",
                "OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(0));
            obj1.setOB_type(cursor.getString(1));
            obj1.setOB_produce_date(cursor.getString(2));
            obj1.setOB_after_date(cursor.getString(3));
            obj1.setOB_open_date(cursor.getString(4));
            obj1.setOB_amount(cursor.getInt(5));
            obj1.setOB_guarantee_day(cursor.getString(6));
            obj1.setOB_remarks(cursor.getString(7));
            obj1.setBetweenDays(cursor.getInt(8));
            infos.add(obj1);
        }
        System.out.println("ObjectDBHelper.getAllUser.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(总表)
    public List<wholeObject> getWholeInfo() {
        List<wholeObject> infos = new ArrayList<>();
        wholeObject who1 = null;
        Cursor cursor = db.query(TABLE_WHOLE, new String[]{"OB_ID","OB_json","OB_name","OB_guarantee_day",
                "OB_url"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            who1 = new wholeObject();
            who1.setOB_json(cursor.getString(0));
            who1.setOB_name(cursor.getString(1));
            who1.setOB_guarantee_day(cursor.getString(2));
            who1.setOB_uri(cursor.getString(3));
            infos.add(who1);
        }
        System.out.println("ObjectDBHelper.getAllUser.size->>>" + infos.size());
        return infos;
    }




    //通过物品名称在总表查询信息
    public wholeObject searchObject(String username) {
        Cursor cursor = db.query(TABLE_WHOLE, new String[]{"OB_json","OB_name","OB_guarantee_day",
                "OB_url"}, "OB_name" + "=?", new String[]{username}, null, null, null);
        wholeObject who1 = new wholeObject();
        if (cursor.moveToFirst()) {
            who1.setID(cursor.getInt(0));
            who1.setOB_json(cursor.getString(1));
            who1.setOB_name(cursor.getString(2));
            who1.setOB_guarantee_day(cursor.getString(3));
            who1.setOB_uri(cursor.getString(4));
        }
        return who1;
    }


    //删除某物品
    public void deleteObject(String name) {
        db.delete(TABLE_FOOD, name + "=?", new String[]{String.valueOf("OB_NAME")});
        System.out.println("删除成功！！！");
    }

}
