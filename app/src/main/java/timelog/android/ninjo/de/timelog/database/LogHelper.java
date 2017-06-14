package timelog.android.ninjo.de.timelog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "logData";
    public static final String LOG_TABLE_NAME = "log_entry";

    public static final String ID_COLUMN = "_id";
    public static final String ACTIVITY_COLUMN = "log_activity";

    public LogHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE log_entry (" + ID_COLUMN + " integer primary key autoincrement,log_start TEXT, log_end TEXT, log_activity TEXT)");
        sqLiteDatabase.execSQL("insert into log_entry(log_start, log_end, log_activity) values ('start','end','WALKING');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
