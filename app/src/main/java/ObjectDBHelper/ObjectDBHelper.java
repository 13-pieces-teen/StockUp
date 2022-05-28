package ObjectDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stockup.R;
import com.example.stockup.objectInfo;

public class ObjectDBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;    //构造方法
    private static final String TAG = "数据库";
    //数据库的名字
    public static final String DB_NAME = "objectManager.db";

    //数据库表名
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_DURG = "durg";
    private static final String TABLE_SUPPLIES = "suppllies";
    private static final String TABLE_COSMETICS = "cosmetics";
    SQLiteDatabase db;

    //食品表
    private static final String CREATE_TABLE_FOOD = "create table " + TABLE_FOOD + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            "OB_guarantee_day text" +
            ", OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //药品表
    private static final String CREATE_TABLE_DRUG = "create table " + TABLE_DURG + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            "OB_guarantee_day text" +
            ", OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //化妆品表
    private static final String CREATE_TABLE_SUPPLIES = "create table " + TABLE_SUPPLIES + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            "OB_guarantee_day text" +
            ", OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ", OB_remarks text" +
            ", OB_between integer)";

    //物资表
    private static final String CREATE_TABLE_COSMETICS = "create table " + TABLE_COSMETICS + " " +
            "(id integer primary key autoincrement," +
            "OB_name text, " +
            "OB_type text, " +
            "OB_guarantee_day text" +
            ", OB_produce_date date" +
            ", OB_after_date date" +
            ", OB_open_date date" +
            ", OB_amount integer" +
            ", OB_remarks text" +
            ", OB_between integer)";

    public ObjectDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //构建数据库
        db.execSQL(CREATE_TABLE_DRUG);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_SUPPLIES);
        db.execSQL(CREATE_TABLE_COSMETICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //添加食物
    public long addFood(objectInfo OB)
    {
        //get一个可写的数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("OB_name", OB.getOB_name());
        values.put("OB_type", OB.getOB_type());
        values.put("OB_guarantee_day", OB.getOB_guarantee_day());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_amount", OB.getOB_amount());
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
        values.put("OB_guarantee_day", OB.getOB_guarantee_day());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
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
        values.put("OB_guarantee_day", OB.getOB_guarantee_day());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
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
        values.put("OB_guarantee_day", OB.getOB_guarantee_day());
        values.put("OB_produce_date",OB.getOB_produce_date());
        values.put("OB_after_date",OB.getOB_after_date());
        values.put("OB_open_date", OB.getOB_open_date());
        values.put("OB_amount", OB.getOB_amount());
        values.put("OB_remarks", OB.getOB_remarks());
        values.put("OB_between", OB.getBetweenDays());

        return  db.insert(TABLE_SUPPLIES, null, values);
    }

    //查询行号（即ID）
//    public int getID(objectInfo OB)
//    {
//
//    }



    //删除某物品
    public void deleteObject(int id) {
        db.delete(TABLE_FOOD, id + "=?", new String[]{String.valueOf(id)});
        System.out.println("删除成功！！！");
    }

}
