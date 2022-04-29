package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMoreActivity extends AppCompatActivity {

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    private static String KEY_PREF_NAME = "userData";
    TextView langButtonTextMore, fullnameTextView, usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)
        editor = shared_getData.edit();
        fullnameTextView = findViewById(R.id.fullnameMorePage);
        usernameTextView = findViewById(R.id.usernameMorePage);
        fullnameTextView.setText(shared_getData.getString("fullname", ""));
        usernameTextView.setText(shared_getData.getString("username", ""));
        langButtonTextMore = findViewById(R.id.langButtonTextMore);
        findViewById(R.id.But_LogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
                editor = shared_getData.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(UserMoreActivity.this, "logout success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserMoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.langButtonMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (langButtonTextMore.getText().toString().equals("ENGLISH")) {
                    setApplicationLocale("");
                    MainActivity.langStr="";
                } else {
                    setApplicationLocale("ar");
                    MainActivity.langStr="ar";
                }
                editor.putString("language", MainActivity.langStr);
                editor.apply();
                finish();
                overridePendingTransition(5, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 5);
            }

        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(), UserNewsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(), UserChallengesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.articles:
                        startActivity(new Intent(getApplicationContext(), UserArticlesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.more:
                        return true;
                }
                return false;
            }
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