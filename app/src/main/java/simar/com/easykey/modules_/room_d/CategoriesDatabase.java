package simar.com.easykey.modules_.room_d;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = Categories.class,exportSchema = false,version = 1)
public abstract class CategoriesDatabase extends RoomDatabase {
    private static final String DB_NAME="catagories_db";
    private static CategoriesDatabase instance;

    public static synchronized CategoriesDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),CategoriesDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }

    public abstract CategoryDao categoryDao();

}
