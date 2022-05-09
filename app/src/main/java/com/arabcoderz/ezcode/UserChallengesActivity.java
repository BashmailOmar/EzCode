package com.arabcoderz.ezcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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

public class UserChallengesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterChallenges viewAdapterChallenges;
    private List<List_challenges> List_Challeng = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_challenges);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.challenges);
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
                        return true;
                    case R.id.articles:
                        startActivity(new Intent(getApplicationContext(),UserArticlesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.more:
                        startActivity(new Intent(getApplicationContext(),UserMoreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.m_RecyclerView_Challenge);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        viewAdapterChallenges = new RecyclerViewAdapterChallenges(List_Challeng, this);
        recyclerView.setAdapter(viewAdapterChallenges);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == List_Challeng.size() - 1) {
                    GetAllChallenges(List_Challeng.get(List_Challeng.size()-1).getId());
                }
            }
        });
        GetAllChallenges(0);
    }//end onCreate
    private void GetAllChallenges(int limit) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MainActivity.MainLink + "ViewAllChallenges.php?limit=" + limit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse = jsonArray.getJSONObject(0);
                            JSONArray jsonArray_usersS = jsonResponse.getJSONArray("show_challenges");

                            for (int i = 0; i < jsonArray_usersS.length(); i++) {
                                JSONObject responsS = jsonArray_usersS.getJSONObject(i);

                                int id = responsS.getInt("challenge_id");
                                String title = responsS.getString("challenge_title_en");
                                String language = responsS.getString("challenge_programming_language");
                                String level = responsS.getString("challenge_level");
                                String points = responsS.getString("challenge_points");

                                List_Challeng.add(new List_challenges(id, title,language,level,points));
                            }
                            viewAdapterChallenges.notifyDataSetChanged();


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