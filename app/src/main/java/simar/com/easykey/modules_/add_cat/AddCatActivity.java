package simar.com.easykey.modules_.add_cat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.CatM;
import simar.com.easykey.modules_.HomeScreen.adapters.DataAdapter;
import simar.com.easykey.modules_.MainActivity;
import simar.com.easykey.sqlite_mod.FeedReaderContract;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class AddCatActivity extends BaseActivity {
    RecyclerView cat_rv;
    DataAdapter categoryAdapter;
    CardView add_Cat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        cat_rv=findViewById(R.id.cat_rv);
        add_Cat=findViewById(R.id.add_Cat);
        SQLiteDatabase.loadLibs(context);
        cat_rv.setLayoutManager(new LinearLayoutManager(context));
        //   data = new FeedReaderDbHelper(getActivity()).getCategories(appSession.getMasterPassword());

        categoryAdapter = new DataAdapter(context, getCategories(),true,getdbIbstance());
        cat_rv.setAdapter(categoryAdapter);
        if (categoryAdapter.getItemCount()<=0){
            add_Cat.setVisibility(View.VISIBLE);
        }else {

            add_Cat.setVisibility(View.GONE);

        }

        showLockscreen();
    }

    public void handleClick(View view) {
        onBackPressed();
    }

    public void handle_add(View view) {
        goToNext(AddToDatabaseActivity.class);
    }


    public ArrayList<CatM> getCategories() {

        ArrayList<CatM> data = new ArrayList<>();
        try {
            FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
            SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase("somePass");
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "';", null);
            Log.e(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());

            String dbValues = "";

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
                    Log.e("dbValues", "====" + dbValues);
                    data.add(new CatM(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID )), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME )), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL))));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key ");
        }

        return data;

    }

    @Override
    protected void onResume() {
        super.onResume();



    }
}
