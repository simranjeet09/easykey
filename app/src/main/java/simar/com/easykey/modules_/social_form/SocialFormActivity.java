package simar.com.easykey.modules_.social_form;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

import android.os.Bundle;
import android.view.View;

public class SocialFormActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_form);
    }

    public void handleClick(View view) {
        onBackPressed();
    }
}
