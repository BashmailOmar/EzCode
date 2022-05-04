package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        setApplicationLocale(shared_getData.getString("language",""));
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent;
                    if(shared_getData.getString("username","").equals("")){
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                    }else{
                        intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }//هذي الميثود اللي نستخدمها عشان نغير لغة التطبيق

}