package simar.com.easykey.modules_.add_module_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.CatM;
import simar.com.easykey.modules_.HomeScreen.adapters.DataAdapter;
import simar.com.easykey.modules_.add_module_.adapters.CategoryAdapter;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SelectCategories extends BaseActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_categories);
        rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        List<CatM> data= new ArrayList<>();
        data.add(new CatM("1","Email"));
        data.add(new CatM("2","Social Network"));
        data.add(new CatM("3","Wifi Password"));
        data.add(new CatM("4","Other"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(this,data);
        rv.setAdapter(categoryAdapter);


    }

    public void handleClick(View view) {
        onBackPressed();
    }
}
