package chokoapp.imanani;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Work {
    public static final String TABLE_NAME = "works";
    private long _id;
    private Date start;
    private Date end;

    public Work() {
        start = new Date();
        end = null;
    }
    public Work(long start_milisecond) {
        start = new Date(start_milisecond);
        end = null;
    }

    public long getId() { return _id; }

    public static String create_sql() {
        return "CREATE TABLE works (\n"
                + "  _id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "  start INTEGER,\n"
                + "  end   INTEGER\n"
                + ");";
    }

    public static int finished(SQLiteDatabase db, long end_milisecond) {
        ContentValues endValue = new ContentValues();
        endValue.put("end", end_milisecond / 1000);
        return db.update(TABLE_NAME, endValue, "end IS NULL", null);
    }
}