package simar.com.easykey.modules_.add_cat;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddCatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
        showLockscreen();
    }

    public void handleClick(View view) {
        onBackPressed();
    }

    public void handle_add(View view) {
        goToNext(AddToDatabaseActivity.class);
    }


}
