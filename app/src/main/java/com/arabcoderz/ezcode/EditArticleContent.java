package com.arabcoderz.ezcode;

import static com.arabcoderz.ezcode.MainActivity.MainLink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditArticleContent extends AppCompatActivity {
    static String editContentURL = MainLink + "edit_article_content.php";
    private SharedPreferences shared_getData;
    private static final String KEY_PREF_NAME = "userData";
    EditText titleEditText, contentEditText;
    RequestQueue requestQueue;
    private AlertDialog.Builder builder;
    String msg, yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_article_content);
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
        builder = new AlertDialog.Builder(this);
        findViewById(R.id.butBackContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditArticleContent.this, ArticleContentActivity.class));
            }
        });
        findViewById(R.id.editArticle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(msg)
                        .setCancelable(true)
                        .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, editContentURL,
                                        response -> Toast.makeText(EditArticleContent.this, "success", Toast.LENGTH_SHORT).show(),
                                        error -> Toast.makeText(EditArticleContent.this, "error", Toast.LENGTH_SHORT).show()) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> parms = new HashMap<>();
                                        parms.put("articleId", String.valueOf(ArticlesRecyclerViewAdapter.articleId));
                                        parms.put("articleTitle", titleEditText.getText().toString());
                                        parms.put("articleContent", contentEditText.getText().toString());
                                        return parms;
                                    }
                                };
                                requestQueue = Volley.newRequestQueue(EditArticleContent.this);
                                requestQueue.add(stringRequest);
                                startActivity(new Intent(EditArticleContent.this, ArticleContentActivity.class));
                            }
                        })
                        .setNegativeButton(no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
        titleEditText = findViewById(R.id.edit_title_article);
        contentEditText = findViewById(R.id.edit_article_content);
        if (MainActivity.langStr.equals("ar")) {
            msg = "هل انت متأكد انك تريد تعديل المقال؟";
            yes = "نعم";
            no = "لا";
        } else {
            msg = "Are you sure you want to edit the article?";
            yes = "Yes";
            no = "No";
        }
        titleEditText.setText(ArticleContentActivity.articleTextViewTitle.getText());
        contentEditText.setText(ArticleContentActivity.articleTextViewContent.getText());
    }
}