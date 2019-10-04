package simar.com.easykey.modules_;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;


import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.master_pass.MasterPasswordActivity;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;


public class BaseActivity extends AppCompatActivity {
    public Context context = this;
    AppSession appSession;

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

    public void showLockscreen() {
        startActivityForResult(new Intent(BaseActivity.this, EnterPinActivity.class), 100);
    }

    public void goToNext(Class aClass) {
        startActivity(new Intent(this, aClass));
    }

    public AppSession getSessionInstance() {
        if (appSession != null) {
            appSession = new AppSession(context);
        }
        return appSession;
    }


    public void resetMasterPass(String old_pass, String pass) {

        FeedReaderDbHelper feedReaderDbHelper =getDbInstance();
        feedReaderDbHelper.rekey(old_pass, pass);
        feedReaderDbHelper.close();
        Toast.makeText(context, "Master Password changed", Toast.LENGTH_SHORT).show();
        startActivity(getIntent());
        finish();
    }


    public FeedReaderDbHelper getDbInstance(){
        SQLiteDatabase.loadLibs(context);
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        return feedReaderDbHelper;
    }

    public void addNewCat(String fields,String masterpass,String tbl_name){
        getDbInstance().createNewTable(fields,tbl_name,masterpass);
    }

}
