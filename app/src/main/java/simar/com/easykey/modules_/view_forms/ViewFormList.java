package simar.com.easykey.modules_.view_forms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.HomeScreen.CatM;

public class ViewFormList extends BaseActivity {
    LinearLayout parent;
    ArrayList<EditText>allEdit= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form_list);
        parent = findViewById(R.id.parent);
        CatM catM = (CatM) getIntent().getSerializableExtra("data");
        String cNames[] = getdbIbstance().getColumnNames(catM.getName());
        for (int i = 0; i < cNames.length; i++) {
            LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            EditText et = (EditText) inflater.inflate(R.layout.edit_text_row, null);
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
