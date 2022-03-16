package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserArticlesActivity extends AppCompatActivity {

    private ImageView addArticlesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_articles);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.articles);
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
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(),UserMoreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        }); //end BottomNavigationView

        addArticlesUser = findViewById(R.id.But_add_articles);
        addArticlesUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserArticlesActivity.this,addArticles.class));
            }
        });// end addArticlesUser >> setOnClickListener



    }//end onCreate
}