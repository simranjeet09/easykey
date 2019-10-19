package simar.com.easykey.modules_;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.core.app.NotificationCompat;
import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;
import simar.com.easykey.modules_.master_pass.MasterPasswordActivity;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;


public class BaseActivity extends AppCompatActivity {
    public Context context = this;
    AppSession appSession;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSession = new AppSession(context);
        SQLiteDatabase.loadLibs(context);
       // if (!isNotificationVisible()) {
            createNotification("Tap To Open", this);

        //}
    }

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
       // if (appSession != null) {

       // }
        return appSession;
    }

/*    public void resetMasterPass(String old_pass, String pass) {

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
    }*/

    public FeedReaderDbHelper getdbIbstance(){
        return new FeedReaderDbHelper(context);
    }

    private NotificationManager notifManager;

    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "10"; // default_channel_id
        String title = "Easy Key"; // Default Channel
      //  Intent intent;
        Intent i = new Intent();
        i.setClassName(this, BReceiver.class.getName());
        i.setAction("Test");
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);



           // intent = new Intent(context, AppHomeNavigation.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.mipmap.ic_launcher) // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setOnlyAlertOnce(true)
                    //.setTicker(aMessage)
                    .setAutoCancel(false)
            /* .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})*/;
        } else {
            builder = new NotificationCompat.Builder(context, id);
           // intent = new Intent(context, AppHomeNavigation.class);
            //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.mipmap.ic_launcher)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    // .setTicker(aMessage)
                    .setOnlyAlertOnce(true)
                    .setOngoing(true)
                    .setAutoCancel(false)
                    /*.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})*/
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }


    public void goToNext(String cat_name,Class aClass){
        Intent intent= new Intent(context,aClass);
        intent.putExtra("cat",cat_name);
        startActivityForResult(intent,111);
    }

}
