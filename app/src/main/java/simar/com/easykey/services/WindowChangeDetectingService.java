package simar.com.easykey.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BReceiver;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class WindowChangeDetectingService extends AccessibilityService {
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        //Configure these here for compatibility with API 13 and below.
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= 16)
            //Just in case this helps
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = tryGetActivity(componentName);
                boolean isActivity = activityInfo != null;
                if (isActivity) {
                    Log.i("CurrentActivity", componentName.flattenToShortString());
                    String cat = "";
                    String stringToCheck=componentName.flattenToShortString();
                    if (stringToCheck.contains("facebook")) {
                        cat = "Facebook";
                        //   startService(new Intent(getBaseContext(), FloatingViewService.class));
                    }else if(stringToCheck.contains("instagram")){
                        cat="Instagram";
                    }else if(stringToCheck.contains("twitter")){
                        cat="Twitter";
                    }else if(stringToCheck.contains("skype")){
                        cat="Skype";
                    }else if(stringToCheck.contains("Viber")){
                        cat="Viber";
                    }else if(stringToCheck.contains("Snapchat")){
                        cat="Snapchat";
                    }else if(stringToCheck.contains("Pinterest")){
                        cat="Pinterest";
                    }else {
                        cat="Other";
                    }
                    createNotification(cat, this);

                }

            }
        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {
    }


    private NotificationManager notifManager;

    public void createNotification(String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "10"; // default_channel_id
        String title = "Easy Key"; // Default Channel
        //  Intent intent;
        Intent i = new Intent();
        i.setClassName(this, BReceiver.class.getName());
        i.setAction(aMessage);
        i.putExtra("cat",aMessage);
        PendingIntent pendingIntent;

        if (aMessage.equals("Other")){
            aMessage="App is running";
        }

        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Intent snoozeIntent = new Intent(this, BReceiver.class);
        snoozeIntent.setAction("add");
        snoozeIntent.putExtra("cat",aMessage);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 1);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 1, snoozeIntent, 0);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(false);

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
                    .addAction(R.drawable.ic_add_black_24dp, "Add New Password",
                            snoozePendingIntent)
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
                    .addAction(R.drawable.ic_add_black_24dp, "Add New Password",
                            snoozePendingIntent)
                    /*.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})*/
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
