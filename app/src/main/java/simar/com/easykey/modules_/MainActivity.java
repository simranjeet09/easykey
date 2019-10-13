package simar.com.easykey.modules_;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;


public class MainActivity extends AppCompatActivity {
    Context context = MainActivity.this;
    AppSession appSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSession = new AppSession(context);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (appSession.isPassSet()) {

                    Intent intent = new Intent(context, EnterPinActivity.class);
                    startActivityForResult(intent, 200);
                } else {
                    showDialog();
                }

            }
        }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                if (resultCode == EnterPinActivity.RESULT_BACK_PRESSED) {
                    Toast.makeText(MainActivity.this, "back pressed", Toast.LENGTH_LONG).show();
                } else if (resultCode == EnterPinActivity.RESULT_OK) {
                    startActivity(new Intent(context, AppHomeNavigation.class));
                    finish();
                }

                break;
        }
    }

    public void handleClick(View view) {
        Intent intent = new Intent(this, EnterPinActivity.class);
        startActivityForResult(intent, 200);
    }

    public void handleReset(View view) {
        Intent intent = EnterPinActivity.getIntent(this, true);
        startActivityForResult(intent, 200);
    }

    public void showDialog() {
        Dialog dia = new Dialog(context);
        dia.setContentView(R.layout.master_pass_layout);
        EditText pass = (EditText) dia.findViewById(R.id.enter_pass);
        TextView save = (TextView) dia.findViewById(R.id.setpass);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().toString().equals("")) {
                    pass.setError("Required");
                } else if (pass.getText().toString().length() < 6) {
                    pass.setError("Password should be 6 characters long.");
                } else {

                    appSession.setMasterPassword(pass.getText().toString());
                    dia.dismiss();
                    Intent intent = new Intent(context, EnterPinActivity.class);
                    startActivityForResult(intent, 200);

                }
            }
        });
        dia.show();
    }
}
