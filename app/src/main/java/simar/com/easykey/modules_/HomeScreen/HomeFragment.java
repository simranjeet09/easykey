package simar.com.easykey.modules_.HomeScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import simar.com.easykey.R;
import simar.com.easykey.modules_.HomeScreen.adapters.DataAdapter;
import simar.com.easykey.modules_.add_module_.SelectCategories;
import simar.com.easykey.modules_.room_d.Categories;
import simar.com.easykey.modules_.room_d.CategoriesDatabase;

public class HomeFragment extends Fragment {

    RecyclerView rv;
    TextView add_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vvv = inflater.inflate(R.layout.fragment_home, container, false);

        rv = vvv.findViewById(R.id.rv);
        add_new = vvv.findViewById(R.id.add_new);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<CatM>data= new ArrayList<>();
        data.add(new CatM("1","Title 1"));
        data.add(new CatM("2","Title 2"));
        data.add(new CatM("3","Title 3"));
        data.add(new CatM("4","Title 4"));

        DataAdapter categoryAdapter = new DataAdapter(getActivity(),data);
        rv.setAdapter(categoryAdapter);
        add_new.setOnClickListener(v -> handleAddNew());

        return vvv;
    }

    private void handleAddNew() {
        startActivity(new Intent(getActivity(),SelectCategories.class));

    }

    public void insertCat(String s) {
        CategoriesDatabase appDb = CategoriesDatabase.getInstance(getActivity());

        Categories categories = new Categories(s);
        appDb.categoryDao().insertCategory(categories);

    }

}
