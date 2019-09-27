package simar.com.easykey.modules_;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, EnterPinActivity.class);
                startActivityForResult(intent, 200);
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
}
