package simar.com.easykey.modules_.add_module_;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

import android.os.Bundle;
import android.view.View;

public class AddWifiPassword extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wifi_password);

        if (getIntent().hasExtra("from")){
            showLockscreen();
        }

    }

    public void handleClick(View view) {
        onBackPressed();
    }
}
