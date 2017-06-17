package timelog.android.ninjo.de.timelog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LogHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "log_entries";
    public static final String LOG_TABLE_NAME = "log_entry";

    public static final String ID_COLUMN = "_id";
    public static final String DURATION_COLUMN = "duration";

    public static final String ACTIVITY_COLUMN = "log_activity";

    public LogHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE log_entry (" + ID_COLUMN + " integer primary key autoincrement, log_start TEXT, log_end TEXT, log_activity TEXT, duration TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //no upgrades yet
    }

    public void onDeleteAll() {
        //TODO implement delete
    }

    public Cursor getAllRows() {
        String query = "SELECT * FROM " + LOG_TABLE_NAME;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }
}
