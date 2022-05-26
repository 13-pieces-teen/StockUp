package ObjectDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.stockup.objectInfo;

public class ObjectDBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;    //构造方法
    private static final String TAG = "ObjectDBHelper";
    //数据库的名字
    public static final String DB_NAME = "objectManager.db";
    //sql表的名字
    private static final String TABLE_NAME_OBJECT = "objectManager";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_OBJECT + " " +
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
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //添加一个物品
    public long addObject(objectInfo OB)
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

        //return的是这一行的id
        return db.insert(TABLE_NAME_OBJECT, null, values);
    }



}
