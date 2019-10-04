package simar.com.easykey.modules_.master_pass;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.AppSession;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

public class MasterPasswordActivity extends BaseActivity {
TextView setpass;
EditText pass;
EditText confirm_pass;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_pass_layout);
        SQLiteDatabase.loadLibs(this);
        setpass=findViewById(R.id.setpass);
        pass=findViewById(R.id.enter_pass);
        confirm_pass=findViewById(R.id.confirm_pass);

         if (getSessionInstance().getMasterPassword().equals("0")){
            pass.setHint(getResources().getString(R.string.enter_pass));
         }else {
             pass.setText(getResources().getString(R.string.enter_old));
         }

        setpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSessionInstance().getMasterPassword().equals("0")){

                    if (!pass.getText().toString().equals(confirm_pass.getText().toString())){
                        Toast.makeText(context, "Password not matched.", Toast.LENGTH_SHORT).show();
                    }else {
                        resetMasterPass("",confirm_pass.getText().toString());
                    }
                }else {

                    if (!pass.getText().toString().equals(getSessionInstance().getMasterPassword())){
                        Toast.makeText(context, "Old password not matched", Toast.LENGTH_SHORT).show();
                    }else {
                        resetMasterPass(pass.getText().toString(),confirm_pass.getText().toString());
                    }
                }





            }
        });

    }
}
