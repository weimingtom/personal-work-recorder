package chokoapp.imanani;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "pwr.db";
    private static final int DB_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Task.create_sql());
        loadSampleTask(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void loadSampleTask(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("sample_tasks.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            SQLiteStatement insert_statement = db.compileStatement(Task
                    .insert_sql());
            db.beginTransaction();
            try {
                while ((line = br.readLine()) != null) {
                    insert_statement.bindString(1, line.split(",")[0]);
                    insert_statement.bindString(2, line.split(",")[1]);
                    insert_statement.execute();
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
