package simar.com.easykey.modules_.HomeScreen;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import simar.com.easykey.R;
import simar.com.easykey.modules_.AppSession;
import simar.com.easykey.modules_.HomeScreen.adapters.DataAdapter;
import simar.com.easykey.modules_.MainActivity;
import simar.com.easykey.modules_.add_cat.AddCatActivity;
import simar.com.easykey.modules_.add_module_.SelectCategories;
import simar.com.easykey.modules_.master_pass.MasterPasswordActivity;
import simar.com.easykey.sqlite_mod.FeedReaderContract;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;


public class HomeFragment extends Fragment {

    RecyclerView rv;
    TextView add_new;
    AppSession appSession;
    List<CatM> data = new ArrayList<>();
    DataAdapter categoryAdapter;
    CardView add_Cat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vvv = inflater.inflate(R.layout.fragment_home, container, false);
        add_Cat = vvv.findViewById(R.id.add_Cat);

        appSession = new AppSession(getActivity());
        SQLiteDatabase.loadLibs(getActivity());
        if (appSession.getIsFirstRun()) {
            initAtFirst();
            appSession.setIsFirstRun();
        }
        rv = vvv.findViewById(R.id.rv);
        add_new = vvv.findViewById(R.id.add_new);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //   data = new FeedReaderDbHelper(getActivity()).getCategories(appSession.getMasterPassword());
        categoryAdapter = new DataAdapter(getActivity(), getCategories(), false, null);
        rv.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        if (categoryAdapter.getItemCount() <= 0) {
            add_Cat.setVisibility(View.VISIBLE);
        } else {
            add_Cat.setVisibility(View.GONE);
        }
        add_new.setOnClickListener(v -> handleAddNew());
        add_Cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCatActivity.class));
            }
        });
        return vvv;
    }

    private void handleAddNew() {
        startActivity(new Intent(getActivity(), SelectCategories.class));

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void initAtFirst() {
        SQLiteDatabase.loadLibs(getActivity());
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(getActivity());
        String pass = new AppSession(getActivity()).getMasterPassword();
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase(pass);
        ContentValues V1 = new ContentValues();
        V1.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, "social");
        V1.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "Social Network");
        if (feedReaderDbHelper.doesNotExist("Password")) {
            long id = db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V1);
            ContentValues cvPass = new ContentValues();
            cvPass.put(FeedReaderContract.FeedEntry.COLUMN_CAT_ID, String.valueOf(id));
            cvPass.put(FeedReaderContract.FeedEntry.TABLE_COLUMN_label, String.valueOf(id));

        }
        if (feedReaderDbHelper.doesNotExist("WIFI Details")) {
            ContentValues V2 = new ContentValues();
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, "wifi");
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "WIFI Details");
            long id = db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V2);

        }

        if (feedReaderDbHelper.doesNotExist("Email")) {
            ContentValues V2 = new ContentValues();
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, "email");
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "Emails");
            long id = db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V2);

        } if (feedReaderDbHelper.doesNotExist("website")) {
            ContentValues V2 = new ContentValues();
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME, "website");
            V2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL, "Website");
            long id = db.insert(FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME, null, V2);

        }


        Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "';", null);
        Log.e(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());
        cursor.close();
        db.close();
    }

    public ArrayList<CatM> getCategories() {

        ArrayList<CatM> data = new ArrayList<>();
        try {
            FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(getActivity());
            String pass = new AppSession(getActivity()).getMasterPassword();
            SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase(pass);
            Cursor cursor = db.rawQuery("SELECT * FROM '" + FeedReaderContract.FeedEntry.CATEGORY_TABLE_NAME + "';", null);
            Log.e(MainActivity.class.getSimpleName(), "Rows count: " + cursor.getCount());

            String dbValues = "";

            if (cursor.moveToFirst()) {
                do {
                    dbValues = dbValues + "\n" + cursor.getString(0) + " , " + cursor.getString(1);
                    Log.e("dbValues", "====" + dbValues);
                    data.add(new CatM(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TABLE_NAME)), cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_CAT_LABEL))));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

        } catch (Exception e) {
            Log.e("Database helper", "Incorrect master key ");
        }

        return data;

    }


//    public void insertDataT(String tbl_name,String val)
}
