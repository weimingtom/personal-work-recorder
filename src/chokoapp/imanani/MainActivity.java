package chokoapp.imanani;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor allTaskCursor;
    private TimeKeeper timeKeeper;
    private Ticker ticker;
    private TaskSelectionSpinner spinner;
    private long currentTaskId = 0;
    private SharedPreferences pref;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.title_bar);
        displayToday();
        db = (new DBOpenHelper(this)).getWritableDatabase();
        allTaskCursor = db.query(Task.TABLE_NAME,
                                 new String[] {"_id", "code", "description" },
                                 null, null, null, null, null);
        startManagingCursor(allTaskCursor);

        timeKeeper = new TimeKeeper(
                (TextView) findViewById(R.id.totalTimeView),
                (TextView) findViewById(R.id.durationView));
        timeKeeper.init(db);
        ticker = new Ticker(timeKeeper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplication()).inflate(R.menu.transition, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.dailySummaryMenu:
            return true;
        case R.id.monthlySummaryMenu:
            return true;
        case R.id.defineTaskMenu:
            startActivity(new Intent(this, TaskListActivity.class));
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupSpinner();
        ticker.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        ticker.stop();
    }

    @Override
    public void onStart() {
        super.onStart();
        pref = getSharedPreferences("pref", MODE_PRIVATE|MODE_WORLD_READABLE);
        currentTaskId = pref.getLong("currentTaskId", 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        pref = getSharedPreferences("pref", MODE_PRIVATE|MODE_WORLD_WRITEABLE);
        Editor e = pref.edit();
        e.putLong("currentTaskId", spinner.getCurrentTaskId());
        e.commit();
    }

    private void displayToday() {
        TextView v = (TextView) findViewById(R.id.todayView);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd (E)");
        v.setText(df.format(Calendar.getInstance().getTime()));
    }

    private void setupSpinner() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.recordingLinearLayout);
        if ( spinner != null ) {
            layout.removeView(spinner);
        }
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(
            this, android.R.layout.simple_spinner_item, makeArrayListForSpinner(allTaskCursor));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = new TaskSelectionSpinner(this, adapter, currentTaskId, timeKeeper);
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        param.setMargins(20, 0, 20, 0);
        layout.addView(spinner, 1, param);
    }

    private ArrayList<Task> makeArrayListForSpinner(Cursor c) {
        c.moveToPosition(-1);
        ArrayList<Task> ret =  new ArrayList<Task>();
        while (c.moveToNext()) {
            ret.add(new Task(c.getLong(0), c.getString(1), c.getString(2)));
        }
        return ret;
    }

    public void onClickStartButton(View view) {
        timeKeeper.beginWork((Task)spinner.getSelectedItem());
    }

    public void onClickFinishButton(View view) {
        timeKeeper.endWork();
    }
}