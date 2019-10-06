package simar.com.easykey.modules_;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PublicKey;

public class AppSession {

    private static final String MyPREFERENCES = "easy_key_simar.com.easykey";
    private static final String MASTER_PASSWORD = "simar.com.easykey";
    private static final String MASTER_PASSWORD_SET = "simar.com.easykey_SET";
    private static final String IS_FIRST_RUN = "simar.com.easykey_first_run";
    private static final String KEY_PIN = "pin";
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
        return sharedpreferences.getString(MASTER_PASSWORD,"easy_key");
    }
    public String getKeyPin(){
        return sharedpreferences.getString(KEY_PIN,"");
    }
    public boolean getIsFirstRun(){
        return sharedpreferences.getBoolean(IS_FIRST_RUN,true);
    }

    public void setIsFirstRun(){
        editor.putBoolean(IS_FIRST_RUN,false);
        editor.commit();
    }

}
