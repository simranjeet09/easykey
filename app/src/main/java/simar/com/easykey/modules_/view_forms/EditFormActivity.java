package simar.com.easykey.modules_.view_forms;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

public class EditFormActivity extends BaseActivity {
    LinearLayout parent;
    TextView title;
    TextView save;
    ArrayList<EditText> allEdit = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        parent = findViewById(R.id.parent);
        save = findViewById(R.id.save);
        title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("tbl_name"));

        initViews();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "aef" + allEdit.size() + "==", Toast.LENGTH_SHORT).show();
                Log.e("size", allEdit.size() + "==");
                ContentValues contentValues = new ContentValues();
                boolean isEmpty = false;
                for (int i = 0; i < allEdit.size(); i++) {
                    String value = allEdit.get(i).getText().toString();
                    if (value.isEmpty()) {
                        isEmpty = true;
                        allEdit.get(i).setError("Required!");
                        break;
                    } else {
                        String key = (String) allEdit.get(i).getTag();
                        contentValues.put(key, allEdit.get(i).getText().toString());
                        Log.e("key", key + "==" + allEdit.get(i).getText().toString());

                    }

                }
                if (!isEmpty) {

                    long result = 0;
                    if (!getIntent().hasExtra("id")) {
                        result = getdbIbstance().saveDataTotable("", contentValues, getIntent().getStringExtra("tbl_name"));
                    } else {
                        result = getdbIbstance().saveDataTotable(getIntent().getStringExtra("id"), contentValues, getIntent().getStringExtra("tbl_name"));
                    }
                    if (result > 0) {
                        setResult(RESULT_OK);
                        finish();

                    } else {
                        Toast.makeText(context, "Unable to save data", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void initViews() {
        if (getIntent().getStringExtra("id").isEmpty()) {
            String cNames[] = getdbIbstance().getColumnNames(getIntent().getStringExtra("tbl_name"));
            for (int i = 0; i < cNames.length; i++) {
                LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.edit_text_row, null);
                EditText et = (EditText) v.findViewById(R.id.field);
                TextView label = (TextView) v.findViewById(R.id.label);

                et.setTag(cNames[i]);
                label.setText(cNames[i]);
                if (cNames[i].equals("_id")) {
                    v.setVisibility(View.GONE);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.bottomMargin = 15;
                layoutParams.leftMargin = 15;
                layoutParams.rightMargin = 15;
                et.setLayoutParams(layoutParams);
                if (cNames[i].equals("_id")) {

                } else {
                    allEdit.add(et);
                }


                parent.addView(v);
            }
        } else {


            parent.removeAllViews();
            allEdit.clear();
            ArrayList<HashMap<String, String>> data = getdbIbstance().getTabelRow(getIntent().getStringExtra("tbl_name"), getIntent().getStringExtra("id"));
            for (int i = 0; i < data.size(); i++) {
                LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.edit_text_row, null);
                EditText et = (EditText) v.findViewById(R.id.field);
                TextView label = (TextView) v.findViewById(R.id.label);
                label.setAllCaps(true);
                if (!data.get(i).get("column_name").equals("_id")) {

                    label.setText(data.get(i).get("column_name"));
                    et.setText(data.get(i).get("column_value"));
                    et.setTag(data.get(i).get("column_name"));
                    if (!data.get(i).get("column_name").equals("title")) {
                        et.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.bottomMargin = 15;
                    layoutParams.leftMargin = 15;
                    layoutParams.rightMargin = 15;
                    et.setLayoutParams(layoutParams);
                    allEdit.add(et);
                    parent.addView(v);

                }
            }
        }


    }


    public void handleBack(View view) {
        onBackPressed();
    }

    public void handleDeleteRow(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Are you sure to delete?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getdbIbstance().removeRow(getIntent().getStringExtra("id"),getIntent().getStringExtra("tbl_name"));
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
