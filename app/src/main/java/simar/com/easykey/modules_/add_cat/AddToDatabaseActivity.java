package simar.com.easykey.modules_.add_cat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;
import simar.com.easykey.sqlite_mod.FeedReaderContract;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AddToDatabaseActivity extends BaseActivity {

    TextView add_new;
    LinearLayout parent;
    EditText cat_title;
 //   ArrayList<HashMap> allMaps = new ArrayList<>();

    HashMap<View, EditText> allViews = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);

        cat_title = findViewById(R.id.cat_title);
        add_new = findViewById(R.id.add_new);
        parent = findViewById(R.id.parent);
        add_new.setOnClickListener(v -> handleAddView());
    }

    private void handleAddView() {


        if (cat_title.getText().toString().length() <= 0) {
            cat_title.setError("Required!");
            Toast.makeText(this, "Please add category title.", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = LayoutInflater.from(AddToDatabaseActivity.this); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.field_row, null);
        ImageView delete = viewMyLayout.findViewById(R.id.delete_row);
        EditText field = viewMyLayout.findViewById(R.id.field);
        delete.setTag(viewMyLayout);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToDatabaseActivity.this);
                builder.setMessage("Do you want to remove this field");
                builder.setTitle("Remove?");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parent.removeView((View) delete.getTag());
                        View v= (View) delete.getTag();
                        Log.e("previousSize=","=="+allViews.size());
                        allViews.remove(v);

                        Log.e("size=","=="+allViews.size());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });

        allViews.put(viewMyLayout, field);
        viewMyLayout.setTag(allViews);
      //  allMaps.add(allViews);
        parent.addView(viewMyLayout);
        field.requestFocus();
    }

    public void handleSave(View view) {
        ArrayList<String> vals = new ArrayList<>();
        boolean error=false;
        for (int k = 0; k < allViews.size(); k++) {
            EditText editText = (EditText) getEntry(k).getValue();

            if (editText!=null){
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Required");
                    Toast.makeText(context, "All Fields are required", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    vals.add(editText.getText().toString() + " TEXT");
                }


            }else {
                error=true;
            }

        }
        if (!error){
            String sql = "CREATE TABLE " + cat_title.getText().toString() + " (id INTEGER PRIMARY KEY, title text, " + TextUtils.join(",", vals) + " )";

            Log.e("sqlQuery", "==" + sql);


            if (vals.size() > 0) {
                boolean b = getdbIbstance().insertNewCat(cat_title.getText().toString(), sql);

                if (b) {
                    goToNext(AppHomeNavigation.class);
                    finishAffinity();
                } else {
                    Toast.makeText(context, "Category already exsist!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please add atleast one field.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private Map.Entry getEntry(int id) {
        Iterator iterator = allViews.entrySet().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (n == id) {

                return entry;
            }
            n++;
        }
        return null;
    }
}
