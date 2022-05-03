package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    Button langButton;
    static String langStr = "";
    public static String MainLink = "http://192.168.1.13/EzCodePHP/"; //192.168.8.100  //192.168.1.13
    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    public static final String SHARED_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared_getData = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = shared_getData.edit();
        langStr = shared_getData.getString("language", "");
        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        if (!cic.isConnectingToInternet()) {
            Toast.makeText(MainActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }//هنا استدعينا الميثود اللي تحقق من اتصال التطبيق بالانترنت وراح تظهر رساله اذا لم يكن هنالك اتصال بالانترنت
        findViewById(R.id.loginBtnMainPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });//هنا سوينا كليك لسنر للزر اللي يودينا لصفحة تسجيل الدخول
        findViewById(R.id.registerBtnMainPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });//هنا سوينا كليك لسنر للزر اللي يودينا لصفحة تسجيل الدخول
        findViewById(R.id.topTenBtnMainPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TopTenActivity.class));
            }
        });//هنا سوينا كليك لسنر للزر اللي يودينا لصفحة تسجيل الدخول
        langButton = findViewById(R.id.changeLanguageBtn);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (langButton.getText().toString().equals("ENGLISH")) {//هنا ناخذ اللي مكتوب ف اللزر اذا كان مكتوب اللغه العربية يغير التطبيق للغه العربية واذا كان غير كذا يتحول للغه الانجليزيه
                    setApplicationLocale("");
                    langStr = "";


                } else {
                    setApplicationLocale("ar");
                    langStr = "ar";
                }
                editor.putString("language", langStr);
                editor.apply();
                finish();
                overridePendingTransition(5, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 5);
            }//هنا راح نغير اللغه بعدين نسوي اعادة انشاء الاكتفتي عشان يبان التغيير اللي سويناه حق اللغه
        });
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