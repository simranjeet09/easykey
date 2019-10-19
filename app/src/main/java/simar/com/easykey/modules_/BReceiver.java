package simar.com.easykey.modules_;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import simar.com.easykey.R;

public class BReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent1)
    {
        Log.d("Test", "########## intent action "+ intent1.getAction());
        Toast.makeText(context, "Hi", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context.getApplicationContext(), AlertDialogActivity.class);
// NEW_TASK allows the new dialog activity to be separate from the existing activity.
// REORDER_TO_FRONT causes the dialog activity to be moved to the front,
// if it's already running.
// Without the NEW_TASK flag, the existing "base" activity
// would be moved to the front as well.
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
// The activity must be started from the application context.
// I'm not sure why exactly.
        context.getApplicationContext().startActivity(intent);
    }

}