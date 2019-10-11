package simar.com.easykey.sqlite_mod;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.MatrixCursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;

import simar.com.easykey.modules_.view_forms.FormModel;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static FeedReaderDbHelper instance;
    public static final String tbl_suffix = "tbl_easy_key_";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EasyKey.db";

    private static final String TEXT_TYPE = " TEXT";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME + TEXT_TYPE + "," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + TEXT_TYPE +
                    " )";

/*
    private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + tbl_suffix + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME + TEXT_TYPE + ")";*/

    private static final String SQL_CREATE_EMAIL_CAT =
            "CREATE TABLE " + tbl_suffix + FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_CAT_ID + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_LINK + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_NOTE + TEXT_TYPE + ")";


    private static final String SQL_CREATE_WIFI_CAT =
            "CREATE TABLE " + tbl_suffix + FeedReaderContract.FeedEntry.WIFI_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_TITLE + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_CAT_ID + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_USERNAME + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_PASSWORD + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_IP + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_PORT + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_DNS + TEXT_TYPE + ")";

    private static final String SQL_TABLE_COLOUMNS =
            "CREATE TABLE " + tbl_suffix + FeedReaderContract.FeedEntry.TABLE_COLUMN_NAMES + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.COLUMN_CAT_ID + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.TABLE_COLUMN_NAMES + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.TABLE_COLUMN_label + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME;

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
        db.execSQL(SQL_CREATE_EMAIL_CAT);
        db.execSQL(SQL_CREATE_WIFI_CAT);
        db.execSQL(SQL_TABLE_COLOUMNS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

/*
    public void initAtFirst() {
        SQLiteDatabase db = this.getWritableDatabase("easy_key");
        ContentValues V1 = new ContentValues();
        V1.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, tbl_suffix+"password");
        V1.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "Password");
        db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V1);
        ContentValues V2 = new ContentValues();
        V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, tbl_suffix+"wifi");
        V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "WIFI Details");
        db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V2);
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
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, tbl_suffix + temp_lbl);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, lbl);
        long id = db.insert(tbl_suffix + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, values);
        Log.e("id", id + "=");


    }





    public void showDataFromDb() {
        try {
            SQLiteDatabase db = this.getWritableDatabase("easy_key");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "';", null);
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

    public void rekey1(String passphrase) {
        SQLiteDatabase db = this.getWritableDatabase("");
       db.execSQL("PRAGMA rekey=" + passphrase);
        db.close();
    }*/


    public boolean doesNotExist(String name) {
        SQLiteDatabase db = this.getWritableDatabase("somePass");
        String query = "SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "' WHERE " + FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + " ='" + name + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        if (cursor.getCount() > 0) {
            return false;
        } else
            return true;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase("somePass");
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    public String[] getColumnNames(String tabelName) {
        Log.e("tb;_name",tabelName);
        SQLiteDatabase sqlDB = this.getWritableDatabase("somePass");
        Cursor dbCursor = sqlDB.query(tabelName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();


        for (int i = 0; i < columnNames.length; i++) {
            Log.e("all names", columnNames[i] + "==");
        }
        return columnNames;

    }


    public ArrayList<FormModel> getFormsList(String tabel_name){
        ArrayList<FormModel> result= new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase("somePass");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + tabel_name + "';", null);
            String dbValues = "";

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
                    FormModel formModel= new FormModel(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE)),cursor.getString(cursor.getColumnIndex(     FeedReaderContract.FeedEntry._ID)));
                    result.add(formModel);
                    Log.e("dbValues", "====" + dbValues);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key");
        }
    return result;
    }
}