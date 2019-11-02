package simar.com.easykey.modules_.social_form;

 import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

 import android.app.AlertDialog;
 import android.content.DialogInterface;
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
            findViewById(R.id.deleteAll).setVisibility(View.GONE);
        }else {
            findViewById(R.id.no_data).setVisibility(View.GONE);
            findViewById(R.id.deleteAll).setVisibility(View.VISIBLE);
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
            socialFormsAdapter= new SocialFormsAdapter(this,data);
            rv_list.setAdapter(socialFormsAdapter);
            socialFormsAdapter.notifyDataSetChanged();


            if (data.size()<=0){
                findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                findViewById(R.id.deleteAll).setVisibility(View.GONE);
            }else {
                findViewById(R.id.no_data).setVisibility(View.GONE);
                findViewById(R.id.deleteAll).setVisibility(View.VISIBLE);
            }

        }
    }

    public void handleDeleteAll(View view) {

        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("All the data from this category will be deleted. This can not be undone.");
        builder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (socialFormsAdapter.getItemCount()>0){
                    getdbIbstance().truncateTabelByCat(category);
                    finish();

                }else {
                    Toast.makeText(context, "No Data to delete.", Toast.LENGTH_SHORT).show();
                }
                }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
