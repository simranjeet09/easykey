package simar.com.easykey.home_dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import simar.com.easykey.R;
import simar.com.easykey.lockmodule.activity.EnterPinActivity;
import simar.com.easykey.modules_.HomeScreen.AppHomeNavigation;
import simar.com.easykey.sqlite_mod.FeedReaderDbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class AlertDialogActivity extends Activity {
    RecyclerView rv;
    Context context;
    TextView noData;
    EmailsAdapter emailsAdapter;
    public static String STRINGTOCOPY="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        context = this;
        rv = findViewById(R.id.rv);
        noData = findViewById(R.id.noData);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wmlp.gravity = Gravity.CENTER;
        SQLiteDatabase.loadLibs(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (height / 2) + 70);
        rv.setLayoutParams(layoutParams);
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(context);
        ArrayList<EmailPassModel> data = dbHelper.getAllSocialEmails(getIntent().getStringExtra("cat"));
        if (data.size() <= 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
        emailsAdapter = new EmailsAdapter(context, data);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(emailsAdapter);
        emailsAdapter.notifyDataSetChanged();
    }

    public void handleClick(View view) {
        Intent intent = new Intent(context, AppHomeNavigation.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == EnterPinActivity.RESULT_BACK_PRESSED) {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
                } else if (resultCode == EnterPinActivity.RESULT_OK) {
                    setClipboard(context,STRINGTOCOPY);
                }

                break;
        }

    }


    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
        STRINGTOCOPY="";
        finish();
    }

    public void handleCancel(View view) {
        finish();
    }
}
