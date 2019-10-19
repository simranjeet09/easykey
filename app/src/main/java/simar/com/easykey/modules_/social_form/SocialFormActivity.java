package simar.com.easykey.modules_.social_form;

 import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

 import android.content.Intent;
 import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class SocialFormActivity extends BaseActivity {
    RecyclerView rv_list;
    SocialFormsAdapter socialFormsAdapter;
    String category="";
    FeedReaderDbHelper dd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_form);
        rv_list=findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
          dd=getdbIbstance();
          category=getIntent().getStringExtra("cat");

        ArrayList<SocialModel>data=dd.getdataFromSocialTabel(category);

        if (data.size()<=0){
            findViewById(R.id.no_data).setVisibility(View.VISIBLE);
        }
        socialFormsAdapter= new SocialFormsAdapter(this,data);
        rv_list.setAdapter(socialFormsAdapter);
        socialFormsAdapter.notifyDataSetChanged();

    }

    public void handleClick(View view) {
        onBackPressed();
    }

    public void handleAdd(View view) {
        goToNext(category,SocialFormDetails.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        if (resultCode==RESULT_OK){
            ArrayList<SocialModel>data=dd.getdataFromSocialTabel(category);
            if (data.size()<=0){
                findViewById(R.id.no_data).setVisibility(View.VISIBLE);
            }
            socialFormsAdapter= new SocialFormsAdapter(this,data);
            rv_list.setAdapter(socialFormsAdapter);
            socialFormsAdapter.notifyDataSetChanged();

        }
    }
}
