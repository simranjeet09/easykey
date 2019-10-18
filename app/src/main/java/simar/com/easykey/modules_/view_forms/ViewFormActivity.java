package simar.com.easykey.modules_.view_forms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

public class ViewFormActivity extends BaseActivity {
    LinearLayout parent;
    TextView title, show;
    ArrayList<TextView> views = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        parent = findViewById(R.id.parent);
        title = findViewById(R.id.title);
        show = findViewById(R.id.show);
        title.setText(getIntent().getStringExtra("tbl_name"));
        initViews();


    }


    public void handleSave(View view) {
    }

    public void handleBack(View view) {
        onBackPressed();
    }

    public void handleEdit(View view) {
        Intent intent = new Intent(context, EditFormActivity.class);
        intent.putExtra("tbl_name", getIntent().getStringExtra("tbl_name"));
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initViews();
            setResult(RESULT_OK);
            if (requestCode==100){
                finish();
            }
        }
    }

    public void initViews() {
        parent.removeAllViews();
        views.clear();
        ArrayList<HashMap<String, String>> data = getdbIbstance().getTabelRow(getIntent().getStringExtra("tbl_name"), getIntent().getStringExtra("id"));
        for (int i = 0; i < data.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.view_text_row, null);
            TextView et = (TextView) v.findViewById(R.id.field);
            TextView label = (TextView) v.findViewById(R.id.label);
            label.setAllCaps(true);
            if (!data.get(i).get("column_name").equals("_id")) {

                label.setText(data.get(i).get("column_name"));
                et.setText(data.get(i).get("column_value"));

                if (!data.get(i).get("column_name").equals("title")) {
                    et.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.bottomMargin = 15;
                layoutParams.leftMargin = 15;
                layoutParams.rightMargin = 15;
                et.setLayoutParams(layoutParams);
                et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClipboard(context,et.getText().toString());
                    }
                });
                views.add(et);
                parent.addView(v);

            }
        }

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < views.size(); i++) {
                    views.get(i).setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });

    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }


}
