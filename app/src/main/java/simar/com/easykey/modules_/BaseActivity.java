package simar.com.easykey.modules_;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


import simar.com.easykey.lockmodule.activity.EnterPinActivity;


public class BaseActivity extends AppCompatActivity {
    public Context context = this;


    @Override
    protected void onResume() {
        super.onResume();
        KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (myKM.inKeyguardRestrictedInputMode()) {
            //it is locked
            startActivityForResult(new Intent(BaseActivity.this, EnterPinActivity.class), 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == EnterPinActivity.RESULT_BACK_PRESSED) {
                    this.finish();
                } else if (resultCode == EnterPinActivity.RESULT_OK) {


                }

                break;
        }
    }

    public void showLockscreen(){
        startActivityForResult(new Intent(BaseActivity.this, EnterPinActivity.class), 100);
    }

    public void goToNext(Class aClass){
        startActivity(new Intent(this,aClass));
    }

}
