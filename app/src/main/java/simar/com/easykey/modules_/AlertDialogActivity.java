package simar.com.easykey.modules_;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

public class AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );*/
        setContentView(R.layout.activity_alert_dialog);

        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmlp.gravity = Gravity.CENTER;

    }
}
