package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserArticlesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticlesRecyclerViewAdapter recyclerView_dAdapter;
    public List<List_Article> listArticle = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_articles);
        findViewById(R.id.But_add_articles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserArticlesActivity.this, addArticles.class));
            }
        });// end addArticlesUser >> setOnClickListener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.articles);
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
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(), UserMoreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        }); //end BottomNavigationView

        recyclerView = findViewById(R.id.articles_RecyclerView);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView_dAdapter = new ArticlesRecyclerViewAdapter(listArticle, this);
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
    }//end onCreate

    public void getAllArticles(int limit) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "articles.php?limit=" + limit,
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

                                listArticle.add(new List_Article(id, title, content, writer, date));
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
}
