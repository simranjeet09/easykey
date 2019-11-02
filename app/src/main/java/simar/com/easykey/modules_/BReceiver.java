package simar.com.easykey.modules_;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import simar.com.easykey.home_dialog.AlertDialogActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;
import simar.com.easykey.modules_.social_form.SocialCatActivity;

public class BReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent1) {
        Log.d("Test", "########## intent action " + intent1.getAction());
        //   Toast.makeText(context, "Hi", Toast.LENGTH_LONG).show();

// NEW_TASK allows the new dialog activity to be separate from the existing activity.
// REORDER_TO_FRONT causes the dialog activity to be moved to the front,
// if it's already running.
// Without the NEW_TASK flag, the existing "base" activity
// would be moved to the front as well.

        if (intent1.getAction().equals("add")) {
            Intent intent;
            String cat = intent1.getStringExtra("cat");
            if(cat!=null){
                if (cat.contains("facebook")
                        || cat.contains("twitter")
                        || cat.contains("instagram")
                        || cat.contains("skype")
                        || cat.contains("viber")
                        || cat.contains("snapchat")
                        || cat.contains("pinterest")) {
                    intent = new Intent(context.getApplicationContext(), SocialCatActivity.class);


                } else {
                    intent = new Intent(context.getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

                }

            }else {
                intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);

            }


            context.getApplicationContext().startActivity(intent);
        } else {
            Intent intent = new Intent(context.getApplicationContext(), AlertDialogActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("cat", intent1.getAction());
            context.getApplicationContext().startActivity(intent);
        }

    }

}