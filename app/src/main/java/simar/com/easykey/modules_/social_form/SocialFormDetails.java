package simar.com.easykey.modules_.social_form;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.sqlite_mod.FeedReaderContract;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocialFormDetails extends BaseActivity {
    EditText title, email, password, notes;
    TextView save, tool_title;
    SocialModel model = null;
    boolean isEdit = false;
    int  id = 0;
    String category = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_form_detial);
        title = findViewById(R.id.title);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tool_title = findViewById(R.id.tool_title);
        notes = findViewById(R.id.notes);



        if (getIntent().hasExtra("data")) {
            isEdit = true;
            findViewById(R.id.delete).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.delete).setVisibility(View.GONE);
            category = getIntent().getStringExtra("cat");
        }
        tool_title.setText(getIntent().getStringExtra("cat"));
        if (isEdit) { ;
            model = (SocialModel) getIntent().getSerializableExtra("data");
            id = model.getId();
            category = model.getCat();
            title.setText(model.getTitle());
            email.setText(model.getEmail());
            password.setText(model.getPassword());
            notes.setText(model.getNote());
        }


    }

    public void handleClick(View view) {
        onBackPressed();
    }

    public void handleSave(View view) {

        if (isEdit) {
            int result = getdbIbstance().insertSocialVals(model.getId(), title.getText().toString(), email.getText().toString(), password.getText().toString(), notes.getText().toString(), category);
            if (result > 0) {
                setResult(RESULT_OK);

                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (title.getText().toString().isEmpty()) {
                title.setError("Required");
            } else if (email.getText().toString().isEmpty()) {
                email.setError("Required");
            } else if (password.getText().toString().isEmpty()) {
                password.setError("Required");
            } else {
                int result = getdbIbstance().insertSocialVals(0, title.getText().toString(), email.getText().toString(), password.getText().toString(), notes.getText().toString(), category);
                if (result > 0) {
                    setResult(RESULT_OK);

                    Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }
     //   finish();

    }

    public void handleDelete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete?");
        builder.setMessage("Are you sure to delete " + model.getTitle() + " from database?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getdbIbstance().removeRow(String.valueOf(model.getId()), FeedReaderContract.FeedEntry.EMAIL_TABLE_NAME);
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}
