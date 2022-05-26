package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddArticles extends AppCompatActivity {

    private EditText Edit_title, Edit_content;
    private Button But_add_article;

    private String Str_title, Str_content, userName;

    private SharedPreferences shared_getData;
    private static String KEY_PREF_NAME = "userData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_articles);
        Edit_title = findViewById(R.id.input_title_article);
        Edit_content = findViewById(R.id.article_content);
        But_add_article = findViewById(R.id.addArticle);
        But_add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newArticle();
            }
        });
        findViewById(R.id.butBackArticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddArticles.this, UserArticlesActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddArticles.this, UserArticlesActivity.class);
        startActivity(intent);
    }
    private void newArticle() {
        Str_title = Edit_title.getText().toString();
        Str_content = Edit_content.getText().toString();
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        userName = shared_getData.getString("username", "admin");
        String artDoneMsg, artErrorMsg, titleMsg, contentMsg;
        if (shared_getData.getString("language", "").equals("ar")) {
            artDoneMsg = "تم الارسال بنجاح";
            artErrorMsg = "عذرا حدث خطأ اثناء الارسال";
            titleMsg = "يجب ان يتكون العنوان 200 حرف على الاكثر";
            contentMsg = "يجب ان يحتوي المقال 5000 حرف على الاكثر";
        } else {
            artDoneMsg = "sent succesfully";
            artErrorMsg = "Sorry, an error occurred while sending";
            titleMsg = "The address must be at most 200 characters";
            contentMsg = "The article must contain at most 5000 characters";
        }

        if (Str_title.isEmpty() || Str_title.length() > 200) {
            new RegisterActivity().showError(Edit_title, titleMsg);
        } else if (Str_content.isEmpty() || Str_content.length() > 5000) {
            new RegisterActivity().showError(Edit_content, contentMsg);
        } else {
            Response.Listener<String> responseLisener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String success = jsonObject.getString("success");

                        if (success.contains("ok")) {
                            Toast.makeText(AddArticles.this, artDoneMsg, Toast.LENGTH_LONG).show(); //اظهار النص من صفحة php
                            //SharedPreferences.Editor editor = shared_save.edit();
                        } else {
                            Toast.makeText(AddArticles.this, artErrorMsg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            SendArticles SendArticles = new SendArticles(Str_title, Str_content, userName, responseLisener);
            RequestQueue queue = Volley.newRequestQueue(AddArticles.this);
            queue.add(SendArticles);
            startActivity(new Intent(AddArticles.this, UserArticlesActivity.class));
        }

    }
}