package simar.com.easykey.sqlite_mod;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.MatrixCursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simar.com.easykey.home_dialog.EmailPassModel;
import simar.com.easykey.modules_.AppSession;
import simar.com.easykey.modules_.social_form.SocialModel;
import simar.com.easykey.modules_.view_forms.FormModel;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static FeedReaderDbHelper instance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EasyKey_.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String PREFERENCES = "easy_key_simar.com.easykey";
    private static final String KEY_PIN = "pin";
    private String master_pass = "somePass";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME + TEXT_TYPE + "," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + TEXT_TYPE +
                    " )";

    private static final String SQL_TBL_EMAIL =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.BASIC_EMAIL_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE + TEXT_TYPE + "," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_LINK + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD + TEXT_TYPE +
                    " )";
    private static final String SQL_TBL_WEBSITE =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.WEBSITES_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE + TEXT_TYPE + "," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_LINK + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_USERNAME + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_IP + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_PORT + TEXT_TYPE +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_DNS + TEXT_TYPE +
                    " )";



    private static final String SQL_CREATE_EMAIL_CAT =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.SOCIAL_CAT + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.EMAIL_TABLE_NOTE + TEXT_TYPE + ")";


    private static final String SQL_CREATE_WIFI_CAT =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.WIFI_TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_TITLE + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_USERNAME + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_PASSWORD + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_IP + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_PORT + " " + TEXT_TYPE + " ," +
                    FeedReaderContract.FeedEntry.WIFI_TABLE_DNS + TEXT_TYPE + ")";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        master_pass = new AppSession(context).getMasterPassword();
    }


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_EMAIL_CAT);
        db.execSQL(SQL_CREATE_WIFI_CAT);
        db.execSQL(SQL_TBL_EMAIL);
        db.execSQL(SQL_TBL_WEBSITE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public boolean doesNotExist(String name) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        String query = "SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "' WHERE " + FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL + " ='" + name + "'";
        Cursor cursor = db.rawQuery(query, new String[]{});
        if (cursor.getCount() > 0) {
            return false;
        } else
            return true;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(master_pass);
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
        Log.e("tb;_name", tabelName);
        SQLiteDatabase sqlDB = this.getWritableDatabase(master_pass);
        Cursor dbCursor = sqlDB.query(tabelName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();


        for (int i = 0; i < columnNames.length; i++) {
            Log.e("all names", columnNames[i] + "==");
        }
        return columnNames;

    }


    public ArrayList<FormModel> getFormsList(String tabel_name) {
        ArrayList<FormModel> result = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase(master_pass);
            Cursor cursor = db.rawQuery("SELECT * FROM '" + tabel_name + "';", null);
            String dbValues = "";

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
                    FormModel formModel = new FormModel(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID)));
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


    public ArrayList<HashMap<String, String>> getTabelRow(String tabel_name, String id) {
        ArrayList<HashMap<String, String>> details = new ArrayList<>();

        try {
            SQLiteDatabase db = this.getWritableDatabase(master_pass);
            Cursor cursor = db.rawQuery("SELECT * FROM '" + tabel_name + "' WHERE _id=" + id + ";", null);
            String dbValues = "";

            if (cursor.moveToFirst()) {

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String data = cursor.getString(i);
                    String column_name = cursor.getColumnName(i);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("column_value", data);
                    map.put("column_name", column_name);
                    Log.e("vals", String.valueOf(map));
                    details.add(map); //change the type of details from ArrayList<String> to arrayList<HashMap<String,String>>
                }
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key");
        }
        return details;
    }

    public boolean insertNewCat(String lbl, String sql) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        ContentValues values = new ContentValues();
        Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
        String temp_lbl = lbl;
        Matcher match = pt.matcher(temp_lbl);

        while (match.find()) {
            String s = match.group();
            temp_lbl = temp_lbl.replaceAll("//" + s, "");
        }

        if (!doesNotExist(lbl)) {
            return false;
        } else {

            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, temp_lbl);
            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, lbl);
            long id = db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, values);
            Log.e("id", id + "=");
            db.execSQL(sql);
            return true;
        }


    }


    public void deleteCatFromDb(String tbl_name) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        String sql = "DROP TABLE " + tbl_name;
        String delete_row = "delete from " + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + " where " + FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME + "='" + tbl_name + "'";
        db.execSQL(delete_row);
        db.execSQL(sql);

    }

    public long saveDataTotable(String id, ContentValues contentValues, String tbl_name) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        long id_ = 0;
        if (!id.isEmpty()) {
            id_ = db.update(tbl_name, contentValues, FeedReaderContract.FeedEntry._ID + "=" + id, null);
        } else {
            id_ = db.insert(tbl_name, null, contentValues);

        }
        return id_;
    }

    public void removeRow(String id, String tbl_name) {
        String delete_row = "delete from " + tbl_name + " where " + FeedReaderContract.FeedEntry._ID + "=" + id;
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        db.execSQL(delete_row);
    }

    public void reKey(String newKey, String oldkey) {
        SQLiteDatabase db = this.getWritableDatabase(oldkey);
        db.rawExecSQL("BEGIN IMMEDIATE TRANSACTION;");
        db.rawExecSQL("PRAGMA rekey = '" + newKey + "';");

    }

    public ArrayList<SocialModel> getdataFromSocialTabel(String cat) {
        ArrayList<SocialModel> result = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase(master_pass);
            Log.e("cat", cat + "==");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME + "' WHERE " + FeedReaderContract.FeedEntry.SOCIAL_CAT + "='" + cat + "';", null);
            String dbValues = "";
/*
         String[]   selectionArgs = new String[] { cat };
            selectionArgs   = new String[] { "%"+selectionArgs + "%" };
            Cursor cursor = db.rawQuery("SELECT * FROM '" +  FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME +
                    "' WHERE "+FeedReaderContract.FeedEntry.SOCIAL_CAT+"", selectionArgs);*/

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + "id==" + cursor.getString(0) + " , " + cursor.getString(1);
                    SocialModel socialModel = new SocialModel
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID))),
                                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE)),
                                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL)),
                                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD)),
                                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.SOCIAL_CAT)),
                                    cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_NOTE)));
                    result.add(socialModel);
                    Log.e("dbValues", "====" + dbValues);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key" + e);
        }


        return result;
    }

    public int insertSocialVals(int id, String title, String email, String pass, String notes, String categoty) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL, email);
        values.put(FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD, pass);
        values.put(FeedReaderContract.FeedEntry.SOCIAL_CAT, categoty);
        if (!notes.isEmpty()) {
            values.put(FeedReaderContract.FeedEntry.EMAIL_TABLE_NOTE, notes);
            if (id != 0) {
                values.put(FeedReaderContract.FeedEntry._ID, id);
            }
        }
        long res = 0;
        if (id != 0) {
            res = db.update(FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME, values, FeedReaderContract.FeedEntry._ID + "=" + id, null);
        } else {
            res = db.insertOrThrow(FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME, null, values);

        }
        Log.e("inserted", "id" + res);
        db.close();
        return (int) res;
    }

    public ArrayList<EmailPassModel> getAllSocialEmails(String cat) {
        ArrayList<EmailPassModel> result = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase(master_pass);
            Log.e("cat", cat + "==");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME + "' WHERE " + FeedReaderContract.FeedEntry.SOCIAL_CAT + "='" + cat + "';", null);
            String dbValues = "";
            if (cursor.moveToFirst()) {
                do {
                    // dbValues = dbValues + "\n" + "id==" + cursor.getString(0) + " , " + cursor.getString(1);
                    EmailPassModel emailPassModel = new EmailPassModel(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_TITLE)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_EMAIL)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.EMAIL_TABLE_PASSWORD)));
                    result.add(emailPassModel);
                    Log.e("dbValues", "====" + dbValues);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key" + e);
        }
        return result;
    }


    public boolean truncateTabel(String tbl) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        db.execSQL("delete from " + tbl);
        db.close();
        return true;
    }

    public boolean truncateTabelByCat(String cat) {
        SQLiteDatabase db = this.getWritableDatabase(master_pass);
        db.execSQL("delete from " +FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME+ " where "+FeedReaderContract.FeedEntry.SOCIAL_CAT+"='"+cat +"'");
        db.close();
        return true;
    }
}

