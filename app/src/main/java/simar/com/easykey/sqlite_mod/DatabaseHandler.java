package simar.com.easykey.sqlite_mod;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class DatabaseHandler {
    Context context;



    public DatabaseHandler (Context context){
        this.context=context;
    }
    private void InitializeSQLCipher() {
        SQLiteDatabase.loadLibs(context);
        File databaseFile = context.getDatabasePath("demo.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "test123", null);
        database.execSQL("create table t1(a, b)");
        database.execSQL("insert into t1(a, b) values(?, ?)", new Object[]{"one for the money",
                "two for the show"});
    }
}
