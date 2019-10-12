package simar.com.easykey.modules_.view_forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.CatM;

public class ViewFormActivity extends BaseActivity {
    LinearLayout parent;
    ArrayList<TextView>allEdit= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        parent = findViewById(R.id.parent);
        Toast.makeText( context, getIntent().getStringExtra("tbl_name"), Toast.LENGTH_SHORT).show();
        String cNames[] = getdbIbstance().getColumnNames(getIntent().getStringExtra("tbl_name"));
        for (int i = 0; i < cNames.length; i++) {
            LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            TextView et = (TextView) inflater.inflate(R.layout.edit_text_row, null);
            et.setTag(cNames[i]);
            et.setHint(cNames[i]);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 15;
            layoutParams.leftMargin = 15;
            layoutParams.rightMargin = 15;
            et.setLayoutParams(layoutParams);
            allEdit.add(et);
            parent.addView(et);
        }

    }
}