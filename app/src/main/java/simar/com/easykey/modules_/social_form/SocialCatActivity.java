package simar.com.easykey.modules_.social_form;

import androidx.appcompat.app.AppCompatActivity;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

import android.os.Bundle;
import android.view.View;

public class SocialCatActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_cat);
        showLockscreen();
    }

    public void handleClick(View view) {
        finish();
    }

    public void handleFacebook(View view) {
        goToNext("Facebook",SocialFormActivity.class);
    }

    public void handleTwitter(View view) {
        goToNext("Twitter",SocialFormActivity.class);
    }

    public void handleInstagram(View view) {
        goToNext("Instagram",SocialFormActivity.class);
    }

    public void handleSkype(View view) {
        goToNext("Skype",SocialFormActivity.class);
    }

    public void handleViber(View view) {
        goToNext("Viber",SocialFormActivity.class);
    }

    public void handleSnapchat(View view) {
        goToNext("Snapchat",SocialFormActivity.class);
    }

    public void handlePinterest(View view) {
        goToNext("Pinterest",SocialFormActivity.class);
    }

    public void handleOther(View view) {
        goToNext("Other",SocialFormActivity.class);
    }
}
