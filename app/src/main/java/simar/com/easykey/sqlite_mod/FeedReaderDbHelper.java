package simar.com.easykey.sqlite_mod;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simar.com.easykey.modules_.MainActivity;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static FeedReaderDbHelper instance;
    public static final String tbl_suffix = "tbl_easy_key_";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + tbl_suffix + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public synchronized FeedReaderDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FeedReaderDbHelper(context);
        }
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public void addTablesToDb() {

        SQLiteDatabase db = this.getWritableDatabase("simar");
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "Easter Bunny has escaped!");

        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.TABLE_NAME + "';", null);
        Log.e("DATABASE", "Rows count: " + cursor.getCount());
        cursor.close();
        db.close();

    }


    public boolean createNewTable(String fields, String table_name, String master_pass) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        String sl = "CREATE TABLE " + tbl_suffix + " " + table_name + " (" +
                FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FeedReaderContract.FeedEntry.COLUMN_CAT_ID + " " + TEXT_TYPE + " ," +
                fields + ")";
        db.execSQL(sl);
       insertNewCat(master_pass, table_name);

        return true;
    }


    public void insertNewCat(String master_pass, String lbl) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        ContentValues values = new ContentValues();
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        String temp_lbl = lbl;
        Matcher match = pt.matcher(temp_lbl);

        while (match.find()) {
            String s = match.group();
            temp_lbl = temp_lbl.replaceAll("//" + s, "");
        }
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, tbl_suffix + temp_lbl);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, lbl);
        values.put(FeedReaderContract.FeedEntry.TABLE_NAME, lbl);
        long id = db.insert(tbl_suffix + FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        Log.e("id", id + "=");


    }

    public void showDataFromDb() {
        try {
            SQLiteDatabase db = this.getWritableDatabase("somePass");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.TABLE_NAME + "';", null);
            Log.e(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());

            String dbValues = "";

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
                    Log.e("dbValues", "====" + dbValues);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key");
        }
    }


    public void rekey(String old_pass, String passphrase) {
        SQLiteDatabase db = this.getWritableDatabase(old_pass);
        db.execSQL("PRAGMA rekey=" + passphrase);
        db.close();
    }

}
