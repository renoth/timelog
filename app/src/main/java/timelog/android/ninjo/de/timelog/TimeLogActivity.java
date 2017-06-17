package timelog.android.ninjo.de.timelog;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Date;

import okhttp3.OkHttpClient;
import timelog.android.ninjo.de.timelog.database.LogHelper;
import timelog.android.ninjo.de.timelog.domain.LogCategory;
import timelog.android.ninjo.de.timelog.domain.LogEvent;
import timelog.android.ninjo.de.timelog.provider.LogProvider;

public class TimeLogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private CursorAdapter adapter;
    private LogEvent logEvent;
    private LogHelper logHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("test", "Loading app");

        setContentView(R.layout.activity_time_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ListView logList = (ListView) findViewById(R.id.loglist);

        String[] from = new String[] { LogHelper.ID_COLUMN };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.log_list_label };

        getLoaderManager().initLoader(0, null, this);

        logHelper = new LogHelper(getApplicationContext());

        adapter = new SimpleCursorAdapter(this, R.layout.log_list_content, logHelper.getAllRows(), from, to, 0);

        logList.setAdapter(adapter);

        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab_stop = (FloatingActionButton) findViewById(R.id.fab_stop);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Started logging", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startNewLog();

                fab_stop.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
            }
        });

        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Stopped logging", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                stopAndSaveNewLogEvent();

                fab_stop.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
        });

        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private void stopAndSaveNewLogEvent() {
        Log.i("APP", "Saving new Event.");

        logEvent.setEnd(new Date().getTime() + "");

        LogHelper logHelper = new LogHelper(getApplicationContext());
        SQLiteDatabase db = logHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("log_start", logEvent.getStart());
        contentValues.put("log_end", logEvent.getEnd());
        contentValues.put("log_activity", logEvent.getCategory().toString());

        db.insert(LogHelper.LOG_TABLE_NAME, null, contentValues);

        adapter.swapCursor(logHelper.getAllRows());
        adapter.notifyDataSetChanged();

        Log.i("APP", "Saved new Event.");
    }

    private void startNewLog() {
        Log.i("APP", "Starting new Event.");
        logEvent = new LogEvent();
        logEvent.setStart(new Date().getTime() + "");
        logEvent.setCategory(LogCategory.COMPUTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String[] projection = { LogHelper.ID_COLUMN, LogHelper.ACTIVITY_COLUMN };
        CursorLoader cursorLoader = new CursorLoader(this, LogProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
