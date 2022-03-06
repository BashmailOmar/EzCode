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
    Button languagesBtn;
    public static String MainLink = "http://192.168.8.100/EzCodePHP/"; //192.168.8.100  //192.168.1.13
    public static String Local_FullName, Local_UserName, Local_UserEmail, Local_UserAge, Local_UserEduLvl, Local_UserGender, Local_UserCountry;
    private SharedPreferences shared_getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared_getData = getSharedPreferences("UserData", Context.MODE_PRIVATE);

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

        languagesBtn = (Button) findViewById(R.id.changeLanguageBtn);
        languagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((languagesBtn.getText().toString()).equals("العربية")) {//هنا ناخذ اللي مكتوب ف اللزر اذا كان مكتوب اللغه العربية يغير التطبيق للغه العربية واذا كان غير كذا يتحول للغه الانجليزيه
                    setApplicationLocale("ar");
                } else {
                    setApplicationLocale("en");
                }
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }//هنا راح نغير اللغه بعدين نسوي اعادة انشاء الاكتفتي عشان يبان التغيير اللي سويناه حق اللغه
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Local_FullName = shared_getData.getString("Local_FullName", "").trim();
        Local_UserName = shared_getData.getString("Local_UserName", "").trim();
        Local_UserEmail = shared_getData.getString("Local_UserEmail", "").trim();
        Local_UserAge = shared_getData.getString("Local_UserAge", "").trim();
        Local_UserEduLvl = shared_getData.getString("Local_UserEduLvl", "").trim();
        Local_UserGender = shared_getData.getString("Local_UserGender", "").trim();
        Local_UserCountry = shared_getData.getString("Local_UserCountry", "").trim();
    }

    private void setApplicationLocale(String locale) {
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