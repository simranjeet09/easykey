package simar.com.easykey.modules_;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PublicKey;

public class AppSession {

    private static final String MyPREFERENCES = "easy_key_simar.com.easykey";
    private static final String MASTER_PASSWORD = "simar.com.easykey";
    private static final String MASTER_PASSWORD_SET = "simar.com.easykey";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;

    public AppSession(Context context){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.apply();
    }

    public void setMasterPassword(String pass){
        editor.putBoolean(MASTER_PASSWORD_SET,true);
        editor.putString(MASTER_PASSWORD,pass);
        editor.commit();
    }

    public boolean isPassSet(){

        return sharedpreferences.getBoolean(MASTER_PASSWORD_SET,false);
    }

    public String getMasterPassword(){
        return sharedpreferences.getString(MASTER_PASSWORD,"");
    }

}
