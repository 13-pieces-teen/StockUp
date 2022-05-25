package ObjectDBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ObjectDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "ObjectDBHelper";
    //数据库的名字
    public static final String DB_NAME = "object_manager.db";
    //sql表的名字
    private static final String TABLE_NAME_OBJECT = "objectManager";
    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_OBJECT + " (id integer primary key autoincrement, " +
            "name text, number text, gender text, Class text, course text, score text)";

    public static final int VERSION = 1;    //构造方法

    public ObjectDBHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    public ObjectDBHelper(@Nullable Context context, @Nullable String name,@Nullable SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
