package simar.com.easykey.modules_.master_pass;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.AppSession;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.MainActivity;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_pass_layout);
        SQLiteDatabase.loadLibs(this);
        setpass = findViewById(R.id.setpass);
        confirm_pass = findViewById(R.id.confirm_pass);
        confirm_pass.setVisibility(View.VISIBLE);
        pass = findViewById(R.id.enter_pass);

        setpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().isEmpty()) {
                    pass.setError("Please enter valid password");
                } else if (getSessionInstance().getMasterPassword().equals(pass.getText().toString())) {
                    if (confirm_pass.getText().toString().isEmpty()) {
                        confirm_pass.setError("Please enter valid password");
                    } else if (confirm_pass.getText().toString().length() < 6) {
                        confirm_pass.setError("Please use minimium 6 alphanumeric characters");
                    } else {
                        getdbIbstance().reKey(confirm_pass.getText().toString(), getSessionInstance().getMasterPassword());
                        getSessionInstance().setMasterPassword(confirm_pass.getText().toString());
                        goToNext(MainActivity.class);
                        finishAffinity();
                    }
                }

            }
        });
    }
}
