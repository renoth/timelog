package timelog.android.ninjo.de.timelog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LogHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "logssss";
    public static final String LOG_TABLE_NAME = "log_entry";

    public static final String ID_COLUMN = "_id";
    public static final String ACTIVITY_COLUMN = "log_activity";

    public LogHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE log_entry (" + ID_COLUMN + " integer primary key autoincrement,log_start TEXT, log_end TEXT, log_activity TEXT)");
        ContentValues initialValues = new ContentValues();
        initialValues.put("log_start", "start");
        initialValues.put("log_end", "end");
        initialValues.put("log_activity", "NOTHING");
        Log.i("1.6", "gets to here");
        sqLiteDatabase.insert(LOG_TABLE_NAME, null, initialValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
