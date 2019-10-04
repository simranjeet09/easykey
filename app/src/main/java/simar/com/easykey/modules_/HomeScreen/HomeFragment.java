package simar.com.easykey.modules_.HomeScreen;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import simar.com.easykey.R;
import simar.com.easykey.modules_.AppSession;
import simar.com.easykey.modules_.HomeScreen.adapters.DataAdapter;
import simar.com.easykey.modules_.MainActivity;
import simar.com.easykey.modules_.add_module_.SelectCategories;
import simar.com.easykey.modules_.master_pass.MasterPasswordActivity;
import simar.com.easykey.modules_.room_d.Categories;
import simar.com.easykey.modules_.room_d.CategoriesDatabase;
import simar.com.easykey.sqlite_mod.FeedReaderContract;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

public class HomeFragment extends Fragment {

    RecyclerView rv;
    TextView add_new;
    AppSession appSession;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vvv = inflater.inflate(R.layout.fragment_home, container, false);
        appSession = new AppSession(getActivity());

     /*   if (!appSession.isPassSet()){
            startActivity(new Intent(getActivity(), MasterPasswordActivity.class));
        }*/
        rv = vvv.findViewById(R.id.rv);
        add_new = vvv.findViewById(R.id.add_new);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<CatM> data = new ArrayList<>();


        DataAdapter categoryAdapter = new DataAdapter(getActivity(), data);
        rv.setAdapter(categoryAdapter);
        add_new.setOnClickListener(v -> handleAddNew());

        return vvv;
    }

    private void handleAddNew() {
        startActivity(new Intent(getActivity(), SelectCategories.class));

    }



}
