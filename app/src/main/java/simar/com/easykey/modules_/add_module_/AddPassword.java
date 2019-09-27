package simar.com.easykey.modules_.add_module_;

import androidx.appcompat.app.AppCompatActivity;
import io.github.ponnamkarthik.richlinkpreview.RichLinkView;
import io.github.ponnamkarthik.richlinkpreview.RichLinkViewTelegram;
import io.github.ponnamkarthik.richlinkpreview.ViewListener;
import simar.com.easykey.R;
import simar.com.easykey.modules_.BaseActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.sax.Element;
import android.telecom.Connection;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class AddPassword extends BaseActivity {
    ImageView img;
    RichLinkViewTelegram richLinkView;
    EditText web_link;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        richLinkView = (RichLinkViewTelegram) findViewById(R.id.richLinkView);
        web_link = (EditText) findViewById(R.id.web_link);
        title= (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("name"));

        if (getIntent().hasExtra("from")){
            showLockscreen();
        }


    /*    web_link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                        richLinkView.setLink(s.toString(), new ViewListener() {

                            @Override
                            public void onSuccess(boolean status) {

                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });


                } catch (IllegalArgumentException e) {
                }


            }
        });*/


    }

    public void handleClick(View view) {
        onBackPressed();
    }

}
