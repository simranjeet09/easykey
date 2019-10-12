package simar.com.easykey.modules_.view_forms;

 import android.content.ContentValues;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.EditText;
 import android.widget.LinearLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.util.ArrayList;

 import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

public class EditFormActivity extends BaseActivity {
    LinearLayout parent;
    TextView title;
    ArrayList<EditText>allEdit= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        parent = findViewById(R.id.parent);
        title = findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("tbl_name"));
        Toast.makeText( context, getIntent().getStringExtra("tbl_name"), Toast.LENGTH_SHORT).show();
        String cNames[] = getdbIbstance().getColumnNames(getIntent().getStringExtra("tbl_name"));
        for (int i = 0; i < cNames.length; i++) {
            LayoutInflater inflater = LayoutInflater.from(context); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            EditText et = (EditText) inflater.inflate(R.layout.edit_text_row, null);
            et.setTag(cNames[i]);
            et.setHint(cNames[i]);
            if (cNames[i].equals("title")){
                et.setVisibility(View.GONE);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 15;
            layoutParams.leftMargin = 15;
            layoutParams.rightMargin = 15;
            et.setLayoutParams(layoutParams);
            allEdit.add(et);
            parent.addView(et);
    }

}

    public void handleSave(View view) {
        ArrayList<String> vals = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < allEdit.size(); i++) {
            if (allEdit.get(i).getText().toString().equals("")) {
                allEdit.get(i).setError("Required");
                return;
            } else {
                contentValues.put((String) allEdit.get(i).getTag(), allEdit.get(i).getText().toString());
            }
        }

        long result=0;
        if (getIntent().hasExtra("id")) {
            result= getdbIbstance().saveDataTotable("", contentValues, getIntent().getStringExtra("tbl_name"));
        } else {
            result=  getdbIbstance().saveDataTotable(getIntent().getStringExtra("id"), contentValues, getIntent().getStringExtra("tbl_name"));
        }
        if (result>0){
            finish();
        }else {
            Toast.makeText(context, "Unabel to save data", Toast.LENGTH_SHORT).show();
        }

    }
}
