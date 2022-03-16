package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class addArticles extends AppCompatActivity {

    private EditText Edit_title , Edit_content;
    private Button But_add_article;

    private String Str_title , Str_content , userName;

    private SharedPreferences shared_getData;
    private static  String KEY_PREF_NAME = "userKEY";

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

    }

    private void newArticle(){
        Str_title = Edit_title.getText().toString();
        Str_content = Edit_content.getText().toString();

        shared_getData = getSharedPreferences(KEY_PREF_NAME,Context.MODE_PRIVATE);
        userName = shared_getData.getString("enterUser","no data");


        Response.Listener<String> responseLisener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String success = jsonObject.getString("success");

                    if (success.contains("ok")) {
                        Toast.makeText(addArticles.this, "done", Toast.LENGTH_LONG).show(); //اظهار النص من صفحة php
                        //SharedPreferences.Editor editor = shared_save.edit();
                    } else {
                        Toast.makeText(addArticles.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        send_articles send_articles = new send_articles(Str_title,Str_content,userName,responseLisener);
        RequestQueue queue = Volley.newRequestQueue(addArticles.this);
        queue.add(send_articles);
    }
}