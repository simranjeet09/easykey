package simar.com.easykey.modules_.view_forms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;


public class ViewFormsList extends BaseActivity {
    RecyclerView rv_list;
    AllFormsAdapter allFormsAdapter;
    TextView title;
    CardView noData;
    ArrayList<FormModel> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forms_list);
        rv_list = findViewById(R.id.rv_list);
        noData= findViewById(R.id.noData);
        title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("tbl_name"));
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        list = getdbIbstance().getFormsList(getIntent().getStringExtra("tbl_name"));
        allFormsAdapter = new AllFormsAdapter(this, list);
        rv_list.setAdapter(allFormsAdapter);
        allFormsAdapter.notifyDataSetChanged();
        if (allFormsAdapter.getItemCount()>0){
            noData.setVisibility(View.GONE);
        }else{
            noData.setVisibility(View.VISIBLE);
        }
    }


    public void handleAdd(View view) {
        Intent intent = new Intent(context, EditFormActivity.class);
        intent.putExtra("tbl_name", getIntent().getStringExtra("tbl_name"));
        intent.putExtra("id", "");
        intent.putExtra("cat_id", getIntent().getStringExtra("cat_id"));
        startActivityForResult(intent,100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            list = getdbIbstance().getFormsList(getIntent().getStringExtra("tbl_name"));
            allFormsAdapter.notifyDataSetChanged();
            if (allFormsAdapter.getItemCount()>0){
                noData.setVisibility(View.GONE);
            }else{
                noData.setVisibility(View.VISIBLE);
            }
        }

    }

    public void handleBack(View view) {
        onBackPressed();
    }
}
