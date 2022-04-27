package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMoreActivity extends AppCompatActivity {

    private SharedPreferences shared_getData;
    private SharedPreferences.Editor editor;
    private static  String KEY_PREF_NAME = "userKEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_more);
        findViewById(R.id.But_LogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
                editor= shared_getData.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(UserMoreActivity.this, "logout success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserMoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.more);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(),UserNewsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.challenges:
                        startActivity(new Intent(getApplicationContext(),UserChallengesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.articles:
                        startActivity(new Intent(getApplicationContext(),UserArticlesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.more:
                        return true;
                }
                return false;
            }
        });
    }
}