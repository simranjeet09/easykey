package simar.com.easykey.modules_.settings_mod;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.BaseActivity;
import simar.com.easykey.modules_.MainActivity;
import simar.com.easykey.modules_.master_pass.MasterPasswordActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AppSettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        showLockscreen();
    }

    public void handleClick(View view) {
        onBackPressed();
    }

    public void handleChangePin(View view) {
        Intent intent = EnterPinActivity.getIntent(this, true);
        startActivityForResult(intent, 200);
    }

    public void handleChangeMasterPass(View view) {
        goToNext(MasterPasswordActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==200){
                goToNext(MainActivity.class);
                finishAffinity();
            }
            if (resultCode == EnterPinActivity.RESULT_BACK_PRESSED) {
                this.finish();
            }

        }
    }
}
