package simar.com.easykey.modules_.view_forms;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;


public class ViewFormsList extends BaseActivity {
    RecyclerView rv_list;
    AllFormsAdapter allFormsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms_list);
        rv_list=findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<FormModel>list= getdbIbstance().getFormsList(getIntent().getStringExtra("tbl_name"));
        allFormsAdapter= new AllFormsAdapter(this,list);
        rv_list.setAdapter(allFormsAdapter);
        allFormsAdapter.notifyDataSetChanged();

    }


    public void handleAdd(View view) {
        Intent intent= new Intent(context,ViewFormActivity.class);
        intent.putExtra("tbl_name",getIntent().getStringExtra("tbl_name"));
        startActivity(intent);

    }
}
