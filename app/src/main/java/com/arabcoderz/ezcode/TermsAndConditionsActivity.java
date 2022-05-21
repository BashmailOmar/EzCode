package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        findViewById(R.id.backBtnTermsAConditions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsAndConditionsActivity.this, RegisterActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}