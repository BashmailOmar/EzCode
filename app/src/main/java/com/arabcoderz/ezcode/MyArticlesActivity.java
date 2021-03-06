package com.arabcoderz.ezcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyArticlesActivity extends AppCompatActivity {
    private RecyclerViewAdapterArticles recyclerView_dAdapter;
    public List<ListArticle> listArticle = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private SharedPreferences shared_getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_articles);

        String KEY_PREF_NAME = "userData";
        shared_getData = getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);// اسم الملف الذي يحتوي المعلومات (KEY_PREF_NAME)

        findViewById(R.id.backBtnMyArticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyArticlesActivity.this, UserMoreActivity.class));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.myArticlesRecyclerView);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView_dAdapter = new RecyclerViewAdapterArticles(listArticle, this);
        recyclerView.setAdapter(recyclerView_dAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == listArticle.size() - 1) {
                    getAllArticles(listArticle.get(listArticle.size() - 1).getId());
                }
            }
        });
        getAllArticles(0);
    }

    void getAllArticles(int limit) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "ViewAllArticles.php?limit=" + limit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_usersS = jsonResponse.getJSONArray("All_Articles");
                            for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                JSONObject responsS = jsonArray_usersS.getJSONObject(i);
                                int id = responsS.getInt("article_id");
                                String title = responsS.getString("article_title");
                                String content = responsS.getString("article_content");
                                String writer = responsS.getString("article_writer");
                                String date = responsS.getString("article_date");
                                if(shared_getData.getString("username", "").equals(writer)) {
                                    listArticle.add(new ListArticle(id, title, content, writer, date));
                                }
                            }
                            recyclerView_dAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        stringRequest.setShouldCache(false);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyArticlesActivity.this, UserMoreActivity.class));
    }
}