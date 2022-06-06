package ObjectDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stockup.Adapter.ListViewAdapter;
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
            ", OB_between integer" +
            ", OB_URL text" +
            ", OB_remindDay int)";

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
            ", OB_between integer" +
            ", OB_URL text" +
            ", OB_remindDay integer)";

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
            ", OB_between integer" +
            ", OB_URL text" +
            ", OB_remindDay integer)";

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
            ", OB_between integer" +
            ", OB_URL text" +
            ", OB_remindDay integer)";

    public ObjectDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }


    public ObjectDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, VERSION);
        db = this.getWritableDatabase();
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
        values.put("OB_URL",OB.getImageURL());
        values.put("OB_remindDay",OB.getRemindDay());

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
        values.put("OB_URL",OB.getImageURL());
        values.put("OB_remindDay",OB.getRemindDay());

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
        values.put("OB_URL",OB.getImageURL());
        values.put("OB_remindDay",OB.getRemindDay());

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
        values.put("OB_URL",OB.getImageURL());
        values.put("OB_remindDay",OB.getRemindDay());

        return  db.insert(TABLE_SUPPLIES, null, values);
    }




    //获取listview所需信息(food)
    public List<objectInfo> getFoodInfo() {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        SQLiteDatabase db = getWritableDatabase();
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_FOOD, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllFood.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(durg)
    public List<objectInfo> getDurgInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_DURG, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllDurg.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(cosmetics)
    public List<objectInfo> getCosmeticsInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_COSMETICS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllCosmetics.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(Supplies)
    public List<objectInfo> getSuppliesInfo() {
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        Cursor cursor = db.query(TABLE_SUPPLIES, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllSupplies.size->>>" + infos.size());
        return infos;
    }

    //获取listview所需信息(总表)
    public List<wholeObject> getWholeInfo() {
        List<wholeObject> infos = new ArrayList<>();
        wholeObject who1 = null;
        Cursor cursor = db.query(TABLE_WHOLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            who1 = new wholeObject();
            who1.setOB_json(cursor.getString(1));
            who1.setOB_name(cursor.getString(2));
            who1.setOB_guarantee_day(cursor.getString(3));
            who1.setOB_uri(cursor.getString(4));
            infos.add(who1);
        }
        System.out.println("ObjectDBHelper.getAllWhole.size->>>" + infos.size());
        return infos;
    }

    //通过过期日期查询返回list(food)
    public List<objectInfo> getFoodInfo_after(String after_date) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        SQLiteDatabase db = getWritableDatabase();
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_FOOD, null,"OB_after_date=?", new String[]{after_date}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllFood.size->>>" + infos.size());
        return infos;
    }
    //通过过期日期查询返回list(drup)
    public List<objectInfo> getDrupInfo_after(String after_date) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        SQLiteDatabase db = getWritableDatabase();
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        //Cursor cursor = db.query(TABLE_DURG, null,"OB_after_date=?", new String[]{after_date}, null, null, null);
        Cursor cursor = db.query(TABLE_DURG, null, "OB_after_date" + "=?", new String[]{after_date}, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllFood.size->>>" + infos.size());
        return infos;
    }

    //通过过期日期查询返回list(Cosmetics)
    public List<objectInfo> getCosmeticsInfo_after(String after_date) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        SQLiteDatabase db = getWritableDatabase();
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_COSMETICS, null,"OB_after_date=?", new String[]{after_date}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllFood.size->>>" + infos.size());
        return infos;
    }

    //通过过期日期查询返回list(Supplies)
    public List<objectInfo> getSuppliesInfo_after(String after_date) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        SQLiteDatabase db = getWritableDatabase();
        List<objectInfo> infos = new ArrayList<>();
        objectInfo obj1 = null;
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_SUPPLIES, null,"OB_after_date=?", new String[]{after_date}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                obj1 = new objectInfo();
                obj1.setOB_name(cursor.getString(1));
                obj1.setOB_type(cursor.getString(2));
                obj1.setOB_produce_date(cursor.getString(3));
                obj1.setOB_after_date(cursor.getString(4));
                obj1.setOB_open_date(cursor.getString(5));
                obj1.setOB_amount(cursor.getInt(6));
                obj1.setOB_guarantee_day(cursor.getString(7));
                obj1.setOB_remarks(cursor.getString(8));
                obj1.setBetweenDays(cursor.getInt(9));
                obj1.setImageURL(cursor.getString(10));
                obj1.setRemindDay(cursor.getInt(11));
                infos.add(obj1);
            }
            cursor.close();
        }
        System.out.println("ObjectDBHelper.getAllFood.size->>>" + infos.size());
        return infos;
    }



    //通过物品名称在总表查询图片uri
    public String searchObject(String name) {
        Cursor cursor = db.query(TABLE_WHOLE, null, "OB_name" + "=?", new String[]{name}, null, null, null);
        wholeObject who1 = new wholeObject();
        if (cursor.moveToFirst()) {
            who1.setOB_json(cursor.getString(1));
            who1.setOB_name(cursor.getString(2));
            who1.setOB_guarantee_day(cursor.getString(3));
            who1.setOB_uri(cursor.getString(4));
        }
        return who1.getOB_uri();
    }


    //通过ID查询食物表
    public objectInfo getFoodFromID(int _id) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        objectInfo obj1 = new objectInfo();
        String s_id = String.valueOf(_id);
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_FOOD, null, "id" + "=?", new String[]{s_id}, null, null, null, null);
        if (cursor.moveToFirst()){
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(1));
            obj1.setOB_type(cursor.getString(2));
            obj1.setOB_produce_date(cursor.getString(3));
            obj1.setOB_after_date(cursor.getString(4));
            obj1.setOB_open_date(cursor.getString(5));
            obj1.setOB_amount(cursor.getInt(6));
            obj1.setOB_guarantee_day(cursor.getString(7));
            obj1.setOB_remarks(cursor.getString(8));
            obj1.setBetweenDays(cursor.getInt(9));
            obj1.setImageURL(cursor.getString(10));
            obj1.setRemindDay(cursor.getInt(11));
        }
        return obj1;
    }

    //通过ID查询药品表
    public objectInfo getDurgFromID(int _id) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        objectInfo obj1 = new objectInfo();
        String s_id = String.valueOf(_id);
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_DURG, null, "id" + "=?", new String[]{s_id}, null, null, null, null);
        if (cursor.moveToFirst()){
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(1));
            obj1.setOB_type(cursor.getString(2));
            obj1.setOB_produce_date(cursor.getString(3));
            obj1.setOB_after_date(cursor.getString(4));
            obj1.setOB_open_date(cursor.getString(5));
            obj1.setOB_amount(cursor.getInt(6));
            obj1.setOB_guarantee_day(cursor.getString(7));
            obj1.setOB_remarks(cursor.getString(8));
            obj1.setBetweenDays(cursor.getInt(9));
            obj1.setImageURL(cursor.getString(10));
            obj1.setRemindDay(cursor.getInt(11));
        }
        return obj1;
    }

    //通过ID查询化妆品表
    public objectInfo getCosmFromID(int _id) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        objectInfo obj1 = new objectInfo();
        String s_id = String.valueOf(_id);
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_COSMETICS, null, "id" + "=?", new String[]{s_id}, null, null, null, null);
        if (cursor.moveToFirst()){
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(1));
            obj1.setOB_type(cursor.getString(2));
            obj1.setOB_produce_date(cursor.getString(3));
            obj1.setOB_after_date(cursor.getString(4));
            obj1.setOB_open_date(cursor.getString(5));
            obj1.setOB_amount(cursor.getInt(6));
            obj1.setOB_guarantee_day(cursor.getString(7));
            obj1.setOB_remarks(cursor.getString(8));
            obj1.setBetweenDays(cursor.getInt(9));
            obj1.setImageURL(cursor.getString(10));
            obj1.setRemindDay(cursor.getInt(11));
        }
        return obj1;
    }

    //通过ID查询物资表
    public objectInfo getSuppliesFromID(int _id) {
        //"OB_name","OB_type","OB_produce_date","OB_after_date","OB_open_date","OB_amount","OB_guarantee_day","OB_remarks","OB_between"
        objectInfo obj1 = new objectInfo();
        String s_id = String.valueOf(_id);
        //select null1 from tableName where null2=null3 group by null4 having null5 order by null6
        Cursor cursor = db.query(TABLE_SUPPLIES, null, "id" + "=?", new String[]{s_id}, null, null, null, null);
        if (cursor.moveToFirst()){
            obj1 = new objectInfo();
            obj1.setOB_name(cursor.getString(1));
            obj1.setOB_type(cursor.getString(2));
            obj1.setOB_produce_date(cursor.getString(3));
            obj1.setOB_after_date(cursor.getString(4));
            obj1.setOB_open_date(cursor.getString(5));
            obj1.setOB_amount(cursor.getInt(6));
            obj1.setOB_guarantee_day(cursor.getString(7));
            obj1.setOB_remarks(cursor.getString(8));
            obj1.setBetweenDays(cursor.getInt(9));
            obj1.setImageURL(cursor.getString(10));
            obj1.setRemindDay(cursor.getInt(11));
        }
        return obj1;
    }



    //删除通过物品名称
    public void deleteFoodWithName(String name) {
        db.delete(TABLE_FOOD, "OB_NAME" + "=?", new String[]{name});
        System.out.println("食品删除成功！！！");
    }

    //删除通过物品名称
    public void deleteDurgWithName(String name) {
        db.delete(TABLE_DURG, "OB_NAME" + "=?", new String[]{name});
        System.out.println("药品删除成功！！！");
    }

    //删除通过物品名称
    public void deleteComsWithName(String name) {
        db.delete(TABLE_COSMETICS, "OB_NAME" + "=?", new String[]{name});
        System.out.println("化妆品删除成功！！！");
    }

    //删除通过物品名称
    public void deleteSupWithName(String name) {
        db.delete(TABLE_SUPPLIES, "OB_NAME" + "=?", new String[]{name});
        System.out.println("物资删除成功！！！");
    }


    //删除某食品(ID)
    public void deleteFoodWithID(int ID) {
        db.delete(TABLE_FOOD, "id" + "=?", new String[]{String.valueOf(ID)});
        System.out.println("食品删除成功！！！");
    }

    //删除某药品
    public void deleteDurgWithID(int ID) {
            db.delete(TABLE_DURG, "id" + "=?", new String[]{String.valueOf(ID)});
        System.out.println("药品删除成功！！！");
    }

    //删除某化妆品
    public void deleteComeWithID(int ID) {
        db.delete(TABLE_COSMETICS, "id" + "=?", new String[]{String.valueOf(ID)});
        System.out.println("化妆品删除成功！！！");
    }

    //删除某物资
    public void deleteSuppliesWithID(int ID) {
        db.delete(TABLE_SUPPLIES, "id" + "=?", new String[]{String.valueOf(ID)});
        System.out.println("物资删除成功！！！");
    }

    //通过ID更新食品信息
    public int updateFoodWithID(objectInfo food, int id) {

        ContentValues values = new ContentValues();
        values.put("OB_name", food.getOB_name());
        values.put("OB_type", food.getOB_type());
        values.put("OB_produce_date",food.getOB_produce_date());
        values.put("OB_after_date",food.getOB_after_date());
        values.put("OB_open_date", food.getOB_open_date());
        values.put("OB_amount", food.getOB_amount());
        values.put("OB_guarantee_day",food.getOB_guarantee_day());
        values.put("OB_remarks", food.getOB_remarks());
        values.put("OB_between", food.getBetweenDays());
        values.put("OB_remindDay", food.getRemindDay());

        System.out.println("更新食物成功！！！");
        return db.update(TABLE_FOOD, values, "id" + "=?", new String[]{String.valueOf(id)});
    }

    //通过ID更新药物信息
    public int updateDurgWithID(objectInfo durg, int id) {

        ContentValues values = new ContentValues();
        values.put("OB_name", durg.getOB_name());
        values.put("OB_type", durg.getOB_type());
        values.put("OB_produce_date",durg.getOB_produce_date());
        values.put("OB_after_date",durg.getOB_after_date());
        values.put("OB_open_date", durg.getOB_open_date());
        values.put("OB_amount", durg.getOB_amount());
        values.put("OB_guarantee_day",durg.getOB_guarantee_day());
        values.put("OB_remarks", durg.getOB_remarks());
        values.put("OB_between", durg.getBetweenDays());
        values.put("OB_remindDay", durg.getRemindDay());

        System.out.println("更新药物成功！！！");
        return db.update(TABLE_DURG, values, "id" + "=?", new String[]{String.valueOf(id)});
    }

    //通过ID更新化妆品信息
    public int updateCosmeticsWithID(objectInfo cosms, int id) {

        ContentValues values = new ContentValues();
        values.put("OB_name", cosms.getOB_name());
        values.put("OB_type", cosms.getOB_type());
        values.put("OB_produce_date",cosms.getOB_produce_date());
        values.put("OB_after_date",cosms.getOB_after_date());
        values.put("OB_open_date", cosms.getOB_open_date());
        values.put("OB_amount", cosms.getOB_amount());
        values.put("OB_guarantee_day",cosms.getOB_guarantee_day());
        values.put("OB_remarks", cosms.getOB_remarks());
        values.put("OB_between", cosms.getBetweenDays());
        values.put("OB_remindDay", cosms.getRemindDay());

        System.out.println("更新化妆品成功！！！");
        return db.update(TABLE_DURG, values, "id" + "=?", new String[]{String.valueOf(id)});
    }

    //通过ID更新物资信息
    public int updateSuppliesWithID(objectInfo supp, int id) {

        ContentValues values = new ContentValues();
        values.put("OB_name", supp.getOB_name());
        values.put("OB_type", supp.getOB_type());
        values.put("OB_produce_date",supp.getOB_produce_date());
        values.put("OB_after_date",supp.getOB_after_date());
        values.put("OB_open_date", supp.getOB_open_date());
        values.put("OB_amount", supp.getOB_amount());
        values.put("OB_guarantee_day",supp.getOB_guarantee_day());
        values.put("OB_remarks", supp.getOB_remarks());
        values.put("OB_between", supp.getBetweenDays());
        values.put("OB_remindDay", supp.getRemindDay());

        System.out.println("更新物资成功！！！");
        return db.update(TABLE_DURG, values, "id" + "=?", new String[]{String.valueOf(id)});
    }





}
