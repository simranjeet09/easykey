package simar.com.easykey.modules_.add_cat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddToDatabaseActivity extends BaseActivity {

    TextView add_new;
    LinearLayout parent;
    EditText cat_title;
    ArrayList<EditText>allFields= new ArrayList<>();

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

        parent.addView(viewMyLayout);
        field.requestFocus();
    }

    public void handleSave(View view) {


        //addNewCat("",getSessionInstance().getMasterPassword(),cat_title.getText().toString());
        goToNext(AppHomeNavigation.class);

    }
}
