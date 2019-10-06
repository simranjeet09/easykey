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

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_pass_layout);
        SQLiteDatabase.loadLibs(this);
        setpass=findViewById(R.id.setpass);
        pass=findViewById(R.id.enter_pass);



        setpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().length()<6){
                    Toast.makeText(context, "Please enter a strong master password", Toast.LENGTH_SHORT).show();
                }else {
                 /*   FeedReaderDbHelper helper=new FeedReaderDbHelper(context);
                    helper.rekey1(pass.getText().toString());
                    getSessionInstance().setMasterPassword(pass.getText().toString());*/
                }

            }
        });

    }
}
